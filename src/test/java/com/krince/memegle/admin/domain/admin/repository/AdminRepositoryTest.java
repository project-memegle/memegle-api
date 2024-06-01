package com.krince.memegle.admin.domain.admin.repository;

import com.krince.memegle.admin.domain.admin.entity.Admin;
import com.krince.memegle.global.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminRepositoryTest {

    @Autowired
    AdminRepository adminRepository;

    private Admin admin1;

    @BeforeEach
    void before() {
        admin1 = Admin.builder()
                .adminId("testId")
                .password("testPassword")
                .role(Role.ROLE_ADMIN)
                .build();
    }

    @Test
    @DisplayName("관리자 계정 찾기 테스트")
    void findByAdminIdAndPassword() {
        //given
        Admin savedAdmin = adminRepository.save(admin1);
        String loginId = "testId";
        String password = "testPassword";

        //when
        Admin findAdmin = adminRepository.findByAdminIdAndPassword(loginId, password).orElseThrow();

        //then
        assertThat(findAdmin).isEqualTo(savedAdmin);
        assertThat(findAdmin.getAdminId()).isEqualTo(loginId);
        assertThat(findAdmin.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("관리자 계정 찾기 테스트 - 없는 아이디")
    void findByAdminIdAndPasswordWrongId() {
        //given
        String loginId = "wrongId";
        String password = "testPassword";

        //when, then
        assertThatThrownBy(() -> adminRepository.findByAdminIdAndPassword(loginId, password).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }
}