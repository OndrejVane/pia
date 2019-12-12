package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import com.vane.pia.domain.Role;
import com.vane.pia.domain.User;
import com.vane.pia.service.RoleManager;
import com.vane.pia.service.UserManager;
import com.vane.pia.utils.LongParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

        User user = userManager.findUserById(LongParser.parseLong(userId));
        model.addAttribute("user", user);
        model.addAttribute("userRoles", userManager.findUserById(LongParser.parseLong(userId)).getRoles());
        model.addAttribute("roles", roleManager.getAllRolesUserDontHave(user));

        return Pages.EDIT_USER_TEMPLATE;
    }

    @PostMapping("/admin/users/{userId}/details")
    public String editUserDetails(@PathVariable String userId,
                                  @Valid User user,
                                  BindingResult bindingResult,
                                  @RequestParam(value = "roles" , required = false) Long[] roles ,
                                  Model model) {

        if (bindingResult.hasErrors()) {
            log.warn("BINDING RESULT ERROR");
            model.addAttribute("roles", roleManager.getRoles());
            user.setId(LongParser.parseLong(userId));
            model.addAttribute("user", user);
            return Pages.EDIT_USER_TEMPLATE;
        }

        userManager.updateUserDetails(user, LongParser.parseLong(userId), roleManager.getRolesByIds(roles));
        return "redirect:/admin/users/" + userId + "?successDetail";
    }

    @PostMapping("/admin/users/{userId}/password")
    public String editUserPassword(@PathVariable String userId,
                                   @RequestParam(value = "newPassword") String newPassword,
                                   @RequestParam(value = "confirmPassword") String confirmPassword) {

        if (newPassword == null || !newPassword.equals(confirmPassword)) {
            log.warn("Confirm password is not same");
            return "redirect:/admin/users/" + userId + "?confirmPassword";
        }
        userManager.changePasswordToUser(LongParser.parseLong(userId), newPassword);
        return "redirect:/admin/users/" + userId + "?successPassword";
    }
}
