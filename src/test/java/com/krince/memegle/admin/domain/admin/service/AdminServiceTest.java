package com.krince.memegle.admin.domain.admin.service;

import com.krince.memegle.admin.domain.admin.dto.request.RequestAdminLoginDto;
import com.krince.memegle.admin.domain.admin.entity.Admin;
import com.krince.memegle.admin.domain.admin.repository.AdminRepository;
import com.krince.memegle.global.Role;
import com.krince.memegle.global.security.JwtProvider;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    AdminServiceImpl adminService;

    @Mock
    AdminRepository adminRepository;

    @Mock
    JwtProvider jwtProvider;

    @Test
    @DisplayName("로그인 테스트")
    void login() {
        //given
        RequestAdminLoginDto requestAdminLoginDto = mock(RequestAdminLoginDto.class);
        Admin admin = mock(Admin.class);

        when(requestAdminLoginDto.getAdminId()).thenReturn("testId");
        when(requestAdminLoginDto.getPassword()).thenReturn("testPassword");
        when(adminRepository.findByAdminIdAndPassword(any(), any())).thenReturn(Optional.ofNullable(admin));
        when(jwtProvider.createAccessToken(1L, Role.ROLE_ADMIN)).thenReturn("Bearer thisIsTestToken");
        when(admin.getId()).thenReturn(1L);
        when(admin.getRole()).thenReturn(Role.ROLE_ADMIN);

        //when
        String token = adminService.login(requestAdminLoginDto);

        //then
        assertThat(token).startsWith("Bearer ");
    }
}