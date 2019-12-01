package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InformationController {

    @GetMapping(Pages.INFORMATION_PAGE)
    public String login() {
        return Pages.INFORMATION_PAGE_TEMPLATE;
    }

}
