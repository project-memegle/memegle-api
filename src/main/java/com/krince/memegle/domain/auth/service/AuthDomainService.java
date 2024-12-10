package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.EmailAuthenticationCodeDto;
import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthDomainService {

    void validateAuthenticationCode(EmailAuthenticationCodeDto emailAuthenticationCodeDto);

    EmailAuthentication registEmailAuthentication(EmailAuthentication emailAuthentication);
}
