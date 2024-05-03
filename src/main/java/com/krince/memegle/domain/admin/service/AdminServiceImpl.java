package com.krince.memegle.domain.admin.service;

import com.krince.memegle.domain.admin.dto.request.RequestAdminLoginDto;
import com.krince.memegle.domain.admin.entity.Admin;
import com.krince.memegle.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public void login(RequestAdminLoginDto requestAdminLoginDto) {
        String adminId = requestAdminLoginDto.getAdminId();
        String password = requestAdminLoginDto.getPassword();

        adminRepository.findByAdminIdAndPassword(adminId, password).orElseThrow(NoSuchElementException::new);
    }
}
