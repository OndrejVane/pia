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
        log.info("Testing user manipulation.");
        Assertions.assertEquals(2, userRepo.count());

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

        // user tests
        log.info("Looking for user.");
        User user = userRepo.findByUsername("test");
        Assertions.assertNotNull(user);
        log.info("Found test.");
        Assertions.assertEquals(1, user.getRoles().size());
        log.info("User has one role, removing.");
        user.getRoles().remove(0);
        userRepo.save(user);
        user = userRepo.findByUsername("test");
        Assertions.assertEquals(0, user.getRoles().size());
        log.info("user has no role now, OK.");
    }
}