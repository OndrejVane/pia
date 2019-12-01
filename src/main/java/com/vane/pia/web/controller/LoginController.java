package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(Pages.LOGIN_PAGE)
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return Pages.FORWARD_HOME_PAGE;
        } else {
            return Pages.LOGIN_PAGE_TEMPLATE;
        }
    }

}
