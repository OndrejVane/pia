package com.vane.pia.dao;

import com.vane.pia.domain.Bill;
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
class BillRepositoryTest {

    @Autowired
    private BillRepository billrepository;

    @Test
    @Transactional
    void billTest() {
        log.info("Testing bills repository");
        Iterable<Bill> bills = billrepository.findAll();
        List<Bill> result = new ArrayList<>();
        bills.forEach(result::add);

        Assertions.assertEquals(6, result.size());
        log.info("Six bills found");

        log.info("Check company and contact for each contact");
        for (Bill bill : result) {
            Assertions.assertNotNull(bill);
            Assertions.assertNotNull(bill.getCompany());
            Assertions.assertEquals("Aimtec a.s.", bill.getCompany().getName());

            Assertions.assertNotNull(bill.getContact());

            log.info("Bill: " + bill.getName() + " has one company and one contact");
        }

        log.info("Check number of items in first bill is 4");
        Bill bill1 = billrepository.findByName("Bill 1");
        Assertions.assertNotNull(bill1);
        Assertions.assertEquals(4, bill1.getItems().size());
        log.info("Check if bill 1 has some user");
        Assertions.assertNotNull(bill1.getUser());


        log.info("Check number of items in second bill is 2");
        Bill bill2 = billrepository.findByName("Bill 2");
        Assertions.assertNotNull(bill2);
        Assertions.assertEquals(2, bill2.getItems().size());
        log.info("Check if bill 2 has some user");
        Assertions.assertNotNull(bill2.getUser());

    }

}
