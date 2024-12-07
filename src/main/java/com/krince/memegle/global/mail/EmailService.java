package com.krince.memegle.global.mail;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendUserAuthenticationEmail(String email, String authenticationCode) throws MessagingException;
}
