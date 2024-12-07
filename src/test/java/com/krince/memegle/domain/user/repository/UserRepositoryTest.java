package com.krince.memegle.domain.user.repository;

import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.entity.SelfAuthentication;
import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.global.constant.Role;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Tag("integrationTest")
@Tag("test")
@Tag("integrationTest")
@SpringBootTest
@DisplayName("회원 리포지토리 테스트(UserRepository)")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SelfAuthenticationRepository selfAuthenticationRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private User user;
    private User unSelfAuthenticationUser;
    private SelfAuthentication selfAuthentication;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
        selfAuthenticationRepository.deleteAll();
        user = User.builder()
                .loginId("testid1")
                .password(passwordEncoder.encode("testpassword1!"))
                .nickname("testnickname")
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        unSelfAuthenticationUser = User.builder()
                .loginId("testid2")
                .password(passwordEncoder.encode("testpassword2!"))
                .nickname("testnickname2")
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(unSelfAuthenticationUser);

        selfAuthentication = SelfAuthentication.builder()
                .userId(user.getId())
                .email("testemail@test.com")
                .build();
        selfAuthenticationRepository.save(selfAuthentication);
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
                String wrongLoginId = "testid3";

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

    @Nested
    @DisplayName("회원 pk로 userInfoDto 조회")
    class FindUserInfoDtoByUserId {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("본인 인증이 된 회원은 이메일 정보를 가지고 있다.")
            void selfAuthenticationUser() {
                //given
                User findUser = userRepository.findByLoginId(user.getLoginId()).get();
                Long userId = findUser.getId();

                //when
                UserInfoDto findUserInfoDto = userRepository.findUserInfoDtoByUserId(userId).get();

                //then
                assertThat(findUserInfoDto.getEmail()).isEqualTo(selfAuthentication.getEmail());
                assertThat(findUserInfoDto.getNickname()).isEqualTo(user.getNickname());
            }

            @Test
            @DisplayName("본인 인증이 되지 않은 회원은 이메일 정보를 가지고 있지 않다.")
            void unSelfAuthenticationUser() {
                //given
                User findUser = userRepository.findByLoginId(unSelfAuthenticationUser.getLoginId()).get();
                Long userId = findUser.getId();

                //when
                UserInfoDto findUserInfoDto = userRepository.findUserInfoDtoByUserId(userId).get();

                //then
                assertThat(findUserInfoDto.getEmail()).isNull();
                assertThat(findUserInfoDto.getNickname()).isEqualTo(unSelfAuthenticationUser.getNickname());
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {
        }
    }

    @Nested
    @DisplayName("이메일로 회원 정보 조회")
    class FindUserByEmail {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("회원이 존재함")
            void foundUser() {
                //given
                User findUser = userRepository.findByLoginId("testid1").get();
                SelfAuthentication selfAuthentication = SelfAuthentication.builder()
                        .userId(findUser.getId())
                        .email("test@test.com")
                        .build();
                selfAuthenticationRepository.save(selfAuthentication);

                //when
                Optional<User> userByEmail = userRepository.findUserByEmail("test@test.com");

                //then
                assertThat(userByEmail.isPresent()).isTrue();
                assertThat(userByEmail.get().getId()).isEqualTo(findUser.getId());
            }

            @Test
            @DisplayName("존재하지 않는 회원")
            void notFoundUser() {
                //given
                String email = "test@test.com";

                //when
                Optional<User> userByEmail = userRepository.findUserByEmail(email);

                //then
                assertThat(userByEmail.isPresent()).isFalse();
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {
        }
    }
}