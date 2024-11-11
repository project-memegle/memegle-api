package com.krince.memegle.global.mail;

import jakarta.mail.MessagingException;

public interface EmailService {

    String sendUserAuthenticationEmail(String email) throws MessagingException;
}
