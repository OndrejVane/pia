package com.vane.pia.service;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import com.vane.pia.domain.Role;
import com.vane.pia.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.vane.pia.dao.RoleRepository;
import lombok.extern.slf4j.Slf4j;

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
	@Order(1)
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
	public List<Role> getRoles() {
		List<Role> retVal = new LinkedList<>();
		this.roleRepo.findAll().forEach(retVal::add);
		return retVal;
	}

}
