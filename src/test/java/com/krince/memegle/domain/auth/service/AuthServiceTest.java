package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.repository.EmailAuthenticationRepository;
import com.krince.memegle.domain.auth.repository.fake.FakeEmailAuthenticationRepository;
import com.krince.memegle.global.mail.EmailService;
import com.krince.memegle.global.mail.fake.FakeEmailService;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

@Tags({
        @Tag("test"),
        @Tag("unitTest")
})
@DisplayName("인증 서비스 테스트(AuthService)")
class AuthServiceTest {

    static AuthService authService;

    static EmailAuthenticationRepository emailAuthenticationRepository;

    static EmailService emailService;

    @BeforeAll
    static void setUp() {
        emailService = new FakeEmailService();
        emailAuthenticationRepository = new FakeEmailAuthenticationRepository();
        authService = new AuthServiceImpl(emailAuthenticationRepository, emailService);
    }

    @Test
    @DisplayName("인증 이메일 전송 테스트")
    void sendAuthenticationMail() throws Exception {
        //given
        UserAuthenticationDto authenticationDto = UserAuthenticationDto.builder().build();

        //when
        String authCode = authService.sendAuthenticationMail(authenticationDto);

        //then
        assertThat(authCode).isEqualTo("1Q2W3E");
    }
}