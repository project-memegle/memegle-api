package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import com.krince.memegle.domain.auth.repository.EmailAuthenticationRepository;
import com.krince.memegle.global.constant.AuthenticationType;
import com.krince.memegle.global.exception.InvalidAuthenticationCodeException;
import com.krince.memegle.global.exception.NoSuchAuthenticationCodeException;
import com.krince.memegle.global.mail.EmailService;
import com.krince.memegle.util.RandomCodeUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmailAuthenticationRepository emailAuthenticationRepository;
    private final EmailService emailService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendAuthenticationMail(UserAuthenticationDto userAuthenticationDto) throws MessagingException {
        String email = userAuthenticationDto.getEmail();
        String userName = userAuthenticationDto.getUserName();
        AuthenticationType authenticationType = userAuthenticationDto.getAuthenticationType();
        String authenticationCode = RandomCodeUtil.generateRandomCode();

        emailService.sendUserAuthenticationEmail(email, authenticationCode);

        EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                .id(email)
                .email(email)
                .userName(userName)
                .authenticationCode(authenticationCode)
                .authenticationType(authenticationType)
                .build();

        emailAuthenticationRepository.save(emailAuthentication);
    }

    @Override
    public void validateAuthenticationCode(String email, String authenticationCode, AuthenticationType authenticationType) {
        EmailAuthentication emailAuthentication = emailAuthenticationRepository.findByEmailAndAuthenticationType(email, authenticationType)
                .orElseThrow(NoSuchAuthenticationCodeException::new);

        validateMatchesAuthenticationCode(authenticationCode, emailAuthentication);
    }

    private void validateMatchesAuthenticationCode(String authenticationCode, EmailAuthentication emailAuthentication) {
        boolean isMatchesAuthenticationCode = emailAuthentication.getAuthenticationCode().equals(authenticationCode);

        if (!isMatchesAuthenticationCode) {
            throw new InvalidAuthenticationCodeException();
        }
    }
}
