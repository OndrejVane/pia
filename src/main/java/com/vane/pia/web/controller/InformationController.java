package com.vane.pia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InformationController {

    @GetMapping("/information")
    public String login() {
        return "information";
    }

}
