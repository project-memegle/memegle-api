package com.krince.memegle.domain.user.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("test")
@DisplayName("회원 닉네임 변경 dto(ChangeNicknameDto)")
class ChangeNicknameDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
        validator = validatorFactoryBean;
    }

    @Nested
    @DisplayName("닉네임(nickname)")
    class Nickname {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("2 ~ 20 한글과 영문 조합")
            void success() {
                //given
                String nickname = "testNickname123";

                //when
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder()
                        .nickname(nickname)
                        .build();

                //then
                successTest(changeNicknameDto, "nickname");
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("1자 이하 닉네임")
            void shortValue() {
                //given
                String nickname = "t";

                //when
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder()
                        .nickname(nickname)
                        .build();

                //then
                failTest(changeNicknameDto, "nickname");
            }

            @Test
            @DisplayName("21자 이상 닉네임")
            void longValue() {
                //given
                String nickname = "asasasasas12121212121";

                //when
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder()
                        .nickname(nickname)
                        .build();

                //then
                failTest(changeNicknameDto, "nickname");
            }

            @Test
            @DisplayName("혀용되지 않는 문자 사용 닉네임")
            void invalidValue() {
                //given
                String nickname = "띄어쓰기 안되겠죠";

                //when
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder()
                        .nickname(nickname)
                        .build();

                //then
                failTest(changeNicknameDto, "nickname");
            }

            @Test
            @DisplayName("빈 문자열")
            void emptyValue() {
                //given
                String nickname = "";

                //when
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder()
                        .nickname(nickname)
                        .build();

                //then
                failTest(changeNicknameDto, "nickname");
            }

            @Test
            @DisplayName("공백 문자열")
            void spaceValue() {
                //given
                String nickname = "           ";

                //when
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder()
                        .nickname(nickname)
                        .build();

                //then
                failTest(changeNicknameDto, "nickname");
            }

            @Test
            @DisplayName("null 사용")
            void nullValue() {
                //given
                String nickname = null;

                //when
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder()
                        .nickname(nickname)
                        .build();

                //then
                failTest(changeNicknameDto, "nickname");
            }

            @Test
            @DisplayName("이모티콘 사용")
            void emojiValue() {
                //given
                String nickname = "\uD83D\uDE00\uD83D";

                //when
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder()
                        .nickname(nickname)
                        .build();

                //then
                failTest(changeNicknameDto, "nickname");
            }

            @Test
            @DisplayName("특수문자 사용")
            void emojiValue2() {
                //given
                String nickname = "[][][][][]";

                //when
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder()
                        .nickname(nickname)
                        .build();

                //then
                failTest(changeNicknameDto, "nickname");
            }
        }
    }

    private void successTest(Object dto, String validValue) {
        //when
        Set<ConstraintViolation<Object>> testResult = validator.validate(dto);

        //then
        assertThat(testResult)
                .filteredOn(result -> result.getPropertyPath().toString().equals(validValue))
                .isEmpty();
    }

    private void failTest(Object dto, String validValue) {
        //when
        Set<ConstraintViolation<Object>> testResult = validator.validate(dto);

        //then
        assertThat(testResult)
                .filteredOn(result -> result.getPropertyPath().toString().equals(validValue))
                .isNotEmpty();
    }
}