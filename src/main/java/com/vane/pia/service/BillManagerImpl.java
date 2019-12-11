package com.vane.pia.service;

import com.vane.pia.dao.*;
import com.vane.pia.domain.Bill;
import com.vane.pia.domain.Company;
import com.vane.pia.domain.Contact;
import com.vane.pia.domain.User;
import com.vane.pia.model.WebCredentials;
import com.vane.pia.utils.Calculator;
import com.vane.pia.utils.generator.Generator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BillManagerImpl implements BillManager {

    private final BillRepository billRepository;
    private final CompanyRepository companyRepository;
    private final ContactRepository contactRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public BillManagerImpl(BillRepository billRepository,
                           CompanyRepository companyRepository,
                           ContactRepository contactRepository,
                           ItemRepository itemRepository,
                           UserRepository userRepository) {
        this.billRepository = billRepository;
        this.companyRepository = companyRepository;
        this.contactRepository = contactRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Bill> getBills() {
        List<Bill> retVal = new LinkedList<>();
        this.billRepository.findAll().forEach(retVal::add);
        return retVal;
    }

    @Override
    public Bill getBillById(Long id){
        Optional<Bill> result = this.billRepository.findById(id);
        if(result.isEmpty()){
            log.error("Bill not found");
        }

        Bill bill = result.get();
        Calculator.calculateFieldsForBill(bill);
        return bill;
    }

    @Override
    public void addBill(Bill bill) {
        if (bill.getUser() == null) {
            bill.setUser(getCurrentUser());
        }

        // get number of bill from company
        Company company = this.companyRepository.findAll().iterator().next();
        String billNumber = Generator.generateBillNumber(company.getNumberOfBills() + 1, bill.getIsAccepted());
        bill.setBillNumber(billNumber);

        billRepository.save(bill);

        // increment number of bills in company counter
        company.setNumberOfBills(company.getNumberOfBills() + 1);
        this.companyRepository.save(company);
    }

    @EventListener(classes = {
            ContextRefreshedEvent.class
    })
    @Order(5)
    @Transactional
    public void setup() {
        if (billRepository.count() == 0) {

            log.info("No Bills found");
            Company company = companyRepository.findAll().iterator().next();

            log.info("Creating Bill 1 and Bill 2 for contact 1");
            Contact contact1 = contactRepository.findByName("Contact 1");
            User user1 = userRepository.findByUsername("qwer3333");
            Bill newBill = new Bill("Bill 1",
                    "Prodej nějakého zboží",
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(10),
                    true);
            newBill.setCompany(company);
            newBill.setContact(contact1);
            newBill.setUser(user1);
            this.addBill(newBill);

            Bill bill2 = new Bill("Bill 2",
                    "Nákup nějakého zboží",
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(15),
                    false);
            bill2.setCompany(company);
            bill2.setContact(contact1);
            bill2.setUser(user1);
            this.addBill(bill2);

            log.info("Creating Bill 3 and Bill 4 for contact 2");
            Contact contact2 = contactRepository.findByName("Contact 2");
            User user2 = userRepository.findByUsername("qwer2222");
            Bill bill3 = new Bill("Bill 3",
                    "Nákup nějakého zboží",
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(15),
                    false);
            bill3.setCompany(company);
            bill3.setContact(contact2);
            bill3.setUser(user2);
            this.addBill(bill3);

            Bill bill4 = new Bill("Bill 4",
                    "Nákup nějakého zboží",
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(15),
                    false);
            bill4.setCompany(company);
            bill4.setContact(contact2);
            bill4.setUser(user2);
            this.addBill(bill4);


            log.info("Creating Bill 5 and Bill 6 for contact 2");
            Contact contact3 = contactRepository.findByName("Contact 3");
            User user3 = userRepository.findByUsername("qwer1111");
            Bill bill5 = new Bill("Bill 5",
                    "Nákup nějakého zboží",
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(15),
                    false);
            bill5.setCompany(company);
            bill5.setContact(contact3);
            bill5.setUser(user3);
            this.addBill(bill5);

            Bill bill6 = new Bill("Bill 6",
                    "Nákup nějakého zboží",
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(15),
                    false);
            bill6.setCompany(company);
            bill6.setContact(contact3);
            bill6.setUser(user3);
            this.addBill(bill6);
        }

    }

    private User getCurrentUser() {
        WebCredentials webCredentials = (WebCredentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(webCredentials.getUsername());
    }
}
