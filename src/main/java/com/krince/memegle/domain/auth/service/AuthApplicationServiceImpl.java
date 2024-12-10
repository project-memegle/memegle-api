package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import com.krince.memegle.global.mail.EmailDomainService;
import com.krince.memegle.util.RandomCodeUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthApplicationServiceImpl implements AuthApplicationService {

    private final AuthDomainService authDomainService;
    private final EmailDomainService emailDomainService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendAuthenticationMail(UserAuthenticationDto userAuthenticationDto) throws MessagingException {
        String authenticationCode = RandomCodeUtil.generateRandomCode();
        emailDomainService.sendUserAuthenticationEmail(userAuthenticationDto.getEmail(), authenticationCode);

        EmailAuthentication emailAuthentication = EmailAuthentication.of(userAuthenticationDto, authenticationCode);
        authDomainService.registEmailAuthentication(emailAuthentication);
    }
}
