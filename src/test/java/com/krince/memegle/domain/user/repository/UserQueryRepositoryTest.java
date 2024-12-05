package com.krince.memegle.domain.user.repository;

import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.entity.SelfAuthentication;
import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.global.constant.Role;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@Tag("test")
@Tag("integrationTest")
@SpringBootTest
@DisplayName("회원 쿼리 리포지토리 테스트(UserQueryRepository)")
class UserQueryRepositoryTest {

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SelfAuthenticationRepository selfAuthenticationRepository;

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
    @DisplayName("회원 pk로 userInfoDto 조회")
    class FindUserInfoDtoByUserId {

        @Tag("develop")
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
                UserInfoDto findUserInfoDto = userQueryRepository.findUserInfoDtoByUserId(userId).get();

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
                UserInfoDto findUserInfoDto = userQueryRepository.findUserInfoDtoByUserId(userId).get();

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
}