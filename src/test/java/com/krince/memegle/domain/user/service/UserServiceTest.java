package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.ChangeNicknameDto;
import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.entity.SelfAuthentication;
import com.krince.memegle.domain.user.repository.fake.FakeSelfAuthenticationRepository;
import com.krince.memegle.domain.user.repository.fake.FakeUserQueryRepository;
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

@Tag("test")
@Tag("unitTest")
@DisplayName("회원 서비스 테스트(UserService)")
class UserServiceTest {

    static String secretKey = "sdufhasduvhaidufhwaoefjoisdjoviasjdoivjojsdfalskdfnaweivjnaosdivnalskmeflszflijlij";
    static Long accessTokenExpired = 864000L;
    static Long refreshTokenExpired = 864000L;
    static SignUpDto signUpDto;
    static SignInDto signInDto;

    static UserService userService;
    static FakeUserRepository userRepository;
    static FakeSelfAuthenticationRepository selfAuthenticationRepository;
    static FakeUserQueryRepository userQueryRepository;
    static PasswordEncoder passwordEncoder;
    static JwtProvider jwtProvider;

    @BeforeAll
    static void setUp() {
        userRepository = new FakeUserRepository();
        selfAuthenticationRepository = new FakeSelfAuthenticationRepository();
        userQueryRepository = new FakeUserQueryRepository();
        passwordEncoder = new BCryptPasswordEncoder();
        jwtProvider = new JwtProvider(secretKey, accessTokenExpired, refreshTokenExpired);
        userService = new UserServiceImpl(userRepository, selfAuthenticationRepository, userQueryRepository, passwordEncoder, jwtProvider);

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

    @Tag("develop")
    @Nested
    @DisplayName("회원 정보 조회")
    class GetUserInfo {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("success")
            void success() {
                //given
                CustomUserDetails userDetails = new CustomUserDetails(1L, Role.ROLE_USER);

                //when
                UserInfoDto findUserInfoDto = userService.getUserInfo(userDetails);

                //then
                assertThat(findUserInfoDto).isNotNull();
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("등록되지 않은 회원은 예외를 반환한다.")
            void noSuchUser() {
                //given 2L 은 등록되지 않은 회원의 pk임
                CustomUserDetails userDetails = new CustomUserDetails(2L, Role.ROLE_USER);

                //when, then
                assertThrows(NoSuchElementException.class, () -> userService.getUserInfo(userDetails));
            }
        }
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

    @Tag("develop")
    @Nested
    @DisplayName("메서드")
    class DropUser {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("success")
            void success() {
                //given
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .userId(1L)
                        .email("test@test.com")
                        .build();
                userService.signUp(signUpDto);
                selfAuthenticationRepository.save(selfAuthentication);
                CustomUserDetails userDetails = new CustomUserDetails(1L, Role.ROLE_USER);

                //when
                userService.dropUser(userDetails);

                //then
                assertThat(userRepository.findAll().size()).isEqualTo(0);
                assertThat(selfAuthenticationRepository.findAll().size()).isEqualTo(0);
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("삭제하려는 회원 정보가 없으면 예외를 반환한다.")
            void notFoundUser() {
                //given
                CustomUserDetails userDetails = new CustomUserDetails(1L, Role.ROLE_USER);

                //when, then
                assertThrows(NoSuchElementException.class, () -> userService.dropUser(userDetails));
            }
        }
    }
}