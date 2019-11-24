package com.vane.pia.dao;

import com.vane.pia.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Slf4j
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    @Transactional
    void testUserManipulation() {
        log.info("Testing accountant manipulation.");
        Assertions.assertEquals(4, userRepo.count());

        // admin tests
        log.info("Looking for admin.");
        User admin = userRepo.findByUsername("admin");
        Assertions.assertNotNull(admin);
        log.info("Found admin.");
        Assertions.assertEquals(1, admin.getRoles().size());
        log.info("Admin has one role, removing.");
        admin.getRoles().remove(0);
        userRepo.save(admin);
        admin = userRepo.findByUsername("admin");
        Assertions.assertEquals(0, admin.getRoles().size());
        log.info("Admin has no role now, OK.");

        // accountant tests
        log.info("Looking for accountant.");
        User user = userRepo.findByUsername("test");
        Assertions.assertNotNull(user);
        log.info("Found test.");
        Assertions.assertEquals(1, user.getRoles().size());
        log.info("User has one role, removing.");
        user.getRoles().remove(0);
        userRepo.save(user);
        user = userRepo.findByUsername("test");
        Assertions.assertEquals(0, user.getRoles().size());
        log.info("accountant has no role now, OK.");

        // secretary tests
        log.info("Looking for accountant.");
        User secretary = userRepo.findByUsername("sec");
        Assertions.assertNotNull(secretary);
        log.info("Found secretary.");
        Assertions.assertEquals(1, secretary.getRoles().size());
        log.info("Secretary has one role, removing.");
        secretary.getRoles().remove(0);
        userRepo.save(secretary);
        secretary = userRepo.findByUsername("sec");
        Assertions.assertEquals(0, secretary.getRoles().size());
        log.info("Secretary has no role now, OK.");

        // accountant tests
        log.info("Looking for accountant.");
        User accountant = userRepo.findByUsername("acc");
        Assertions.assertNotNull(accountant);
        log.info("Found test.");
        Assertions.assertEquals(1, accountant.getRoles().size());
        log.info("Accountant has one role, removing.");
        accountant.getRoles().remove(0);
        userRepo.save(accountant);
        accountant = userRepo.findByUsername("acc");
        Assertions.assertEquals(0, accountant.getRoles().size());
        log.info("accountant has no role now, OK.");
    }
}