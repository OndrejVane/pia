package com.vane.pia.service;

import com.vane.pia.dao.CompanyRepository;
import com.vane.pia.dao.RoleRepository;
import com.vane.pia.dao.UserRepository;
import com.vane.pia.domain.Company;
import com.vane.pia.domain.Role;
import com.vane.pia.domain.User;
import com.vane.pia.exception.UserNotFoundException;
import com.vane.pia.model.Roles;
import com.vane.pia.model.WebCredentials;
import com.vane.pia.utils.mail.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserManagerImpl implements UserManager, UserDetailsService {

    private static final String DEFAULT_PASSWORD = "1234";

    // admin user
    private static final String ADMIN_USER_NAME = "qwer1111";

    // normal user
    private static final String USER_NAME = "qwer2222";

    // normal accountant
    private static final String ACCOUNTANT_NAME = "qwer3333";

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    public UserManagerImpl(PasswordEncoder encoder,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           CompanyRepository companyRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<User> getUsers() {
        List<User> retVal = new LinkedList<>();
        for (User user : this.userRepository.findAll()) {
            retVal.add(user);
        }
        return Collections.unmodifiableList(retVal);
    }

    @Override
    public boolean addUser(User user) {
        if (this.userRepository.findByUsername(user.getUsername()) != null) {
            log.error("User already exists!");
            return false;
        }

        // send mail async
        //CompletableFuture.runAsync(() -> mailService.sendRegistrationMail(user));

        String hashed = this.encoder.encode(user.getPassword());
        user.setPassword(hashed);
        this.userRepository.save(user);
        log.debug("User successfully created");
        return true;
    }

    public void updateUserDetails(User user, Long id) {
        User updatedUser = findUserById(id);
        if (updatedUser == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }
        user.setCreateDateTime(updatedUser.getCreateDateTime());
        user.setPassword(updatedUser.getPassword());
        user.setUsername(updatedUser.getUsername());
        user.setId(updatedUser.getId());
        user.setRoles(updatedUser.getRoles());
        userRepository.save(user);
        log.info("User " + user.getUsername() + " has been updated");
    }

    @Override
    public void changePasswordToUser(Long userId, String newPassword) {
        User user = this.findUserById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        log.info("User password for user " + user.getUsername() + "has been successfully changed");
    }

    @EventListener(classes = {
            ContextRefreshedEvent.class
    })
    @Order(3)
    @Transactional
    public void setup() {
        if (this.userRepository.count() == 0) {
            Company company = companyRepository.findAll().iterator().next();

            log.info("No admin present, creating admin.");
            User newAdmin = new User(ADMIN_USER_NAME, DEFAULT_PASSWORD, "admin", "admin", "123456789", "Ing.", "Street",
                    "City", "123", "25219", "Vane1@seznam.cz", "+420123456789", "1234567890123456", "1234567890123456"
                    , "5500");
            this.addUser(newAdmin);
            User admin = this.userRepository.findByUsername(ADMIN_USER_NAME);
            Role roleAdmin = this.roleRepository.findByCode("ADMIN");
            admin.getRoles().add(roleAdmin);
            admin.getCompanies().add(company);
            this.userRepository.save(admin);

            log.info("No user present, creating user.");
            User newUser = new User(USER_NAME, DEFAULT_PASSWORD, "user", "user", "123456789", "Ing.", "Street",
                    "City", "123", "25219", "Vane1@seznam.cz", "+420123456789", "1234567890123456", "1234567890123456"
                    , "5500");
            newUser.setUsername(USER_NAME);
            newUser.getCompanies().add(company);
            this.addUser(newUser);

            log.info("No accountant present, creating accountant.");
            User newAccountant = new User(ACCOUNTANT_NAME, DEFAULT_PASSWORD, "accountant", "accountant", "123456789", "Ing.", "Street",
                    "City", "123", "25219", "Vane1@seznam.cz", "+420123456789", "1234567890123456", "1234567890123456"
                    , "5500");
            this.addUser(newAccountant);
            User accountant = this.userRepository.findByUsername(ACCOUNTANT_NAME);
            Role roleAccountant = this.roleRepository.findByCode("ACCOUNTANT");
            accountant.getRoles().add(roleAccountant);
            accountant.getCompanies().add(company);
            this.userRepository.save(accountant);
        }
    }

    private String toSpringRole(Role role) {
        return "ROLE_" + role.getCode();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        WebCredentials webCredentials = new WebCredentials(user.getId(), user.getUsername(), user.getPassword(),
                user.getFirstName(), user.getLastName());

        user.getRoles()
                .stream()
                .map(this::toSpringRole)
                .forEach(webCredentials::addRole);

        return webCredentials;
    }

    @Override
    public WebCredentials getCurrentUser() {
        return (WebCredentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public boolean checkPassword(String rawPassword) {
        return encoder.matches(rawPassword, getCurrentUser().getPassword());
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        List<User> adminUsers = roleRepository.findByCode(Roles.ADMIN.getCode()).getUsers();
        if (adminUsers.size() == 1 && adminUsers.get(0).getId().equals(id)) {
            log.warn("Attempt to delete last admin in app.");
            return;
        }
        userRepository.deleteUserById(id);
    }


    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return user.get();
    }

    @Override
    public boolean checkUserId(Long id) {
        return userRepository.findById(id).isPresent();
    }

}
