package com.vane.pia.dao;

import com.vane.pia.domain.Bill;
import com.vane.pia.domain.Company;
import com.vane.pia.domain.Contact;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Slf4j
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @Transactional
    public void companyTest(){
        log.info("Testing company repository");
        Company company = companyRepository.findByName("Aimtec a.s.");
        Assertions.assertNotNull(company);
        log.info("Company found");
        Assertions.assertEquals("Aimtec a.s.", company.getName());
        log.info("Company name is right");

        log.info("Check company contacts");
        List<Contact> contacts = company.getContacts();
        Assertions.assertNotNull(contacts);
        Assertions.assertEquals(3, contacts.size());
        log.info("Three contacts found, correct");

        log.info("Check company bills");
        List<Bill> bills = company.getBills();
        Assertions.assertNotNull(bills);
        Assertions.assertEquals(6, bills.size());
        log.info("Six bills found, correct");
    }

    @Test
    void companyManipulationTest(){
        log.info("Company created");
        Company company = new Company("Company",
                "Hálkova",
                "1203/32",
                "Plzeň",
                "30100",
                "25201816",
                "CZ25201816",
                0,
                "1234567890123456",
                "5500");
        companyRepository.save(company);
        log.info("New company saved");

        Iterable<Company> companiesFound = companyRepository.findAll();
        List<Company> result = new ArrayList<>();
        companiesFound.forEach(result::add);
        Assertions.assertEquals(2, result.size());
        log.info("Two companies found");

        log.info("Check company with name Company has no contacts and bills");
        Company companyCompany = companyRepository.findByName("Company");
        Assertions.assertNotNull(companyCompany);
        log.info("Deleting company with name Company");
        companyRepository.delete(companyCompany);

        log.info("Check number of companies equals one");
        companiesFound = companyRepository.findAll();
        result = new ArrayList<>();
        companiesFound.forEach(result::add);
        Assertions.assertEquals(1, result.size());
    }

}
