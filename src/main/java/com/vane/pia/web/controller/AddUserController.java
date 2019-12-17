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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@Slf4j
public class AddUserController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private RoleManager roleManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(Pages.ADD_USER_PAGE)
    public String showAddUser(Model model) {
        User newUser = new User();
        model.addAttribute("selectRoles", roleManager.getRoles());
        model.addAttribute("user", newUser);
        return Pages.ADD_USER_TEMPLATE;
    }

    @PostMapping(Pages.ADD_USER_PAGE)
    public String addUser(@Valid User newUser,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            log.warn("BINDING RESULT ERROR");
            model.addAttribute("selectRoles", roleManager.getRoles());
            return Pages.ADD_USER_TEMPLATE;
        }
        boolean isSuccess = userManager.addUser(newUser);
        if (isSuccess) {
            return Pages.REDIRECT_USERS_PAGE_WITH_SUCCESS;
        }
        return Pages.ERROR_ADD_USER_PAGE;
    }

}

