package com.vane.pia.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Secured({"ROLE_USER", "ROLE_SECRETARY", "ROLE_ACCOUNTANT"})
public class BillsController {

    @GetMapping("/bills")
    public String index() {
        return "bills";
    }

}

