package com.vane.pia.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import com.vane.pia.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vane.pia.dao.RoleRepository;
import com.vane.pia.dao.UserRepository;
import com.vane.pia.domain.User;
import com.vane.pia.model.WebCredentials;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserManagerImpl implements UserManager, UserDetailsService {

	// admin user
	private static final String ADMIN_USER_NAME = "admin";
	private static final String ADMIN_PASSWORD = "admin";

	// normal user
	private static final String USER_NAME = "test";
	private static final String USER_PASSWORD = "test";

	// normal accountant
	private static final String ACCOUNTANT_NAME = "acc";
	private static final String ACCOUNTANT_PASSWORD = "acc";

	// normal secretary
	private static final String SECRETARY_NAME = "sec";
	private static final String SECRETARY_PASSWORD = "sec";

	private final PasswordEncoder encoder;

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;

	@Autowired
	public UserManagerImpl(PasswordEncoder encoder, UserRepository userRepo, RoleRepository roleRepo) {
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
	}

	@Override
	public List<User> getUsers() {
		List<User> retVal = new LinkedList<>();
		for (User user : this.userRepo.findAll()) {
			retVal.add(user);
		}
		return Collections.unmodifiableList(retVal);
	}

	@Override
	public void addUser(String username, String password) {
		if (this.userRepo.findByUsername(username) != null) {
			throw new IllegalArgumentException("User already exists!");
		}

		String hashed = this.encoder.encode(password);
		User user = new User(username, hashed);
		this.userRepo.save(user);
	}

	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(2)
	@Transactional
	public void setup() {
		if (this.userRepo.count() == 0) {
			log.info("No admin present, creating admin.");
			this.addUser(ADMIN_USER_NAME, ADMIN_PASSWORD);
			User admin = this.userRepo.findByUsername(ADMIN_USER_NAME);
			Role roleAdmin = this.roleRepo.findByCode("ADMIN");
			admin.getRoles().add(roleAdmin);
			this.userRepo.save(admin);

			log.info("No user present, creating user.");
			this.addUser(USER_NAME, USER_PASSWORD);
			User user = this.userRepo.findByUsername(USER_NAME);
			Role roleUser = this.roleRepo.findByCode("USER");
			user.getRoles().add(roleUser);
			this.userRepo.save(user);

			log.info("No accountant present, creating accountant.");
			this.addUser(ACCOUNTANT_NAME, ACCOUNTANT_PASSWORD);
			User accountant = this.userRepo.findByUsername(ACCOUNTANT_NAME);
			Role roleAccountant = this.roleRepo.findByCode("ACCOUNTANT");
			accountant.getRoles().add(roleAccountant);
			this.userRepo.save(accountant);

			log.info("No secretary present, creating secretary.");
			this.addUser(SECRETARY_NAME, SECRETARY_PASSWORD);
			User secretary = this.userRepo.findByUsername(SECRETARY_NAME);
			Role roleSecretary = this.roleRepo.findByCode("SECRETARY");
			secretary.getRoles().add(roleSecretary);
			this.userRepo.save(secretary);
		}
	}

	private String toSpringRole(Role role) {
		return "ROLE_" + role.getCode();
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username!");
		}

		WebCredentials creds = new WebCredentials(user.getUsername(), user.getPassword());

		user.getRoles()
		.stream()
		.map(this::toSpringRole)
		.forEach(creds::addRole);

		return creds;
	}

}
