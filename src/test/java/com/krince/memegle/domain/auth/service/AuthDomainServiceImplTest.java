package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.EmailAuthenticationCodeDto;
import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import com.krince.memegle.domain.auth.repository.EmailAuthenticationRepository;
import com.krince.memegle.domain.auth.repository.fake.FakeEmailAuthenticationRepository;
import com.krince.memegle.global.constant.AuthenticationType;
import com.krince.memegle.global.exception.InvalidAuthenticationCodeException;
import com.krince.memegle.global.exception.NoSuchAuthenticationCodeException;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Tag("test")
@Tag("unitTest")
@DisplayName("인증 도메인 서비스 테스트(AuthDomainService)")
class AuthDomainServiceImplTest {

    static EmailAuthenticationRepository emailAuthenticationRepository;
    static AuthDomainService authDomainService;

    @BeforeAll
    static void setUp() {
        emailAuthenticationRepository = new FakeEmailAuthenticationRepository();
        authDomainService = new AuthDomainServiceImpl(emailAuthenticationRepository);
    }

    @BeforeEach
    void beforeEach() {
        emailAuthenticationRepository.deleteAll();
    }

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
                EmailAuthenticationCodeDto emailAuthenticationCodeDto = EmailAuthenticationCodeDto.builder()
                        .email(email)
                        .authenticationCode(authenticationCode)
                        .authenticationType(authenticationType)
                        .build();
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .email(email)
                        .userName(username)
                        .authenticationCode(authenticationCode)
                        .authenticationType(authenticationType)
                        .build();
                EmailAuthentication savedEmailAuthentication = emailAuthenticationRepository.save(emailAuthentication);

                //when
                authDomainService.validateAuthenticationCode(emailAuthenticationCodeDto);

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
                EmailAuthenticationCodeDto emailAuthenticationCodeDto = EmailAuthenticationCodeDto.builder()
                        .email(wrongEmail)
                        .authenticationCode(authenticationCode)
                        .authenticationType(authenticationType)
                        .build();
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .email(email)
                        .userName(username)
                        .authenticationCode(authenticationCode)
                        .authenticationType(authenticationType)
                        .build();

                emailAuthenticationRepository.save(emailAuthentication);

                //when, then
                assertThrows(NoSuchAuthenticationCodeException.class, () -> authDomainService.validateAuthenticationCode(emailAuthenticationCodeDto));
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
                EmailAuthenticationCodeDto emailAuthenticationCodeDto = EmailAuthenticationCodeDto.builder()
                        .email(email)
                        .authenticationCode(authenticationCode)
                        .authenticationType(wrongAuthenticationType)
                        .build();
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .email(email)
                        .userName(username)
                        .authenticationCode(authenticationCode)
                        .authenticationType(authenticationType)
                        .build();

                emailAuthenticationRepository.save(emailAuthentication);

                //when, then
                assertThrows(NoSuchAuthenticationCodeException.class, () -> authDomainService.validateAuthenticationCode(emailAuthenticationCodeDto));
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
                EmailAuthenticationCodeDto emailAuthenticationCodeDto = EmailAuthenticationCodeDto.builder()
                        .email(email)
                        .authenticationCode(wrongAuthenticationCode)
                        .authenticationType(authenticationType)
                        .build();
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .email(email)
                        .userName(username)
                        .authenticationCode(authenticationCode)
                        .authenticationType(authenticationType)
                        .build();

                emailAuthenticationRepository.save(emailAuthentication);

                //when, then
                assertThrows(InvalidAuthenticationCodeException.class, () -> authDomainService.validateAuthenticationCode(emailAuthenticationCodeDto));
            }
        }
    }
}