package com.krince.memegle.global.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("성공 응답 클래스 테스트")
class SuccessResponseTest {

    @Test
    @DisplayName("기본 생성 테스트")
    void createTest() {
        //given
        String results = "testResultMessage";
        ResponseCode responseCode = ResponseCode.OK;

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