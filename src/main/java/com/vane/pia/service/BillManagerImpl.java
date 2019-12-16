package com.vane.pia.service;

import com.vane.pia.dao.*;
import com.vane.pia.domain.*;
import com.vane.pia.exception.BillNotFoundException;
import com.vane.pia.exception.UserNotFoundException;
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
import java.time.LocalDate;
import java.util.ArrayList;
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
    private final TempItemRepository tempItemRepository;

    @Autowired
    public BillManagerImpl(BillRepository billRepository,
                           CompanyRepository companyRepository,
                           ContactRepository contactRepository,
                           ItemRepository itemRepository,
                           UserRepository userRepository,
                           TempItemRepository tempItemRepository) {
        this.billRepository = billRepository;
        this.companyRepository = companyRepository;
        this.contactRepository = contactRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.tempItemRepository = tempItemRepository;
    }

    @Override
    public List<Bill> getBills() {
        List<Bill> retVal = new LinkedList<>();
        Iterable<Bill> foundBills = this.billRepository.findAll();
        for (Bill bill : foundBills) {
            Calculator.calculateFieldsForBill(bill);
                retVal.add(bill);

        }
        return retVal;
    }

    @Override
    public Bill getBillById(Long id) {
        Optional<Bill> result = this.billRepository.findById(id);
        if (result.isEmpty()) {
            log.error("Bill not found");
            throw new BillNotFoundException(id);
        }

        Bill bill = result.get();
        Calculator.calculateFieldsForBill(bill);
        return bill;
    }

    @Override
    public List<Bill> getAllIssuedBills() {
        List<Bill> issuedBills = new ArrayList<>();
        for (Bill bill : getBills()) {
            if(!bill.getIsAccepted()){
                issuedBills.add(bill);
            }
        }
        return issuedBills;
    }

    @Override
    public List<Bill> getAllAcceptedBills() {
        List<Bill> acceptedBills = new ArrayList<>();
        for (Bill bill : getBills()) {
            if(bill.getIsAccepted()){
                acceptedBills.add(bill);
            }
        }
        return acceptedBills;
    }

    @Override
    @Transactional
    public void deleteBillById(Long id) {
        Optional<Bill> billOptional = this.billRepository.findById(id);
        if(billOptional.isEmpty()){
            log.warn("Bill not found");
            throw new BillNotFoundException(id);
        }
        Bill bill = billOptional.get();
        for (Item item : bill.getItems()){
            item.setDeleted(true);
            this.itemRepository.save(item);
        }
        bill.setDeleted(true);
        this.billRepository.save(bill);
    }

    @Override
    public void updateBill(Bill bill, Long billId) {
        Optional<Bill> foundBill = this.billRepository.findById(billId);
        if(foundBill.isEmpty()){
            throw new BillNotFoundException(billId);
        }

        Optional<User> foundUser = this.userRepository.findById(getCurrentUser().getId());
        if(foundUser.isEmpty()){
            throw  new UserNotFoundException(getCurrentUser().getId());
        }

        Bill dbBill = foundBill.get();
        dbBill.setName(bill.getName());
        dbBill.setDescription(bill.getDescription());
        dbBill.setContact(bill.getContact());
        dbBill.setIssuedDate(bill.getIssuedDate());
        dbBill.setDueDate(bill.getDueDate());
        dbBill.setIsAccepted(bill.getIsAccepted());
        dbBill.setIsCash(bill.getIsCash());
        dbBill.setIsPaid(bill.getIsPaid());
        dbBill.setDeleted(bill.isDeleted());
        dbBill.setUser(foundUser.get());

        this.billRepository.save(dbBill);

    }

    @Override
    @Transactional
    public void addBill(Bill bill) {
        if (bill.getUser() == null) {
            bill.setUser(getCurrentUser());
        }

        // get number of bill from company
        Company company = this.companyRepository.findAll().iterator().next();
        String billNumber = Generator.generateBillNumber(company.getNumberOfBills() + 1, bill.getIsAccepted());
        bill.setBillNumber(billNumber);
        bill.setCompany(company);

        billRepository.save(bill);

        // remove all temporary items
        tempItemRepository.deleteAllByUserId(getCurrentUser().getId());

        // increment number of bills in company counter
        company.setNumberOfBills(company.getNumberOfBills() + 1);
        this.companyRepository.save(company);

        // store all bill items
        Bill storedBill = this.billRepository.findByBillNumber(billNumber);
        for (Item item : bill.getItems()){
            item.setBill(storedBill);
            this.itemRepository.save(item);
        }
    }

    private void addSimpleBill(Bill bill){
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
            Contact contact1 = contactRepository.findByName("Alza.cz a.s.");
            User user1 = userRepository.findByUsername("qwer3333");
            Bill newBill = new Bill("Bill 1",
                    "Nákup a montáž počítaču",
                    true,
                    LocalDate.now(),
                    LocalDate.now().plusDays(10),
                    true,
                    false);
            newBill.setCompany(company);
            newBill.setContact(contact1);
            newBill.setUser(user1);
            this.addSimpleBill(newBill);

            Bill bill2 = new Bill("Bill 2",
                    "Nákup softwaru",
                    true,
                    LocalDate.now(),
                    LocalDate.now().plusDays(15),
                    false,
                    false);
            bill2.setCompany(company);
            bill2.setContact(contact1);
            bill2.setUser(user1);
            this.addSimpleBill(bill2);

            log.info("Creating Bill 3 and Bill 4 for contact 2");
            Contact contact2 = contactRepository.findByName("CZC.cz s.r.o.");
            User user2 = userRepository.findByUsername("qwer2222");
            Bill bill3 = new Bill("Bill 3",
                    "Nákup zásuvek",
                    true,
                    LocalDate.now(),
                    LocalDate.now().plusDays(15),
                    false,
                    true);
            bill3.setCompany(company);
            bill3.setContact(contact2);
            bill3.setUser(user2);
            this.addSimpleBill(bill3);

            Bill bill4 = new Bill("Bill 4",
                    "Prodej softwaru",
                    false,
                    LocalDate.now(),
                    LocalDate.now().plusDays(15),
                    false,
                    true);
            bill4.setCompany(company);
            bill4.setContact(contact2);
            bill4.setUser(user2);
            this.addSimpleBill(bill4);


            log.info("Creating Bill 5 and Bill 6 for contact 2");
            Contact contact3 = contactRepository.findByName("ELFETEX, spol. s r.o.");
            User user3 = userRepository.findByUsername("qwer1111");
            Bill bill5 = new Bill("Bill 5",
                    "Nákup zásuvek",
                    true,
                    LocalDate.now(),
                    LocalDate.now().plusDays(15),
                    false,
                    false);
            bill5.setCompany(company);
            bill5.setContact(contact3);
            bill5.setUser(user3);
            bill5.setDeleted(true);
            this.addSimpleBill(bill5);

            Bill bill6 = new Bill("Bill 6",
                    "Nákup kabelů",
                    true,
                    LocalDate.now(),
                    LocalDate.now().plusDays(15),
                    false,
                    true);
            bill6.setCompany(company);
            bill6.setContact(contact3);
            bill6.setUser(user3);
            this.addSimpleBill(bill6);
        }

    }

    private User getCurrentUser() {
        WebCredentials webCredentials = (WebCredentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(webCredentials.getUsername());
    }
}
