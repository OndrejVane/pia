package com.vane.pia.web.controller;


import com.vane.pia.domain.User;
import com.vane.pia.exception.LastAdminDeletingException;
import com.vane.pia.service.UserManager;
import com.vane.pia.utils.LongParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@Slf4j
public class UsersController {

    @Autowired
    private UserManager userManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }


    @GetMapping("/admin/users")
    public String index(Model model) {
        List<User> users = userManager.getUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/admin/users")
    public String deleteUser(@RequestParam(value = "userId") String userId){
        try {
            userManager.deleteUserById(LongParser.parseLong(userId));
        } catch (LastAdminDeletingException e) {
            log.warn("Attempt to delete last admin");
            return "redirect:/admin/users?adminDeleteError";
        }
        log.info("Delete user with id: " + userId);
        return "redirect:/admin/users";
    }
}

