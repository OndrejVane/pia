package com.vane.pia.utils.mail;

import com.vane.pia.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class MailSenderImpl implements MailService {




    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
        log.info("Mail has been sent to " + to);
    }

    @Override
    public void sendRegistrationMail(User toUser) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toUser.getEmail());
        message.setSubject("New registration PIA");
        String textMessage = "Dear " + toUser.getFirstName() +
                " " + toUser.getLastName() + ",\n" +
                "you have been successfully register in web application PIA\n" +
                "for managing your bills. You can now log in HERE with credentials:\n" +
                "Username: " + toUser.getUsername() + "\n" +
                "Password: " + toUser.getPassword() + "\n\n" +
                "After login you can change your password in user settings.\n\n" +
                "Best wishes,\n" +
                "PIA administrator";
        message.setText(textMessage);
        javaMailSender.send(message);
        log.info("Registration mail has been send on " + toUser.getEmail());
    }
}
