package com.vane.pia.utils.mail;

public interface MailService {

    void sendMessage(String to, String subject, String text);
}
