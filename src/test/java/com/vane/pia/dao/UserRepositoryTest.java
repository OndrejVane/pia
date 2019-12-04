package com.vane.pia.dao;

import com.vane.pia.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    @Transactional
    @Order(1)
    void testUserManipulation() {
        log.info("Testing accountant manipulation.");
        Assertions.assertEquals(3, userRepo.count());

        // admin tests
        log.info("Looking for admin.");
        User admin = userRepo.findByUsername("qwer1111");
        Assertions.assertNotNull(admin);
        log.info("Found admin.");
        Assertions.assertEquals(1, admin.getRoles().size());
        log.info("Admin has one role, removing.");
        admin.getRoles().remove(0);
        userRepo.save(admin);
        admin = userRepo.findByUsername("qwer1111");
        Assertions.assertEquals(0, admin.getRoles().size());
        log.info("Admin has no role now, OK.");

        // accountant tests
        log.info("Looking for accountant.");
        User user = userRepo.findByUsername("qwer2222");
        Assertions.assertNotNull(user);
        log.info("Found test.");
        Assertions.assertEquals(0, user.getRoles().size());
        log.info("User has no role");

        // accountant tests
        log.info("Looking for accountant.");
        User accountant = userRepo.findByUsername("qwer3333");
        Assertions.assertNotNull(accountant);
        log.info("Found test.");
        Assertions.assertEquals(1, accountant.getRoles().size());
        log.info("Accountant has one role, removing.");
        accountant.getRoles().remove(0);
        userRepo.save(accountant);
        accountant = userRepo.findByUsername("qwer3333");
        Assertions.assertEquals(0, accountant.getRoles().size());
        log.info("accountant has no role now, OK.");
    }

    @Test
    @Transactional
    @Order(2)
    void testUpdateUser() {
        log.info("Testing user update method");
        User user = userRepo.findByUsername("qwer1111");
        Assertions.assertNotNull(user);
        log.info("User found. Change some first name and last name");

        user.setFirstName("John");
        user.setLastName("Snow");

        userRepo.save(user);
        log.info("User saved with new first name and last name");

        User updatedUser = userRepo.findByUsername("qwer1111");

        Assertions.assertNotNull(user);
        Assertions.assertEquals("John", updatedUser.getFirstName());
        Assertions.assertEquals("Snow", updatedUser.getLastName());
        log.info("User has been successfully updated");

    }

    @Test
    @Transactional
    @Order(3)
    void deleteUserById() {
        User user = userRepo.findByUsername("qwer1111");
        Assertions.assertNotNull(user);
        log.info("User found");
        userRepo.deleteUserById(user.getId());
        log.info("User deleted");
        user = userRepo.findByUsername("qwer1111");
        Assertions.assertNull(user);
        log.info("User is null, deleted");
    }
}
