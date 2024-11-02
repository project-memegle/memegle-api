package com.krince.memegle.global.security;

import com.krince.memegle.global.constant.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    JwtProvider jwtProvider;

    private Long id;
    private Role role;
    private String accessToken;
    private String substringToken;

    @BeforeEach
    void before() {
        this.id = 1L;
        this.role = Role.ROLE_ADMIN;
        this.accessToken = jwtProvider.createAccessToken(this.id, this.role);
        this.substringToken = this.accessToken.substring("Bearer ".length());
    }

    @Test
    @DisplayName("토큰 생성 테스트")
    void createAccessToken() {
        //given
        Long id = this.id;
        Role role = this.role;

        //when
        String accessToken = jwtProvider.createAccessToken(id, role);

        //then
        assertThat(accessToken).startsWith("Bearer ");
    }

    @Test
    @DisplayName("토큰 검증 결과 테스트")
    void isValidToken() {
        //given
        String substringToken = this.substringToken;
        String wrongToken = "wrongToken";

        //when
        Boolean isValidAccessToken = jwtProvider.isValidToken(substringToken);
        Boolean isValidWrongToken = jwtProvider.isValidToken(wrongToken);

        //then
        assertThat(isValidAccessToken).isTrue();
        assertThat(isValidWrongToken).isFalse();
    }

    @Test
    @DisplayName("계정 권한 가져오기 테스트")
    void getRole() {
        //given
        String substringToken = this.substringToken;

        //when
        Role role = jwtProvider.getRole(substringToken);

        //then
        assertThat(role).isEqualTo(this.role);
    }

    @Test
    @DisplayName("계정 고유번호 가져오기 테스트")
    void getId() {
        //given
        String substringToken = this.substringToken;

        //when
        Long id = jwtProvider.getId(substringToken);

        //then
        assertThat(id).isEqualTo(this.id);
    }

    @Test
    @DisplayName("Bearer 자르기 테스트")
    void extractToken() {
        //given
        String accessToken = this.accessToken;

        //when
        String substringToken = jwtProvider.extractToken(accessToken);

        //then
        assertThat(substringToken).isEqualTo(this.substringToken);
    }

    @Test
    @DisplayName("Bearer 자르기 테스트 - 실패")
    void extractTokenFail() {
        //given
        String wrongToken = "wrongToken";

        //when, then
        assertThatThrownBy(() -> jwtProvider.extractToken(wrongToken))
                .isInstanceOf(IllegalArgumentException.class);
    }
}