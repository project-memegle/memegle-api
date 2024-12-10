package com.krince.memegle.global.validateor;


import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("test")
@Tag("unitTest")
@DisplayName("커스텀 검증 클래스(CustomValidator)")
class CustomValidatorTest {

    @Tag("develop")
    @Tag("target")
    @Nested
    @DisplayName("이메일 형식 검증")
    class ValidateEmailFormat {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("맞는 이메일형식")
            void success() {
                //given
                String email = "test@test.com";

                //when, then
                assertDoesNotThrow(() -> CustomValidator.validateEmailFormat(email));
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("심볼(@) 누락")
            void NotUsedSymbol() {
                //given
                String email = "testtest.com";

                //when, then
                assertThrows(IllegalArgumentException.class, () -> CustomValidator.validateEmailFormat(email));
            }

            @Test
            @DisplayName("너무 짧은 최상위 도메인")
            void shortTopLevelDomain() {
                //given
                String email = "test@test.c";

                //when, then
                assertThrows(IllegalArgumentException.class, () -> CustomValidator.validateEmailFormat(email));
            }

            @Test
            @DisplayName("점(.) 누락")
            void NotUsedDot() {
                //given
                String email = "test@testcom";

                //when, then
                assertThrows(IllegalArgumentException.class, () -> CustomValidator.validateEmailFormat(email));
            }

            @Test
            @DisplayName("허용되지 않는 문자 포함")
            void NotArrowedValue() {
                //given
                String email = "!test@test.com";

                //when, then
                assertThrows(IllegalArgumentException.class, () -> CustomValidator.validateEmailFormat(email));
            }

            @Test
            @DisplayName("공백 포함")
            void spaceValue() {
                //given
                String email = "te st@test.com";

                //when, then
                assertThrows(IllegalArgumentException.class, () -> CustomValidator.validateEmailFormat(email));
            }
        }
    }
}