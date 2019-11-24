package com.vane.pia.dao;

import com.vane.pia.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Slf4j
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Transactional
    public void testUserRoles(){
        log.info("Testing role manipulation");
        Assertions.assertEquals(4, roleRepository.count());

        // check admin role
        log.info("Test find role by code ADMIN");
        Role admin = roleRepository.findByCode("ADMIN");
        Assertions.assertNotNull(admin);
        log.info("Admin role found, check role name Admin");
        Assertions.assertEquals("Admins", admin.getName());
        log.info("Check number of users with admin role, should be one");
        Assertions.assertEquals(1, admin.getUsers().size());

        admin.getUsers().remove(0);
        roleRepository.save(admin);
        admin = roleRepository.findByCode("ADMIN");
        Assertions.assertEquals(0, admin.getUsers().size());

        // check user role
        log.info("Test find role by code USER");
        Role user = roleRepository.findByCode("USER");
        Assertions.assertNotNull(user);
        log.info("User role found, check role name User");
        Assertions.assertEquals("Users", user.getName());
        log.info("Check number of users with user role, should be one");
        Assertions.assertEquals(1, user.getUsers().size());

        user.getUsers().remove(0);
        roleRepository.save(user);
        user = roleRepository.findByCode("USER");
        Assertions.assertEquals(0, user.getUsers().size());

        // check accountant role
        log.info("Test find role by code ACCOUNTANT");
        Role accountant = roleRepository.findByCode("ACCOUNTANT");
        Assertions.assertNotNull(accountant);
        log.info("Accountant role found, check role name Accountants");
        Assertions.assertEquals("Accountants", accountant.getName());
        log.info("Check number of users with accountant role, should be one");
        Assertions.assertEquals(1, accountant.getUsers().size());

        accountant.getUsers().remove(0);
        roleRepository.save(accountant);
        accountant = roleRepository.findByCode("ACCOUNTANT");
        Assertions.assertEquals(0, accountant.getUsers().size());

        // check secretary role
        log.info("Test find role by code SECRETARY");
        Role secretary = roleRepository.findByCode("SECRETARY");
        Assertions.assertNotNull(secretary);
        log.info("Secretary role found, check role name Secretaries");
        Assertions.assertEquals("Secretaries", secretary.getName());
        log.info("Check number of users with secretary role, should be one");
        Assertions.assertEquals(1, secretary.getUsers().size());

        secretary.getUsers().remove(0);
        roleRepository.save(secretary);
        secretary = roleRepository.findByCode("SECRETARY");
        Assertions.assertEquals(0, secretary.getUsers().size());

    }
}