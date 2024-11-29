package com.krince.memegle.domain.user.repository;

import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.global.constant.Role;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;

@Tag("integrationTest")
@Tags({
        @Tag("test"),
        @Tag("integrationTest")
})
@SpringBootTest
@DisplayName("회원 리포지토리 테스트(UserRepository)")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
        user = User.builder()
                .loginId("testid1")
                .password(passwordEncoder.encode("testpassword1!"))
                .nickname("testnickname")
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
    }

    @Nested
    @DisplayName("중복 회원 존재 유무 테스트")
    class ExistsByLoginId {

        @Nested
        @DisplayName("성공")
        class ExistsByLoginIdSuccess {

            @Test
            @DisplayName("중복 회원이 존재하면 true 를 반환한다")
            void existUser() {
                //given
                String loginId = "testid1";

                //when
                boolean isExistUser = userRepository.existsByLoginId(loginId);

                //then
                assertThat(isExistUser).isTrue();
            }

            @Test
            @DisplayName("중복 회원이 없으면 false 를 반환한다")
            void notExistUser() {
                //given
                String wrongLoginId = "testid2";

                //when
                boolean wrongResult = userRepository.existsByLoginId(wrongLoginId);

                //then
                assertThat(wrongResult).isFalse();
            }
        }
    }

    @Nested
    @DisplayName("로그인 아이디로 회원 조회")
    class FindByLoginId {

        @Test
        @DisplayName("성공")
        void findByLoginIdSuccess() {
            //given
            String loginId = "testid1";

            //when
            User findUser = userRepository.findByLoginId(loginId).orElseThrow();

            //then
            assertThat(findUser.getLoginId()).isEqualTo(loginId);
        }

        @Nested
        @DisplayName("실패")
        class FindByLoginIdFail {

            @Test
            @DisplayName("없는 로그인 아이디로 회원 조회시 예외가 발생한다.")
            void wrongLoginId() {
                //given
                String loginId = "wrongLoginId";

                //when, then
                assertThrows(NoSuchElementException.class, () -> userRepository.findByLoginId(loginId).orElseThrow());
            }
        }
    }
}