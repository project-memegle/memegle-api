package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.ChangeNicknameDto;
import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.repository.fake.FakeUserRepository;
import com.krince.memegle.global.constant.Role;
import com.krince.memegle.global.exception.DuplicateUserException;
import com.krince.memegle.global.exception.DuplicationResourceException;
import com.krince.memegle.global.security.CustomUserDetails;
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

    @Tag("develop")
    @Nested
    @DisplayName("회원 닉네임 변경")
    class ChangeNickname {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("중복되지 않은 닉네임으로 가입할 수 있다.")
            void success() {
                //given
                userService.signUp(signUpDto);
                CustomUserDetails userDetails = new CustomUserDetails(1L, Role.ROLE_USER);
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder().nickname("anotherNickname").build();

                //when
                boolean isChangedNickname = userService.changeNickname(userDetails, changeNicknameDto);

                //then
                assertThat(isChangedNickname).isTrue();
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("중복되는 닉네임이 있다면 예외를 반환한다.")
            void duplicateNickname() {
                //given
                userService.signUp(signUpDto);
                CustomUserDetails userDetails = new CustomUserDetails(1L, Role.ROLE_USER);
                String duplicateNickname = signUpDto.getNickname();
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder().nickname(duplicateNickname).build();

                //when, then
                assertThrows(DuplicationResourceException.class, () -> userService.changeNickname(userDetails, changeNicknameDto));
            }

            @Test
            @DisplayName("등록된 회원이 아니라면 예외를 반환한다.")
            void unregisteredUser() {
                //given
                userService.signUp(signUpDto);
                CustomUserDetails userDetails = new CustomUserDetails(2L, Role.ROLE_USER);
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder().nickname("아무닉네임").build();

                //when, then
                assertThrows(NoSuchElementException.class, () -> userService.changeNickname(userDetails, changeNicknameDto));
            }
        }
    }
}