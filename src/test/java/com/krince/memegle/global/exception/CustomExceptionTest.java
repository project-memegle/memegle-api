package com.krince.memegle.global.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("커스텀 예외 클레스 테스트")
class CustomExceptionTest {

    @Test
    @DisplayName("기본 생성 테스트")
    void defaultConstructorTest() {
        assertThatThrownBy(() -> {
                    throw new CustomException();
                })
                .isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("메시지를 포함하는 예외 생성 테스트")
    void messageConstructorTest() {
        //given
        String exceptionMessage = "this is test exception message";

        //when, then
        assertThatThrownBy(() -> {
            throw new CustomException(exceptionMessage);
        })
                .isInstanceOf(CustomException.class)
                .hasMessage(exceptionMessage);
    }

}