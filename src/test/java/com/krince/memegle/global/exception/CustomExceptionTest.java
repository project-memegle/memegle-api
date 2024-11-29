package com.krince.memegle.global.exception;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Tags({
        @Tag("test"),
        @Tag("unitTest")
})
@DisplayName("커스텀 예외 클레스 테스트(CustomException)")
class CustomExceptionTest {

    @Test
    @DisplayName("기본 생성 테스트")
    void defaultConstructorTest() throws CustomException {
        assertThrows(CustomException.class, () -> {
            throw new CustomException();
        });
    }

    @Test
    @DisplayName("메시지를 포함하는 예외 생성 테스트")
    void messageConstructorTest() {
        //given
        String exceptionMessage = "this is test exception message";

        //when
        CustomException exception = assertThrows(CustomException.class, () -> {
            throw new CustomException(exceptionMessage);
        });

        //then
        assertThat(exception)
                .isInstanceOf(CustomException.class)
                .hasMessage(exceptionMessage);
    }

}