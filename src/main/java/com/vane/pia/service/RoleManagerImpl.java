package com.vane.pia.service;

import com.vane.pia.dao.RoleRepository;
import com.vane.pia.domain.Role;
import com.vane.pia.domain.User;
import com.vane.pia.exception.UserNotFoundException;
import com.vane.pia.model.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class RoleManagerImpl implements RoleManager {

    private final RoleRepository roleRepo;

    @Autowired
    public RoleManagerImpl(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @EventListener(classes = {
            ContextRefreshedEvent.class
    })
    @Order(2)
    public void setup() {
        if (this.roleRepo.count() == 0) {
            log.info("No roles present, creating all roles defining in Roles enum");
            EnumSet.allOf(Roles.class).forEach(this::addRole);
        }
    }

    public void addRole(Roles roles) {
        Role role = new Role(roles.getCode(), roles.getName());
        this.roleRepo.save(role);
    }

    @Override
    public List<Role> getAllRolesUserDontHave(User user) {
        List<Role> roles = this.getRoles();
        if (roles.size() == user.getRoles().size()) {
            roles = new ArrayList<>();
        } else {
            for (Role role : roles) {
                if (user.getRoles().contains(role)) {
                    roles.remove(role);
                }
            }
        }
        return roles;
    }

    public List<Role> getRolesByIds(Long[] ids) {
        List<Role> roles = new ArrayList<>();
        if(ids != null){
            for (Long id : ids) {
                Optional<Role> role = roleRepo.findById(id);
                if(role.isEmpty()){
                    throw new UserNotFoundException(id);
                }
                roles.add(role.get());
            }
        }
        return roles;
    }

    @Override
    public List<Role> getRoles() {
        List<Role> retVal = new LinkedList<>();
        this.roleRepo.findAll().forEach(retVal::add);
        return retVal;
    }

}
