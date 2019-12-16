package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import com.vane.pia.domain.Contact;
import com.vane.pia.service.ContactManager;
import com.vane.pia.utils.LongParser;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class ContactController {

    @Autowired
    ContactManager contactManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(Pages.CONTACT_PAGE)
    public String showContactDetails(@PathVariable String contactId, Model model) {

        Contact contact = contactManager.findContactById(LongParser.parseLong(contactId));
        model.addAttribute("contact", contact);

        return Pages.EDIT_CONTACT_TEMPLATE;
    }

    @PreAuthorize("hasRole('ACCOUNTANT')")
    @PostMapping(Pages.CONTACT_PAGE)
    public String updateContact(@PathVariable String contactId,
                                @Valid Contact contact,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("BINDING RESULT ERROR");
            contact.setId(LongParser.parseLong(contactId));
            model.addAttribute("contact", contact);
            return Pages.EDIT_CONTACT_TEMPLATE;
        }

        contact.setId(LongParser.parseLong(contactId));
        contactManager.addContact(contact);

        return "redirect:/contacts/" + contactId + "?success";
    }

}
