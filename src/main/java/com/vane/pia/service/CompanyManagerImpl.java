package com.vane.pia.service;

import com.vane.pia.dao.CompanyRepository;
import com.vane.pia.domain.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class CompanyManagerImpl implements CompanyManager {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyManagerImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @EventListener(classes = {
            ContextRefreshedEvent.class
    })
    @Order(1)
    @Transactional
    public void setup() {
        if (companyRepository.count() == 0) {
            log.info("No company found");
            Company company = new Company("Aimtec a.s.",
                    "Hálkova",
                    "1203/32",
                    "Plzeň",
                    "30100",
                    "25201816",
                    "CZ25201816",
                    0,
                    "1234567890123456",
                    "5500");
            this.addCompany(company);
            log.info("One company created");
        }
    }

    @Override
    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Company getCompany() {
        return companyRepository.findAll().iterator().next();
    }

    @Override
    public void updateCompany(Company company) {
        Company foundCompany =  getCompany();

        company.setId(foundCompany.getId());
        company.setNumberOfBills(foundCompany.getNumberOfBills());

        companyRepository.save(company);
    }

}
