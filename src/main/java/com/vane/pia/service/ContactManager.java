package com.vane.pia.service;

import com.vane.pia.domain.Contact;

import java.util.List;

public interface ContactManager {

    List<Contact> getAllContacts();

    void deleteContactById(Long id);

    Contact findContactById(Long id);

    void addContact(Contact contact);
}
