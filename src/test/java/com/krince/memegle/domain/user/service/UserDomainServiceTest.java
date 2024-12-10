package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.entity.SelfAuthentication;
import com.krince.memegle.domain.user.repository.SelfAuthenticationRepository;
import com.krince.memegle.domain.user.repository.UserRepository;
import com.krince.memegle.domain.user.repository.fake.FakeSelfAuthenticationRepository;
import com.krince.memegle.domain.user.repository.fake.FakeUserRepository;
import com.krince.memegle.global.exception.DuplicateUserException;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@Tag("test")
@Tag("unitTest")
@DisplayName("회원 도메인 서비스(UserDomainService)")
class UserDomainServiceTest {

    static UserDomainService userDomainService;

    static UserRepository userRepository;
    static SelfAuthenticationRepository selfAuthenticationRepository;

    static PasswordEncoder passwordEncoder;

    @BeforeAll
    static void setUp() {
        userRepository = new FakeUserRepository();
        selfAuthenticationRepository = new FakeSelfAuthenticationRepository();

        passwordEncoder = new BCryptPasswordEncoder();

        userDomainService = new UserDomainServiceImpl(userRepository, selfAuthenticationRepository, passwordEncoder);
    }

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
        selfAuthenticationRepository.deleteAll();
    }

    @Tag("develop")
    @Tag("target")
    @Nested
    @DisplayName("중복 이메일 유무 테스트(중복 이메일 없어야 통과)")
    class ValidateDuplicateEmail {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("중복되는 이메일이 없으면 통과한다.")
            void success() {
                //given
                String email = "test@test.com";

                //when, then
                assertDoesNotThrow(() -> userDomainService.validateDuplicateEmail(email));
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("중복된 이메일이 있으면 예외를 반환한다.")
            void duplicateEmail() {
                //given
                String email = "test@test.com";
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .userId(1L)
                        .email(email)
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //when, then
                assertThrows(DuplicateUserException.class, () -> userDomainService.validateDuplicateEmail(email));
            }
        }
    }
}