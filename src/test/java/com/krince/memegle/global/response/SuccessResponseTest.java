package com.krince.memegle.global.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static com.krince.memegle.global.response.SuccessResponseCode.*;
import static org.assertj.core.api.Assertions.*;

@Tags({
        @Tag("test"),
        @Tag("unitTest")
})
@DisplayName("성공 응답 클래스 테스트(SuccessResponseTest)")
class SuccessResponseTest {

    @Test
    @DisplayName("기본 생성 테스트")
    void createTest() {
        //given
        String results = "testResultMessage";
        SuccessResponseCode responseCode = OK;

        //when
        SuccessResponse<String> successResponse = new SuccessResponse<>(responseCode, results);

        //then
        assertThat(successResponse.getResults()).isEqualTo(results);
        assertThat(successResponse.getSuccess()).isEqualTo(responseCode.getIsSuccess());
        assertThat(successResponse.getCode()).isEqualTo(responseCode.getCode());
        assertThat(successResponse.getStatus()).isEqualTo(responseCode.getHttpStatus());
        assertThat(successResponse.getMessage()).isEqualTo(responseCode.getMessage());
    }

}