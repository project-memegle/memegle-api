package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.repository.EmailAuthenticationRepository;
import com.krince.memegle.domain.auth.repository.fake.FakeEmailAuthenticationRepository;
import com.krince.memegle.domain.user.entity.SelfAuthentication;
import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.domain.user.repository.SelfAuthenticationRepository;
import com.krince.memegle.domain.user.repository.UserRepository;
import com.krince.memegle.domain.user.repository.fake.FakeSelfAuthenticationRepository;
import com.krince.memegle.domain.user.repository.fake.FakeUserRepository;
import com.krince.memegle.domain.user.service.UserDomainService;
import com.krince.memegle.domain.user.service.UserDomainServiceImpl;
import com.krince.memegle.global.constant.AuthenticationType;
import com.krince.memegle.global.exception.DuplicateUserException;
import com.krince.memegle.global.mail.EmailDomainService;
import com.krince.memegle.global.mail.fake.FakeEmailDomainService;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@Tag("test")
@Tag("unitTest")
@DisplayName("인증 서비스 테스트(AuthService)")
class AuthApplicationServiceTest {

    static AuthApplicationService authApplicationService;
    static AuthDomainService authDomainService;
    static UserDomainService userDomainService;

    static EmailAuthenticationRepository emailAuthenticationRepository;
    static UserRepository userRepository;
    static SelfAuthenticationRepository selfAuthenticationRepository;

    static PasswordEncoder passwordEncoder;

    static EmailDomainService emailDomainService;

    @BeforeAll
    static void setUp() {
        emailAuthenticationRepository = new FakeEmailAuthenticationRepository();
        userRepository = new FakeUserRepository();
        selfAuthenticationRepository = new FakeSelfAuthenticationRepository();

        emailDomainService = new FakeEmailDomainService();
        authDomainService = new AuthDomainServiceImpl(emailAuthenticationRepository);
        userDomainService = new UserDomainServiceImpl(userRepository, selfAuthenticationRepository, passwordEncoder);

        passwordEncoder = new BCryptPasswordEncoder();

        authApplicationService = new AuthApplicationServiceImpl(authDomainService, emailDomainService, userDomainService);
    }

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
        emailAuthenticationRepository.deleteAll();
        selfAuthenticationRepository.deleteAll();
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

    @Tag("develop")
    @Nested
    @DisplayName("이메일 중복 검증")
    class ValidateDuplicateMail {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("중복된 이메일이 없다면 통과")
            void success() {
                //given
                String email = "test@test.com";

                //when, then
                assertDoesNotThrow(() -> authApplicationService.validateDuplicateMail(email));
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("중복된 이메일이 있다면 예외를 반환한다.")
            void duplicateEmail() {
                //given
                String email = "test@test.com";
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .userId(1L)
                        .email(email)
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //when, then
                assertThrows(DuplicateUserException.class, () -> authApplicationService.validateDuplicateMail(email));
            }
        }
    }

    @Tag("develop")
    @Nested
    @DisplayName("메서드")
    class validateDuplicateLoginId {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("success")
            void success() {
                //given
                String loginId = "test@test.com";

                //when, then
                assertDoesNotThrow(() -> authApplicationService.validateDuplicateLoginId(loginId));
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("중복되는 아이디가 있으면 예외를 반환한다.")
            void success() {
                //given
                User user = User.builder()
                        .loginId("test@test.com")
                        .password("password")
                        .nickname("nickname")
                        .build();
                userRepository.save(user);

                //when, then
                assertThrows(DuplicateUserException.class, () -> authApplicationService.validateDuplicateLoginId("test@test.com"));
            }
        }
    }
}