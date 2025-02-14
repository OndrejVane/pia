package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import com.vane.pia.domain.Contact;
import com.vane.pia.exception.UserNotFoundException;
import com.vane.pia.service.ContactManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class ContactsController {

    @Autowired
    private ContactManager contactManager;

    @GetMapping(Pages.CONTACTS_PAGE)
    public String showAllContacts(Model model) {
        List<Contact> contacts = contactManager.getAllContacts();
        model.addAttribute("contacts", contacts);
        return Pages.CONTACTS_TEMPLATE;
    }

    @PreAuthorize("hasRole('ACCOUNTANT')")
    @PostMapping(Pages.CONTACTS_PAGE)
    public String deleteUser(@RequestParam(value = "contactId") String contactId){
        contactManager.deleteContactById(Long.parseLong(contactId));
        log.info("Delete contact with id: " + contactId);
        return Pages.REDIRECT_CONTACTS_PAGE;
    }
}
