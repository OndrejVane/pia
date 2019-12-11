package com.vane.pia.dao;

import com.vane.pia.domain.Contact;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    @Transactional
    public void contactTest() {
        log.info("Testing contacts repository");
        Iterable<Contact> contacts = contactRepository.findAll();
        List<Contact> result = new ArrayList<>();
        contacts.forEach(result::add);

        Assertions.assertEquals(3, result.size());
        log.info("Three contacts found");

        log.info("Check company and bills for each contact");
        for (Contact contact : result){
            Assertions.assertNotNull(contact);
            Assertions.assertNotNull(contact.getCompany());
            Assertions.assertEquals("Aimtec a.s.", contact.getCompany().getName());

            Assertions.assertNotNull(contact.getBills());
            Assertions.assertEquals(2, contact.getBills().size());
            log.info("Contact: " + contact.getName() + " is correct");
        }

    }

}
