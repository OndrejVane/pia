package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(Pages.HOME_PAGE)
    public String index() {
        return Pages.HOME_PAGE_TEMPLATE;
    }

}
