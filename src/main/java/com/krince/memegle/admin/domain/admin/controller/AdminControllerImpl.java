package com.krince.memegle.admin.domain.admin.controller;

import com.krince.memegle.admin.domain.admin.dto.request.RequestAdminLoginDto;
import com.krince.memegle.admin.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {

    private final AdminService adminService;

    @Override
    @PostMapping("/sign/in")
    public ResponseEntity<Void> login(RequestAdminLoginDto requestAdminLoginDto) {
        String headerName = "Authorization";
        String AccessToken = adminService.login(requestAdminLoginDto);

        return ResponseEntity.status(204).header(headerName, AccessToken).build();
    }
}
