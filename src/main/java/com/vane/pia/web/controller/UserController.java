package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import com.vane.pia.dao.UserRepository;
import com.vane.pia.domain.User;
import com.vane.pia.model.WebCredentials;
import com.vane.pia.service.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserManager userManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(Pages.USER_DETAIL_PAGE)
    public String showUserDetail(Model model) {
        WebCredentials userDetails = (WebCredentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("user", currentUser);
        return Pages.USER_DETAIL_TEMPLATE;
    }


    @PostMapping(Pages.USER_DETAIL_EDIT_PAGE)
    public String updateUserDetails(@Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.warn("BINDING RESULT ERROR");
            return Pages.ADD_USER_TEMPLATE;
        }
        userManager.updateUserDetails(user, userManager.getCurrentUser().getId(), null);
        return Pages.REDIRECT_USER_PAGE_DETAILS_SUCCESS;
    }

    @PostMapping(Pages.USER_PASSWORD_EDIT_PAGE)
    public String changePassword(@RequestParam(value = "oldPassword") String oldPassword,
                                 @RequestParam(value = "newPassword") String newPassword,
                                 @RequestParam(value = "confirmPassword") String confirmPassword) {

        if (!userManager.checkPassword(oldPassword)) {
            log.warn("Old password is not correct");
            return Pages.REDIRECT_USER_PAGE_PASSWORD_ERROR_INCORRECT;
        }

        if (!newPassword.equals(confirmPassword)) {
            log.warn("Confirm password is not same");
            return Pages.REDIRECT_USER_PAGE_PASSWORD_ERROR_CONFIRM;
        }

        userManager.changePasswordToUser(userManager.getCurrentUser().getId(), newPassword);
        return Pages.REDIRECT_USER_PAGE_PASSWORD_SUCCESS;
    }

}

