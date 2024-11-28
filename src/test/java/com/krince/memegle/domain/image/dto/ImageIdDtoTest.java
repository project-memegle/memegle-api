package com.krince.memegle.domain.image.dto;

import org.junit.jupiter.api.*;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Tag("test")
@DisplayName("이미지 아이디 dto 테스트")
class ImageIdDtoTest {

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
    class ImageId {

        @Nested
        @DisplayName("")
        class Success {

            @Test
            @DisplayName("")
            void success() {

            }
        }

        @Nested
        @DisplayName("")
        class Fail {

        }
    }
}