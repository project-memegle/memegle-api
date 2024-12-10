package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import com.krince.memegle.domain.auth.repository.fake.FakeEmailAuthenticationRepository;
import com.krince.memegle.domain.auth.service.AuthDomainService;
import com.krince.memegle.domain.auth.service.AuthDomainServiceImpl;
import com.krince.memegle.domain.user.dto.request.*;
import com.krince.memegle.domain.user.dto.response.LoginIdDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.entity.SelfAuthentication;
import com.krince.memegle.domain.user.repository.fake.FakeSelfAuthenticationRepository;
import com.krince.memegle.domain.user.repository.fake.FakeUserRepository;
import com.krince.memegle.global.constant.AuthenticationType;
import com.krince.memegle.global.constant.Role;
import com.krince.memegle.global.exception.DuplicateUserException;
import com.krince.memegle.global.exception.DuplicationResourceException;
import com.krince.memegle.global.exception.InvalidAuthenticationCodeException;
import com.krince.memegle.global.exception.NoSuchAuthenticationCodeException;
import com.krince.memegle.global.mail.fake.FakeEmailDomainService;
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
@DisplayName("회원 어플리케이션 서비스 테스트(UserApplicationService)")
class UserApplicationServiceTest {

    static String secretKey = "sdufhasduvhaidufhwaoefjoisdjoviasjdoivjojsdfalskdfnaweivjnaosdivnalskmeflszflijlij";
    static Long accessTokenExpired = 864000L;
    static Long refreshTokenExpired = 864000L;
    static SignUpDto signUpDto;
    static SignInDto signInDto;

    static UserApplicationService userApplicationService;
    static UserDomainService userDomainService;
    static AuthDomainService authDomainService;
    static FakeEmailDomainService emailService;

    static FakeUserRepository userRepository;
    static FakeSelfAuthenticationRepository selfAuthenticationRepository;
    static FakeEmailAuthenticationRepository emailAuthenticationRepository;

    static PasswordEncoder passwordEncoder;
    static JwtProvider jwtProvider;

    @BeforeAll
    static void setUp() {
        userRepository = new FakeUserRepository();
        selfAuthenticationRepository = new FakeSelfAuthenticationRepository();
        emailAuthenticationRepository = new FakeEmailAuthenticationRepository();

        passwordEncoder = new BCryptPasswordEncoder();
        jwtProvider = new JwtProvider(secretKey, accessTokenExpired, refreshTokenExpired);

        userDomainService = new UserDomainServiceImpl(userRepository, selfAuthenticationRepository, passwordEncoder);
        authDomainService = new AuthDomainServiceImpl(emailAuthenticationRepository);
        emailService = new FakeEmailDomainService();

        userApplicationService = new UserApplicationServiceImpl(
                userDomainService,
                authDomainService,
                passwordEncoder,
                jwtProvider
        );

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
        selfAuthenticationRepository.deleteAll();
        emailAuthenticationRepository.deleteAll();
    }

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
                UserInfoDto findUserInfoDto = userApplicationService.getUserInfo(userDetails);

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
                assertThrows(NoSuchElementException.class, () -> userApplicationService.getUserInfo(userDetails));
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
            userApplicationService.signUp(signUpDto);

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
                userApplicationService.signUp(signUpDto);

                //when, then
                assertThrows(DuplicateUserException.class, () -> userApplicationService.signUp(signUpDto));
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
            userApplicationService.signUp(signUpDto);

            //when
            TokenDto tokenDto = userApplicationService.signIn(signInDto);

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
                userApplicationService.signUp(signUpDto);

                SignInDto wrongLoginIdSignInDto = SignInDto.builder()
                        .loginId("wrongloginid123")
                        .password("Password123")
                        .build();

                //when, then
                assertThrows(NoSuchElementException.class, () -> userApplicationService.signIn(wrongLoginIdSignInDto));
            }

            @Test
            @DisplayName("틀린 비밀번호")
            void wrongPassword() {
                //given
                userApplicationService.signUp(signUpDto);
                SignInDto wrongPasswordSignInDto = SignInDto.builder()
                        .loginId("login123")
                        .password("wrongpassword123")
                        .build();

                //when, then
                assertThrows(BadCredentialsException.class, () -> userApplicationService.signIn(wrongPasswordSignInDto));
            }
        }
    }

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
                userApplicationService.signUp(signUpDto);
                CustomUserDetails userDetails = new CustomUserDetails(1L, Role.ROLE_USER);
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder().nickname("anotherNickname").build();

                //when, then
                assertDoesNotThrow(() -> userApplicationService.changeNickname(userDetails, changeNicknameDto));
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("중복되는 닉네임이 있다면 예외를 반환한다.")
            void duplicateNickname() {
                //given
                userApplicationService.signUp(signUpDto);
                CustomUserDetails userDetails = new CustomUserDetails(1L, Role.ROLE_USER);
                String duplicateNickname = signUpDto.getNickname();
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder().nickname(duplicateNickname).build();

                //when, then
                assertThrows(DuplicationResourceException.class, () -> userApplicationService.changeNickname(userDetails, changeNicknameDto));
            }

            @Test
            @DisplayName("등록된 회원이 아니라면 예외를 반환한다.")
            void unregisteredUser() {
                //given
                userApplicationService.signUp(signUpDto);
                CustomUserDetails userDetails = new CustomUserDetails(2L, Role.ROLE_USER);
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder().nickname("아무닉네임").build();

                //when, then
                assertThrows(NoSuchElementException.class, () -> userApplicationService.changeNickname(userDetails, changeNicknameDto));
            }
        }
    }

    @Nested
    @DisplayName("회원 탈퇴")
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
                userApplicationService.signUp(signUpDto);
                selfAuthenticationRepository.save(selfAuthentication);
                CustomUserDetails userDetails = new CustomUserDetails(1L, Role.ROLE_USER);

                //when
                userApplicationService.dropUser(userDetails);

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
                assertThrows(NoSuchElementException.class, () -> userApplicationService.dropUser(userDetails));
            }
        }
    }

    @Nested
    @DisplayName("비밀번호 변경")
    class ChangePassword {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("등록된 회원, 본인인증 완료, 인증코드, 인증타입 일치")
            void success() {
                //given
                //회원 등록
                userApplicationService.signUp(signUpDto);

                //회원 본인인증 등록
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .email("test@test.com")
                        .userId(1L)
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //이메일 전송
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .authenticationType(AuthenticationType.PASSWORD)
                        .authenticationCode("1Q2W3E")
                        .userName("login123")
                        .email("test@test.com")
                        .id("test@test.com")
                        .build();
                emailAuthenticationRepository.save(emailAuthentication);

                ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                        .email("test@test.com")
                        .authenticationCode("1Q2W3E")
                        .authenticationType(AuthenticationType.PASSWORD)
                        .loginId("login123")
                        .password("Password1234")
                        .build();

                //when
                userApplicationService.changePassword(changePasswordDto);

                //then
                String changedPassword = userRepository.findByLoginId("login123").get().getPassword();
                boolean isMatchesPassword = passwordEncoder.matches("Password1234", changedPassword);
                assertThat(isMatchesPassword).isTrue();
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("회원정보 없음")
            void noSuchUser() {
                //given
                //회원 본인인증 등록
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .email("test@test.com")
                        .userId(1L)
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //이메일 전송
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .authenticationType(AuthenticationType.PASSWORD)
                        .authenticationCode("1Q2W3E")
                        .userName("login123")
                        .email("test@test.com")
                        .id("test@test.com")
                        .build();
                emailAuthenticationRepository.save(emailAuthentication);

                ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                        .email("test@test.com")
                        .authenticationCode("1Q2W3E")
                        .authenticationType(AuthenticationType.PASSWORD)
                        .loginId("login123")
                        .password("Password1234")
                        .build();

                //when, then
                assertThrows(NoSuchElementException.class, () -> userApplicationService.changePassword(changePasswordDto));
            }

            @Test
            @DisplayName("없는 이메일")
            void noSuchEmail() {
                //given
                //회원 등록
                userApplicationService.signUp(signUpDto);

                //회원 본인인증 등록
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .email("test@test.com")
                        .userId(1L)
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //이메일 전송
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .authenticationType(AuthenticationType.PASSWORD)
                        .authenticationCode("1Q2W3E")
                        .userName("login123")
                        .email("test@test.com")
                        .id("test@test.com")
                        .build();
                emailAuthenticationRepository.save(emailAuthentication);

                ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                        .email("worngTest@test.com")
                        .authenticationCode("1Q2W3E")
                        .authenticationType(AuthenticationType.PASSWORD)
                        .loginId("login123")
                        .password("Password1234")
                        .build();

                //when, then
                assertThrows(NoSuchAuthenticationCodeException.class, () -> userApplicationService.changePassword(changePasswordDto));
            }

            @Test
            @DisplayName("틀린 인증 타입")
            void noSuchAuthenticationType() {
                //given
                //회원 등록
                userApplicationService.signUp(signUpDto);

                //회원 본인인증 등록
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .email("test@test.com")
                        .userId(1L)
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //이메일 전송
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .authenticationType(AuthenticationType.PASSWORD)
                        .authenticationCode("1Q2W3E")
                        .userName("login123")
                        .email("test@test.com")
                        .id("test@test.com")
                        .build();
                emailAuthenticationRepository.save(emailAuthentication);

                ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                        .email("test@test.com")
                        .authenticationCode("1Q2W3E")
                        .authenticationType(AuthenticationType.ID)
                        .loginId("login123")
                        .password("Password1234")
                        .build();

                //when, then
                assertThrows(NoSuchAuthenticationCodeException.class, () -> userApplicationService.changePassword(changePasswordDto));
            }

            @Test
            @DisplayName("틀린 인증코드")
            void noSuchAuthenticationCode() {
                //given
                //회원 등록
                userApplicationService.signUp(signUpDto);

                //회원 본인인증 등록
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .email("test@test.com")
                        .userId(1L)
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //이메일 전송
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .authenticationType(AuthenticationType.PASSWORD)
                        .authenticationCode("1Q2W3E")
                        .userName("login123")
                        .email("test@test.com")
                        .id("test@test.com")
                        .build();
                emailAuthenticationRepository.save(emailAuthentication);

                ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                        .email("test@test.com")
                        .authenticationCode("Q1W2E3")
                        .authenticationType(AuthenticationType.PASSWORD)
                        .loginId("login123")
                        .password("Password1234")
                        .build();

                //when, then
                assertThrows(InvalidAuthenticationCodeException.class, () -> userApplicationService.changePassword(changePasswordDto));
            }
        }
    }

    @Nested
    @DisplayName("회원 로그인 아이디 찾기")
    class GetLoginId {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("이메일, 인증 코드, 인증 타입 모두 일치")
            void success() {
                //given
                //회원 등록
                userApplicationService.signUp(signUpDto);

                //회원 본인인증 등록
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .email("test@test.com")
                        .userId(1L)
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //이메일 전송
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .authenticationType(AuthenticationType.ID)
                        .authenticationCode("1Q2W3E")
                        .userName("login123")
                        .email("test@test.com")
                        .id("test@test.com")
                        .build();
                emailAuthenticationRepository.save(emailAuthentication);

                FindLoginIdDto findLoginIdDto = FindLoginIdDto.builder()
                        .email("test@test.com")
                        .authenticationCode("1Q2W3E")
                        .authenticationType(AuthenticationType.ID)
                        .build();

                //when
                LoginIdDto loginIdDto = userApplicationService.getLoginId(findLoginIdDto);

                //then
                assertThat(loginIdDto.getLoginId()).isEqualTo("login123");
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("없는 이메일")
            void notFoundEmail() {
                //given
                //회원 등록
                userApplicationService.signUp(signUpDto);

                //회원 본인인증 등록
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .email("test@test.com")
                        .userId(1L)
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //이메일 전송
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .authenticationType(AuthenticationType.ID)
                        .authenticationCode("1Q2W3E")
                        .userName("login123")
                        .email("test@test.com")
                        .id("test@test.com")
                        .build();
                emailAuthenticationRepository.save(emailAuthentication);

                FindLoginIdDto findLoginIdDto = FindLoginIdDto.builder()
                        .email("wrongEmail@test.com")
                        .authenticationCode("1Q2W3E")
                        .authenticationType(AuthenticationType.ID)
                        .build();

                //when, then
                assertThrows(NoSuchAuthenticationCodeException.class, () -> userApplicationService.getLoginId(findLoginIdDto));
            }

            @Test
            @DisplayName("틀린 인증코드")
            void notFoundAuthenticationCode() {
                //given
                //회원 등록
                userApplicationService.signUp(signUpDto);

                //회원 본인인증 등록
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .email("test@test.com")
                        .userId(1L)
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //이메일 전송
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .authenticationType(AuthenticationType.ID)
                        .authenticationCode("1Q2W3E")
                        .userName("login123")
                        .email("test@test.com")
                        .id("test@test.com")
                        .build();
                emailAuthenticationRepository.save(emailAuthentication);

                FindLoginIdDto findLoginIdDto = FindLoginIdDto.builder()
                        .email("test@test.com")
                        .authenticationCode("Q1W2E3")
                        .authenticationType(AuthenticationType.ID)
                        .build();

                //when, then
                assertThrows(InvalidAuthenticationCodeException.class, () -> userApplicationService.getLoginId(findLoginIdDto));
            }

            @Test
            @DisplayName("틀린 인증타입")
            void notFoundAuthenticationType() {
                //given
                //회원 등록
                userApplicationService.signUp(signUpDto);

                //회원 본인인증 등록
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .email("test@test.com")
                        .userId(1L)
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //이메일 전송
                EmailAuthentication emailAuthentication = EmailAuthentication.builder()
                        .authenticationType(AuthenticationType.ID)
                        .authenticationCode("1Q2W3E")
                        .userName("login123")
                        .email("test@test.com")
                        .id("test@test.com")
                        .build();
                emailAuthenticationRepository.save(emailAuthentication);

                FindLoginIdDto findLoginIdDto = FindLoginIdDto.builder()
                        .email("test@test.com")
                        .authenticationCode("1Q2W3E")
                        .authenticationType(AuthenticationType.PASSWORD)
                        .build();

                //when, then
                assertThrows(NoSuchAuthenticationCodeException.class, () -> userApplicationService.getLoginId(findLoginIdDto));
            }
        }
    }
}