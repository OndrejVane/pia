package com.vane.pia.utils.mail;

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
}
