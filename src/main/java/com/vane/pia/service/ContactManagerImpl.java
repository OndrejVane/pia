package com.vane.pia.service;

import com.vane.pia.dao.CompanyRepository;
import com.vane.pia.dao.ContactRepository;
import com.vane.pia.domain.Company;
import com.vane.pia.domain.Contact;
import com.vane.pia.domain.User;
import com.vane.pia.exception.ContactNotFoundException;
import com.vane.pia.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactManagerImpl implements ContactManager {

    private ContactRepository contactRepository;
    private CompanyRepository companyRepository;

    @Autowired
    public ContactManagerImpl(ContactRepository contactRepository, CompanyRepository companyRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
    }

    @EventListener(classes = {
            ContextRefreshedEvent.class
    })
    @Order(4)
    @Transactional
    public void setup() {
        if (contactRepository.count() == 0) {
            log.info("No contacts found");
            Company company = companyRepository.findAll().iterator().next();

            log.info("Creating contact 1");
            //Contact contact1 = new Contact("Contact 1");
            Contact contact1 = new Contact("Contact 1",
                    "Masarykova",
                    "747",
                    "Rudná",
                    "25219",
                    "35301816",
                    "1234567890123456",
                    "5500");
            contact1.setCompany(company);
            this.addContact(contact1);

            log.info("Creating contact 2");
            //Contact contact2 = new Contact("Contact 2");
            Contact contact2 = new Contact("Contact 2",
                    "Borská",
                    "234",
                    "Plzeň",
                    "30100",
                    "35301098",
                    "CZ25201789",
                    "1234567890123456",
                    "5500");
            contact2.setCompany(company);
            this.addContact(contact2);

            log.info("Creating contact 3");
            //Contact contact3 = new Contact("Contact 3");
            Contact contact3 = new Contact("Contact 3",
                    "Němejcova",
                    "1204/6",
                    "Plzeň",
                    "30100",
                    "35301198",
                    "cz25201489",
                    "1234567890123456",
                    "5500");
            contact3.setCompany(company);
            this.addContact(contact3);
        }

    }

    @Override
    public void addContact(Contact contact) {
        contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new LinkedList<>();
        this.contactRepository.findAll().forEach(contacts::add);
        return contacts;
    }

    @Override
    public void deleteContactById(Long id) {
        this.contactRepository.deleteById(id);
    }

    @Override
    public Contact findContactById(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isEmpty()) {
            throw new ContactNotFoundException(id);
        }
        return contact.get();
    }
}
