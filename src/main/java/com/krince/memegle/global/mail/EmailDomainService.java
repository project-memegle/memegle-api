package com.krince.memegle.global.mail;

import jakarta.mail.MessagingException;

public interface EmailDomainService {

    void sendUserAuthenticationEmail(String email, String authenticationCode) throws MessagingException;
}
