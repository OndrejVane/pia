package com.vane.pia.service;

import com.vane.pia.dao.RoleRepository;
import com.vane.pia.dao.UserRepository;
import com.vane.pia.domain.Role;
import com.vane.pia.domain.User;
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

    // normal secretary
    private static final String SECRETARY_NAME = "qwer4444";

    private final PasswordEncoder encoder;

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    @Autowired
    private MailService mailService;

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
    public boolean addUser(User user) {
        if (this.userRepo.findByUsername(user.getUsername()) != null) {
            log.error("User already exists!");
            return false;
        }

        // send mail async
        //CompletableFuture.runAsync(() -> mailService.sendRegistrationMail(user));

        String hashed = this.encoder.encode(user.getPassword());
        user.setPassword(hashed);
        this.userRepo.save(user);
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
        userRepo.save(user);
        log.info("User " + user.getUsername() + " has been updated");
    }

    @Override
    public void changePasswordToUser(Long userId, String newPassword) {
        User user = this.findUserById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);
        log.info("User password for user " + user.getUsername() + "has been successfully changed");
    }

    @EventListener(classes = {
            ContextRefreshedEvent.class
    })
    @Order(2)
    @Transactional
    public void setup() {
        if (this.userRepo.count() == 0) {
            log.info("No admin present, creating admin.");
            User newAdmin = new User(ADMIN_USER_NAME, DEFAULT_PASSWORD, "admin", "admin", "123456789", "Ing.", "Street",
                    "City", 123, "25219", "Vane1@seznam.cz", "+420123456789", "1234567890123456", "1234567890123456"
                    , "5500");
            this.addUser(newAdmin);
            User admin = this.userRepo.findByUsername(ADMIN_USER_NAME);
            Role roleAdmin = this.roleRepo.findByCode("ADMIN");
            admin.getRoles().add(roleAdmin);
            this.userRepo.save(admin);

            log.info("No user present, creating user.");
            User newUser = new User(USER_NAME, DEFAULT_PASSWORD, "user", "user", "123456789", "Ing.", "Street",
                    "City", 123, "25219", "Vane1@seznam.cz", "+420123456789", "1234567890123456", "1234567890123456"
                    , "5500");
            newUser.setUsername(USER_NAME);
            this.addUser(newUser);
            User user = this.userRepo.findByUsername(USER_NAME);
            Role roleUser = this.roleRepo.findByCode("USER");
            user.getRoles().add(roleUser);
            this.userRepo.save(user);

            log.info("No accountant present, creating accountant.");
            User newAccountant = new User(ACCOUNTANT_NAME, DEFAULT_PASSWORD, "accountant", "accountant", "123456789", "Ing.", "Street",
                    "City", 123, "25219", "Vane1@seznam.cz", "+420123456789", "1234567890123456", "1234567890123456"
                    , "5500");
            this.addUser(newAccountant);
            User accountant = this.userRepo.findByUsername(ACCOUNTANT_NAME);
            Role roleAccountant = this.roleRepo.findByCode("ACCOUNTANT");
            accountant.getRoles().add(roleAccountant);
            this.userRepo.save(accountant);

            log.info("No secretary present, creating secretary.");
            User newSecretary = new User(SECRETARY_NAME, DEFAULT_PASSWORD, "secretary", "secretary", "123456789", "Ing.", "Street",
                    "City", 123, "25219", "Vane1@seznam.cz", "+420123456789", "1234567890123456", "1234567890123456"
                    , "5500");
            this.addUser(newSecretary);
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
        userRepo.deleteUserById(id);
    }


    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid Id!");
        }
        return user.get();
    }

    @Override
    public boolean checkUserId(Long id) {
        return userRepo.findById(id).isPresent();
    }

}
