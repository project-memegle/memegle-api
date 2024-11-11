package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import jakarta.mail.MessagingException;

public interface AuthService {

    String sendAuthenticationMail(UserAuthenticationDto userAuthenticationDto) throws MessagingException;
}
