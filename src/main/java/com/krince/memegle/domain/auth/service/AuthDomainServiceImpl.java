package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.EmailAuthenticationCodeDto;
import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import com.krince.memegle.domain.auth.repository.EmailAuthenticationRepository;
import com.krince.memegle.global.exception.InvalidAuthenticationCodeException;
import com.krince.memegle.global.exception.NoSuchAuthenticationCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDomainServiceImpl implements AuthDomainService {

    private final EmailAuthenticationRepository emailAuthenticationRepository;

    @Override
    public void validateAuthenticationCode(EmailAuthenticationCodeDto emailAuthenticationCodeDto) {
        EmailAuthentication emailAuthentication = emailAuthenticationRepository.findByEmailAndAuthenticationType(emailAuthenticationCodeDto.getEmail(), emailAuthenticationCodeDto.getAuthenticationType())
                .orElseThrow(NoSuchAuthenticationCodeException::new);

        validateMatchesAuthenticationCode(emailAuthenticationCodeDto.getAuthenticationCode(), emailAuthentication);
    }

    @Override
    public EmailAuthentication registEmailAuthentication(EmailAuthentication emailAuthentication) {
        return emailAuthenticationRepository.save(emailAuthentication);
    }

    private void validateMatchesAuthenticationCode(String authenticationCode, EmailAuthentication emailAuthentication) {
        boolean isMatchesAuthenticationCode = emailAuthentication.getAuthenticationCode().equals(authenticationCode);

        if (!isMatchesAuthenticationCode) {
            throw new InvalidAuthenticationCodeException();
        }
    }
}