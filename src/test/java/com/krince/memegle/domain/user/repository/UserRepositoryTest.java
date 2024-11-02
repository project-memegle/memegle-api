package com.krince.memegle.domain.user.repository;

import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.global.constant.Role;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;

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

    @Test
    @DisplayName("로그인 아이디로 회원 조회")
    void findByLoginId() {
        //given
        String loginId = "testId1";

        //when
        User findUser = userRepository.findByLoginId(loginId).orElseThrow();

        //then
        assertThat(findUser.getLoginId()).isEqualTo(loginId);
    }

    @Test
    @DisplayName("없는 로그인 아이디로 회원 조회시 예외가 발생한다.")
    void findByLoginIdWrongLoginId() {
        //given
        String loginId = "wrongLoginId";

        //when, then
        assertThrows(NoSuchElementException.class, () -> userRepository.findByLoginId(loginId).orElseThrow());
    }
}