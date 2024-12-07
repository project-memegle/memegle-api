package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.global.constant.AuthenticationType;
import com.krince.memegle.global.exception.NoSuchAuthenticationCodeException;
import jakarta.mail.MessagingException;

public interface AuthService {

    void sendAuthenticationMail(UserAuthenticationDto userAuthenticationDto) throws MessagingException;

    void validateAuthenticationCode(String email, String authenticationCode, AuthenticationType authenticationType);
}
