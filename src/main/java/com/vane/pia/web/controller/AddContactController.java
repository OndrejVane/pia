package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import com.vane.pia.domain.Contact;
import com.vane.pia.service.ContactManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasRole('ACCOUNTANT')")
public class AddContactController {

    @Autowired
    ContactManager contactManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(Pages.ADD_CONTACT_PAGE)
    public String showNexContactPage(Model model) {

        Contact contact = new Contact();
        model.addAttribute("contact", contact);

        return Pages.ADD_CONTACT_TEMPLATE;
    }

    @PreAuthorize("hasRole('ACCOUNTANT')")
    @PostMapping(Pages.ADD_CONTACT_PAGE)
    public String addContact(@Valid Contact newContact,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.warn("BINDING RESULT ERROR");
            return Pages.ADD_CONTACT_TEMPLATE;
        }
        contactManager.addContact(newContact);

        return Pages.REDIRECT_CONTACTS_PAGE_WITH_SUCCESS;
    }

}
