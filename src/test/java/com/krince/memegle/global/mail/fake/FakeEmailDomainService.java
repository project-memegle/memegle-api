package com.krince.memegle.global.mail.fake;

import com.krince.memegle.global.mail.EmailDomainService;
import jakarta.mail.MessagingException;

public class FakeEmailDomainService implements EmailDomainService {

    @Override
    public void sendUserAuthenticationEmail(String email, String authenticationCode) throws MessagingException {
    }
}
