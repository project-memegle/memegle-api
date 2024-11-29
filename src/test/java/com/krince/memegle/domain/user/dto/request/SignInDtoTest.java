package com.krince.memegle.domain.user.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@Tags({
        @Tag("test"),
        @Tag("dtoUnitTest"),
        @Tag("unitTest")
})
@DisplayName("로그인 dto 테스트(SignInDto)")
class SignInDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
        validator = validatorFactoryBean;
    }

    @Nested
    @DisplayName("loginId 검증")
    class LoginId {

        @Nested
        @DisplayName("성공")
        class LoginIdSuccess {

            @Test
            @DisplayName("숫자만으로 이루어질 수 있다.")
            void onlyNumberValue() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .loginId("123123123")
                        .build();

                //when, then
                successTest(signInDto, "loginId");
            }

            @Test
            @DisplayName("영어소문자만으로 이루어질 수 있다.")
            void onlyEnglishValue() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .loginId("english")
                        .build();

                //when, then
                successTest(signInDto, "loginId");
            }
        }

        @Nested
        @DisplayName("실패")
        class LoginIdFail {

            @Test
            @DisplayName("로그인 아이디는 6자 이상 15자 이하여야한다")
            void outOfRange() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .loginId("fiuhasdiusdfivunasiudgnaisudf2346tsd234234")
                        .build();

                //when, then
                failTest(signInDto, "loginId");
            }

            @Test
            @DisplayName("로그인 아이디는 빈 값을 보낼 수 없다")
            void notBlank() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .loginId("")
                        .build();

                //when, then
                failTest(signInDto, "loginId");
            }

            @Test
            @DisplayName("아이디에는 대문자를 사용할 수 없다.")
            void uppercaseValue() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .loginId("UPPERCASE123")
                        .build();

                //when, then
                failTest(signInDto, "loginId");
            }

            @Test
            @DisplayName("아이디에는 한글을 포함할 수 없다.")
            void koreanValue() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .loginId("한글한글한글123")
                        .build();

                //when, then
                failTest(signInDto, "loginId");
            }

            @Test
            @DisplayName("아이디에는 공백이 포함될 수 없다.")
            void spaceValue() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .loginId("login id")
                        .build();

                //when, then
                failTest(signInDto, "loginId");
            }
        }
    }

    @Nested
    @DisplayName("password 검증")
    class Password {

        @Nested
        @DisplayName("성공")
        class PasswordSuccess {

            @Test
            @DisplayName("8 ~ 20자 이내 공백 없는 문자")
            void successPassword() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .password("testPassword1!")
                        .build();

                //when, then
                successTest(signInDto, "password");
            }

            @Test
            @DisplayName("공백 빼고 모든 문자 허용함")
            void successPassword2() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .password("aA1!한글#@$")
                        .build();

                //when, then
                successTest(signInDto, "password");
            }
        }

        @Nested
        @DisplayName("실패")
        class PasswordFail {

            @Test
            @DisplayName("공백을 포함할 수 없다.")
            void spaceValue() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .password("test password 1")
                        .build();

                //when, then
                failTest(signInDto, "password");
            }

            @Test
            @DisplayName("8자 미만으로 비밀번호를 사용할 수 없다.")
            void shortValue() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .password("short!")
                        .build();

                //when, then
                failTest(signInDto, "password");
            }

            @Test
            @DisplayName("20자 초과로 비밀번호를 사용할 수 없다.")
            void longValue() {
                //given
                SignInDto signInDto = SignInDto.builder()
                        .password("superSoVaryVaryVaryLongPassword!")
                        .build();

                //when, then
                failTest(signInDto, "password");
            }
        }
    }

    private void successTest(SignInDto signInDto, String validValue) {
        //when
        Set<ConstraintViolation<SignInDto>> testResult = validator.validate(signInDto);

        //then
        assertThat(testResult)
                .filteredOn(result -> result.getPropertyPath().toString().equals(validValue))
                .isEmpty();
    }

    private void failTest(SignInDto signInDto, String validValue) {
        //when
        Set<ConstraintViolation<SignInDto>> testResult = validator.validate(signInDto);

        //then
        assertThat(testResult)
                .filteredOn(result -> result.getPropertyPath().toString().equals(validValue))
                .isNotEmpty();
    }
}