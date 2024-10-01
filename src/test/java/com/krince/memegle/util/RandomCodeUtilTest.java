package com.krince.memegle.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("랜덤 문자열 코드 유틸 테스트")
class RandomCodeUtilTest {

    @Test
    @DisplayName("랜덤 문자열 코드 생성 테스트")
    void generateRandomCode() {
        //given, when
        String randomCode1 = RandomCodeUtil.generateRandomCode();
        String randomCode2 = RandomCodeUtil.generateRandomCode();

        //then
        assertThat(randomCode1.length()).isEqualTo(6);
        assertThat(randomCode1).isNotEqualTo(randomCode2);
    }
}