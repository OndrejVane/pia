package com.vane.pia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BillsController {

    @GetMapping("/bills")
    public String index() {
        return "bills";
    }

}

