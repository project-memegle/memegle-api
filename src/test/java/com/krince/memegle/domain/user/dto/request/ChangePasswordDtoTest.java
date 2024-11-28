package com.krince.memegle.domain.user.dto.request;

import jakarta.validation.Validator;
import org.junit.jupiter.api.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.assertj.core.api.Assertions.*;

@Tag("test")
@DisplayName("비밀번호 변경 dto 테스트")
class ChangePasswordDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
        validator = validatorFactoryBean;
    }

    @Tag("develop")
    @Nested
    @DisplayName("")
    class Email {

        @Nested
        @DisplayName("")
        class Success {

            @Test
            @DisplayName("")
            void success() {
                assertThat(true).isFalse();
            }
        }

        @Nested
        @DisplayName("")
        class Fail {

        }
    }

    @Nested
    @DisplayName("")
    class AuthenticationCode {

        @Nested
        @DisplayName("")
        class Success {

        }

        @Nested
        @DisplayName("")
        class Fail {

        }
    }

    @Nested
    @DisplayName("")
    class LoginId {

        @Nested
        @DisplayName("")
        class Success {

        }

        @Nested
        @DisplayName("")
        class Fail {

        }
    }

    @Nested
    @DisplayName("")
    class Password {

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