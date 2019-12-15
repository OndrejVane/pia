package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import com.vane.pia.domain.Company;
import com.vane.pia.service.CompanyManager;
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

import javax.validation.Valid;

@Controller
@Slf4j
public class CompanyController {


    @Autowired
    CompanyManager companyManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(Pages.COMPANY_PAGE)
    public String showCompany(Model model) {
        model.addAttribute("company", companyManager.getCompany());
        return Pages.COMPANY_TEMPLATE;
    }

    @PostMapping(Pages.COMPANY_PAGE)
    public String editCompanyDetails(@Valid Company company, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.warn("BINDING RESULT ERROR");
            return Pages.COMPANY_TEMPLATE;
        }
        companyManager.updateCompany(company);

        return Pages.REDIRECT_COMPANY_PAGE_WITH_SUCCESS;
    }




}
