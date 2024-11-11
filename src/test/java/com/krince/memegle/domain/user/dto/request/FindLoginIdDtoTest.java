package com.krince.memegle.domain.user.dto.request;

import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Tags({
        @Tag("test"),
        @Tag("dtoUnitTest"),
        @Tag("unitTest")
})
@DisplayName("아이디 찾기 dto 테스트")
class FindLoginIdDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
        validator = validatorFactoryBean;
    }

    @Nested
    @DisplayName("")
    class AuthenticationCode {

        @Nested
        @DisplayName("")
        class Success {

            @Test
            @DisplayName("")
            void success() {
                Assertions.assertThat(true).isFalse();
            }
        }

        @Nested
        @DisplayName("")
        class Fail {

        }
    }

    @Nested
    @DisplayName("")
    class Email {

        @Nested
        @DisplayName("")
        class Success {

        }

        @Nested
        @DisplayName("")
        class Fail {

        }
    }
}