package com.krince.memegle.domain.admin.controller;

import com.krince.memegle.domain.admin.dto.request.RequestAdminLoginDto;
import com.krince.memegle.domain.admin.service.AdminService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자", description = "관리자 관련 API")
@RestController()
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminServiceImpl;

    @PostMapping("/sign/in")
    @ApiResponse(description = "로그인 성공", responseCode = "204")
    public ResponseEntity<Void> login(@RequestBody RequestAdminLoginDto requestAdminLoginDto) {

        return ResponseEntity.noContent().build();
    }
}
