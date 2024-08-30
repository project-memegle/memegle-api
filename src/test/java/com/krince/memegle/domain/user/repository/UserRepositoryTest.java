package com.krince.memegle.domain.user.repository;

import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.global.Role;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@DisplayName("회원 리포지토리 테스트")
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
                .loginId("testId1")
                .password(passwordEncoder.encode("testPassword1!"))
                .name("테스트")
                .nickname("testNickname")
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
    }

    @Test
    @DisplayName("중복 회원 존재 유무 테스트")
    void existsByLoginId() {
        //given
        String loginId = "testId1";
        String wrongLoginId = "testId2";

        //when
        boolean isExistUser = userRepository.existsByLoginId(loginId);
        boolean wrongResult = userRepository.existsByLoginId(wrongLoginId);

        //then
        assertThat(isExistUser).isTrue();
        assertThat(wrongResult).isFalse();
    }
}