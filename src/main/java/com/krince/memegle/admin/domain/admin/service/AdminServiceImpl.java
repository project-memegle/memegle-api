package com.krince.memegle.admin.domain.admin.service;

import com.krince.memegle.admin.domain.admin.dto.request.RequestAdminLoginDto;
import com.krince.memegle.admin.domain.admin.entity.Admin;
import com.krince.memegle.admin.domain.admin.repository.AdminRepository;
import com.krince.memegle.global.Role;
import com.krince.memegle.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final JwtProvider jwtProvider;

    public String login(RequestAdminLoginDto requestAdminLoginDto) {
        String adminId = requestAdminLoginDto.getAdminId();
        String password = requestAdminLoginDto.getPassword();

        Admin admin = adminRepository.findByAdminIdAndPassword(adminId, password).orElseThrow(NoSuchElementException::new);
        Long id = admin.getId();
        Role role = admin.getRole();

        return jwtProvider.createAccessToken(id, role);
    }
}
