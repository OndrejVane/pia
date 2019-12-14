package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import com.vane.pia.domain.Contact;
import com.vane.pia.service.ContactManager;
import com.vane.pia.utils.LongParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ContactController {

    @Autowired
    ContactManager contactManager;

    @GetMapping(Pages.CONTACT_PAGE)
    public String showContactDetails(@PathVariable String contactId, Model model) {

        Contact contact = contactManager.findContactById(LongParser.parseLong(contactId));
        model.addAttribute("contact", contact);

        return Pages.EDIT_CONTACT_TEMPLATE;
    }

}
