package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.EmailAuthenticationCodeDto;
import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.global.constant.AuthenticationType;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

public interface AuthApplicationService {

    void sendAuthenticationMail(UserAuthenticationDto userAuthenticationDto) throws MessagingException;

    void validateDuplicateMail(String email);

    void validateEmailAuthenticationCode(@Valid EmailAuthenticationCodeDto emailAuthenticationCodeDto);
}
