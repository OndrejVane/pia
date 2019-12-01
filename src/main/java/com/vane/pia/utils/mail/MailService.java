package com.vane.pia.utils.mail;

import com.vane.pia.domain.User;

public interface MailService {

    void sendMessage(String to, String subject, String text);

    void sendRegistrationMail(User toUser);
}
