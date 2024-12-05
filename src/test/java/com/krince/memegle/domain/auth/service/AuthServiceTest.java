package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import com.krince.memegle.domain.auth.repository.EmailAuthenticationRepository;
import com.krince.memegle.domain.auth.repository.fake.FakeEmailAuthenticationRepository;
import com.krince.memegle.global.constant.AuthenticationType;
import com.krince.memegle.global.exception.InvalidAuthenticationCodeException;
import com.krince.memegle.global.exception.NoSuchAuthenticationCodeException;
import com.krince.memegle.global.mail.EmailService;
import com.krince.memegle.global.mail.fake.FakeEmailService;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("test")
@Tag("unitTest")
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
        UserAuthenticationDto authenticationDto = UserAuthenticationDto.builder()
                .authenticationType(AuthenticationType.SIGN_UP)
                .build();

        //when
        String authCode = authService.sendAuthenticationMail(authenticationDto);

        //then
        assertThat(authCode).isEqualTo("1Q2W3E");
    }

    @Tag("develop")
    @Nested
    @DisplayName("이메일 인증코드 검증 유무 조회")
    class ValidateAuthenticationCode {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("이메일, 인증코드, 인증타입 모두 일치")
            void success() {
                //given
                String email = "test@test.com";
                String username = "한지희";
                String authenticationCode = "1Q2W3E";
                AuthenticationType authenticationType = AuthenticationType.PASSWORD;
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .email(email)
                        .userName(username)
                        .authenticationCode(authenticationCode)
                        .authenticationType(authenticationType)
                        .build();
                EmailAuthentication savedEmailAuthentication = emailAuthenticationRepository.save(emailAuthentication);

                //when
                authService.validateAuthenticationCode(email, authenticationCode, authenticationType);

                //then
                assertThat(savedEmailAuthentication.getEmail()).isEqualTo(email);
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("이메일이 틀리면 예외를 반환한다.")
            void noSuchEmail() {
                //given
                String email = "test@test.com";
                String wrongEmail = "wrong@test.com";
                String username = "한지희";
                String authenticationCode = "1Q2W3E";
                AuthenticationType authenticationType = AuthenticationType.PASSWORD;
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .email(email)
                        .userName(username)
                        .authenticationCode(authenticationCode)
                        .authenticationType(authenticationType)
                        .build();

                emailAuthenticationRepository.save(emailAuthentication);

                //when, then
                assertThrows(NoSuchAuthenticationCodeException.class, () -> authService.validateAuthenticationCode(wrongEmail, authenticationCode, authenticationType));
            }

            @Test
            @DisplayName("인증타입이 틀리면 예외를 반환한다.")
            void noSuchAuthenticationType() {
                //given
                String email = "test@test.com";
                String username = "한지희";
                String authenticationCode = "1Q2W3E";
                AuthenticationType authenticationType = AuthenticationType.PASSWORD;
                AuthenticationType wrongAuthenticationType = AuthenticationType.SIGN_UP;
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .email(email)
                        .userName(username)
                        .authenticationCode(authenticationCode)
                        .authenticationType(authenticationType)
                        .build();

                emailAuthenticationRepository.save(emailAuthentication);

                //when, then
                assertThrows(NoSuchAuthenticationCodeException.class, () -> authService.validateAuthenticationCode(email, authenticationCode, wrongAuthenticationType));
            }

            @Test
            @DisplayName("인증타입이 틀리면 예외를 반환한다.")
            void noSuchAuthenticationCode() {
                //given
                String email = "test@test.com";
                String username = "한지희";
                String authenticationCode = "1Q2W3E";
                String wrongAuthenticationCode = "Q1W2E3";
                AuthenticationType authenticationType = AuthenticationType.PASSWORD;
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .email(email)
                        .userName(username)
                        .authenticationCode(authenticationCode)
                        .authenticationType(authenticationType)
                        .build();

                emailAuthenticationRepository.save(emailAuthentication);

                //when, then
                assertThrows(InvalidAuthenticationCodeException.class, () -> authService.validateAuthenticationCode(email, wrongAuthenticationCode, authenticationType));
            }
        }
    }
}