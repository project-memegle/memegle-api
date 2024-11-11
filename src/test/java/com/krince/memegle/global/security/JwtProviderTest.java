package com.krince.memegle.global.security;

import com.krince.memegle.global.constant.Role;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.*;

import static com.krince.memegle.global.constant.Role.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Tags({
        @Tag("test"),
        @Tag("unitTest")
})
@DisplayName("jwt 테스트(JwtProvider)")
class JwtProviderTest {

    static JwtProvider jwtProvider;

    static String secretKey = "sdufhasduvhaidufhwaoefjoisdjoviasjdoivjojsdfalskdfnaweivjnaosdivnalskmeflszflijlij";
    static Long accessTokenExpired = 864000L;
    static Long refreshTokenExpired = 864000L;

    static Long id;
    static Role role;
    static String accessToken;
    static String substringToken;

    @BeforeAll
    static void before() {
        jwtProvider = new JwtProvider(secretKey, accessTokenExpired, refreshTokenExpired);

        id = 1L;
        role = ROLE_ADMIN;
        accessToken = jwtProvider.createAccessToken(id, role);
        substringToken = accessToken.substring("Bearer ".length());
    }

    @Nested
    @DisplayName("엑세스 토큰 생성")
    class CreateAccessToken {

        @Test
        @DisplayName("성공")
        void createAccessToken() {
            //given
            Long id = 1L;
            Role role = ROLE_USER;

            //when
            String accessToken = jwtProvider.createAccessToken(id, role);

            //then
            assertThat(accessToken).startsWith("Bearer ");
        }
    }

    @Nested
    @DisplayName("토큰 검증 결과")
    class IsValidToken {

        @Nested
        @DisplayName("성공")
        class IsValidTokenSuccess {

            @Test
            @DisplayName("올바른 토큰을 입력하면 true를 반환한다.")
            void validToken() {
                //given
                String wrongToken = "wrongToken";

                //when
                Boolean isValidAccessToken = jwtProvider.isValidToken(substringToken);
                Boolean isValidWrongToken = jwtProvider.isValidToken(wrongToken);

                //then
                assertThat(isValidAccessToken).isTrue();
                assertThat(isValidWrongToken).isFalse();
            }
        }

        @Nested
        @DisplayName("실패")
        class IsInvalidTokenFail {

            @Test
            @DisplayName("올바르지 않은 토큰을 입력하면 false를 반환한다.")
            void invalidToken() {
                //given
                String wrongToken = "wrongToken";

                //when
                Boolean isValidWrongToken = jwtProvider.isValidToken(wrongToken);

                //then
                assertThat(isValidWrongToken).isFalse();
            }
        }
    }

    @Nested
    @DisplayName("계정 권한 가져오기")
    class GetRole {

        @Nested
        @DisplayName("성공")
        class GetRoleSuccess {

            @Test
            @DisplayName("올바른 토큰은 해당 계정의 권한을 반환한다.")
            void getRole() {
                //given, when
                Role role = jwtProvider.getRole(substringToken);

                //then
                assertThat(role).isEqualTo(ROLE_ADMIN);
            }
        }

        @Nested
        @DisplayName("실패")
        class GetRoleFail {

            @Test
            @DisplayName("올바르지 않은 토큰은 예외를 반환한다.")
            void wrongToken() {
                //given
                String wrongToken = "wrongToken";

                //when, then
                assertThrows(MalformedJwtException.class, () -> jwtProvider.getRole(wrongToken));
            }
        }
    }

    @Nested
    @DisplayName("계정 고유번호 가져오기")
    class GetId {

        @Nested
        @DisplayName("성공")
        class GetIdSuccess {

            @Test
            @DisplayName("계정 고유번호 가져오기 테스트")
            void getId() {
                //given, when
                Long findId = jwtProvider.getId(substringToken);

                //then
                assertThat(findId).isEqualTo(id);
            }
        }
    }

    @Nested
    @DisplayName("Bearer 자르기")
    class ExtractToken {

        @Nested
        @DisplayName("성공")
        class ExtractTokenSuccess {

            @Test
            @DisplayName("Bearer 자르기")
            void extractToken() {
                //given, when
                String findSubstringToken = jwtProvider.extractToken(accessToken);

                //then
                assertThat(findSubstringToken).isEqualTo(substringToken);
            }
        }

        @Nested
        @DisplayName("실패")
        class ExtractTokenFail {

            @Test
            @DisplayName("Bearer 로 시작하지 않는 토큰은 예외를 반환한다.")
            void extractTokenFail() {
                //given
                String wrongToken = "wrongToken";

                //when, then
                assertThrows(IllegalArgumentException.class, () -> jwtProvider.extractToken(wrongToken));
            }
        }
    }
}