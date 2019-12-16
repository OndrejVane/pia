package com.vane.pia.service;

import com.vane.pia.dao.CompanyRepository;
import com.vane.pia.dao.ContactRepository;
import com.vane.pia.domain.Company;
import com.vane.pia.domain.Contact;
import com.vane.pia.exception.ContactNotFoundException;
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
            Contact contact1 = new Contact("Alza.cz a.s.",
                    "Jankovcova",
                    "1522/53",
                    "Praha 7 - Holešovice",
                    "17000",
                    "27082440",
                    "CZ27082440",
                    "1265098001",
                    "5500");
            contact1.setCompany(company);
            this.addContact(contact1);

            log.info("Creating contact 2");
            Contact contact2 = new Contact("CZC.cz s.r.o.",
                    "U Garáží",
                    "1611/1",
                    "Praha - Holešovice",
                    "17000",
                    "25655701",
                    "CZ25655701",
                    "0117766173",
                    "0300");
            contact2.setCompany(company);
            this.addContact(contact2);

            log.info("Creating contact 3");
            Contact contact3 = new Contact("ELFETEX, spol. s r.o.",
                    "Hřbitovní",
                    "31a",
                    "Plzeň",
                    "31216",
                    "40524485",
                    "CZ40524485",
                    "10004371",
                    "0100");
            contact3.setCompany(company);
            this.addContact(contact3);
        }

    }

    @Override
    public void addContact(Contact contact) {
        // set company to contact
        contact.setCompany(companyRepository.findAll().iterator().next());
        contact.setDeleted(false);
        contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new LinkedList<>();
        Iterable<Contact> foundContacts = this.contactRepository.findAll();
        for (Contact contact : foundContacts){
            if(!contact.isDeleted()){
                contacts.add(contact);
            }
        }
        return contacts;
    }

    @Override
    public void deleteContactById(Long id) {
        Contact contact = this.findContactById(id);
        if(contact == null){
            throw new ContactNotFoundException(id);
        }
        contact.setDeleted(true);
        this.contactRepository.save(contact);
    }

    @Override
    public Contact findContactById(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isEmpty() || contact.get().isDeleted()) {
            throw new ContactNotFoundException(id);
        }
        return contact.get();
    }
}
