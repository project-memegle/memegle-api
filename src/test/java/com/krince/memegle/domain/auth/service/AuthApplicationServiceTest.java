package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import com.krince.memegle.domain.auth.repository.EmailAuthenticationRepository;
import com.krince.memegle.domain.auth.repository.fake.FakeEmailAuthenticationRepository;
import com.krince.memegle.global.constant.AuthenticationType;
import com.krince.memegle.global.exception.InvalidAuthenticationCodeException;
import com.krince.memegle.global.exception.NoSuchAuthenticationCodeException;
import com.krince.memegle.global.mail.EmailDomainService;
import com.krince.memegle.global.mail.fake.FakeEmailDomainService;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("test")
@Tag("unitTest")
@DisplayName("인증 서비스 테스트(AuthService)")
class AuthApplicationServiceTest {

    static AuthApplicationService authApplicationService;
    static AuthDomainService authDomainService;

    static EmailAuthenticationRepository emailAuthenticationRepository;

    static EmailDomainService emailDomainService;

    @BeforeAll
    static void setUp() {
        emailAuthenticationRepository = new FakeEmailAuthenticationRepository();
        emailDomainService = new FakeEmailDomainService();
        authDomainService = new AuthDomainServiceImpl(emailAuthenticationRepository);

        authApplicationService = new AuthApplicationServiceImpl(authDomainService, emailDomainService);
    }

    @BeforeEach
    void beforeEach() {
        emailAuthenticationRepository.deleteAll();
    }

    @Test
    @DisplayName("인증 이메일 전송 테스트")
    void sendAuthenticationMail() throws Exception {
        //given
        UserAuthenticationDto authenticationDto = UserAuthenticationDto.builder()
                .authenticationType(AuthenticationType.SIGN_UP)
                .build();

        //when, then
        authApplicationService.sendAuthenticationMail(authenticationDto);
    }
}