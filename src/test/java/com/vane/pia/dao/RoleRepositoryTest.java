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
        Assertions.assertEquals(2, roleRepository.count());

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

    }
}
