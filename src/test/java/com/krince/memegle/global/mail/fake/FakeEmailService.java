package com.krince.memegle.global.mail.fake;

import com.krince.memegle.global.mail.EmailService;
import jakarta.mail.MessagingException;

public class FakeEmailService implements EmailService {

    @Override
    public void sendUserAuthenticationEmail(String email, String authenticationCode) throws MessagingException {
    }
}
