package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import com.vane.pia.domain.User;
import com.vane.pia.service.RoleManager;
import com.vane.pia.service.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
public class EditUserController {

    @Autowired
    UserManager userManager;

    @Autowired
    RoleManager roleManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }


    @GetMapping("/admin/users/{userId}")
    public String showUserDetails(@PathVariable String userId, Model model) {

        model.addAttribute("user", userManager.findUserById(Long.parseLong(userId)));
        model.addAttribute("roles", roleManager.getRoles());

        return Pages.EDIT_USER_TEMPLATE;
    }

    @PostMapping("/admin/users/{userId}/details")
    public String editUserDetails(@PathVariable String userId, @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("BINDING RESULT ERROR");
            model.addAttribute("roles", roleManager.getRoles());
            user.setId(Long.parseLong(userId));
            model.addAttribute("user", user);
            return Pages.EDIT_USER_TEMPLATE;
        }
        log.warn(user.toString());
        userManager.updateUserDetails(user, Long.parseLong(userId));
        return "redirect:/admin/users/"+userId+"?successDetail";
    }

    @PostMapping("/admin/users/{userId}/password")
    public String editUserPassword(@PathVariable String userId,
                                   @RequestParam(value = "newPassword") String newPassword,
                                   @RequestParam(value = "confirmPassword") String confirmPassword) {

        if(newPassword == null || !newPassword.equals(confirmPassword)){
            log.warn("Confirm password is not same");
            return "redirect:/admin/users/"+userId+"?confirmPassword";
        }
        userManager.changePasswordToUser(Long.parseLong(userId), newPassword);
        return "redirect:/admin/users/"+userId+"?successPassword";
    }
}
