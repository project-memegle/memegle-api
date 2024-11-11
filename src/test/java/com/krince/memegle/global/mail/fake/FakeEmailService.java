package com.krince.memegle.global.mail.fake;

import com.krince.memegle.global.mail.EmailService;
import jakarta.mail.MessagingException;

public class FakeEmailService implements EmailService {

    @Override
    public String sendUserAuthenticationEmail(String email) throws MessagingException {
        return "1Q2W3E";
    }
}
