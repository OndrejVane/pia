package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import com.vane.pia.domain.Contact;
import com.vane.pia.exception.UserNotFoundException;
import com.vane.pia.service.ContactManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String index(Model model) {
        List<Contact> contacts = contactManager.getAllContacts();
        model.addAttribute("contacts", contacts);
        return Pages.CONTACTS_TEMPLATE;
    }

    @PostMapping(Pages.CONTACTS_PAGE)
    public String deleteUser(@RequestParam(value = "contact") String contactId){
        // TODO zkontrolovat, jestli není tento kontakt u nějaké faktury, pokud ano, tak nelze smazat
        contactManager.deleteContactById(Long.parseLong(contactId));
        log.info("Delete contact with id: " + contactId);
        return "redirect:/admin/users";
    }
}
