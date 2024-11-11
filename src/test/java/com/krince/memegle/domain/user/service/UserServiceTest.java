package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.repository.fake.FakeUserRepository;
import com.krince.memegle.global.exception.DuplicateUserException;
import com.krince.memegle.global.security.JwtProvider;
import org.junit.jupiter.api.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Tags({
        @Tag("test"),
        @Tag("unitTest")
})
@DisplayName("회원 서비스 테스트(UserService)")
class UserServiceTest {

    static String secretKey = "sdufhasduvhaidufhwaoefjoisdjoviasjdoivjojsdfalskdfnaweivjnaosdivnalskmeflszflijlij";
    static Long accessTokenExpired = 864000L;
    static Long refreshTokenExpired = 864000L;
    static SignUpDto signUpDto;
    static SignInDto signInDto;

    static UserService userService;

    static FakeUserRepository userRepository;

    static PasswordEncoder passwordEncoder;

    static JwtProvider jwtProvider;

    @BeforeAll
    static void setUp() {
        userRepository = new FakeUserRepository();
        passwordEncoder = new BCryptPasswordEncoder();
        jwtProvider = new JwtProvider(secretKey, accessTokenExpired, refreshTokenExpired);
        userService = new UserServiceImpl(userRepository, passwordEncoder, jwtProvider);

        signUpDto = SignUpDto.builder()
                .loginId("login123")
                .password("Password123")
                .nickname("nickName")
                .build();

        signInDto = SignInDto.builder()
                .loginId("login123")
                .password("Password123")
                .build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("회원가입")
    class SignUp {

        @Test
        @DisplayName("성공")
        void success() {
            //given, when
            userService.signUp(signUpDto);

            //then
            assertThat(userRepository.findAll().size()).isEqualTo(1);
        }

        @Nested
        @DisplayName("실패")
        class SignUpFail {
            @Test
            @DisplayName("중복 회원")
            void fail() {
                //given
                userService.signUp(signUpDto);

                //when, then
                assertThrows(DuplicateUserException.class, () -> userService.signUp(signUpDto));
            }
        }

    }

    @Nested
    @DisplayName("회원 로그인")
    class signIn {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            userService.signUp(signUpDto);

            //when
            TokenDto tokenDto = userService.signIn(signInDto);

            //then
            assertThat(tokenDto.getAccessToken()).startsWith("Bearer");
            assertThat(tokenDto.getRefreshToken()).startsWith("Bearer");
        }

        @Nested
        @DisplayName("실패")
        class signInFail {

            @Test
            @DisplayName("없는 아이디")
            void missMatchLoginId() {
                //given
                userService.signUp(signUpDto);

                SignInDto wrongLoginIdSignInDto = SignInDto.builder()
                        .loginId("wrongloginid123")
                        .password("Password123")
                        .build();

                //when, then
                assertThrows(NoSuchElementException.class, () -> userService.signIn(wrongLoginIdSignInDto));
            }

            @Test
            @DisplayName("틀린 비밀번호")
            void wrongPassword() {
                //given
                userService.signUp(signUpDto);
                SignInDto wrongPasswordSignInDto = SignInDto.builder()
                        .loginId("login123")
                        .password("wrongpassword123")
                        .build();

                //when, then
                assertThrows(BadCredentialsException.class, () -> userService.signIn(wrongPasswordSignInDto));
            }
        }
    }
}