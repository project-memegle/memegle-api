package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.global.constant.AuthenticationType;
import jakarta.mail.MessagingException;

public interface AuthApplicationService {

    void sendAuthenticationMail(UserAuthenticationDto userAuthenticationDto) throws MessagingException;
}
