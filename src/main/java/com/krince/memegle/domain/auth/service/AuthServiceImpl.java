package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import com.krince.memegle.domain.auth.repository.EmailAuthenticationRepository;
import com.krince.memegle.global.constant.AuthenticationType;
import com.krince.memegle.global.mail.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmailAuthenticationRepository emailAuthenticationRepository;
    private final EmailService emailService;

    @Override
    public String sendAuthenticationMail(UserAuthenticationDto userAuthenticationDto) throws MessagingException {
        String email = userAuthenticationDto.getEmail();
        String userName = userAuthenticationDto.getUserName();
        AuthenticationType authenticationType = userAuthenticationDto.getAuthenticationType();

        String authenticationCode = emailService.sendUserAuthenticationEmail(email);

        EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                .id(email)
                .email(email)
                .userName(userName)
                .authenticationCode(authenticationCode)
                .authenticationType(authenticationType.getStringValue())
                .build();

        emailAuthenticationRepository.save(emailAuthentication);

        return authenticationCode;
    }
}
