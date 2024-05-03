package com.krince.memegle.domain.admin.controller;

import com.krince.memegle.domain.admin.dto.request.RequestAdminLoginDto;
import com.krince.memegle.domain.admin.dto.response.RequestConfirmMimeImageDto;
import com.krince.memegle.domain.admin.dto.response.ResponseGetAdminPostsDto;
import com.krince.memegle.domain.admin.service.AdminService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {

    private final AdminService adminService;

    @Override
    @PostMapping("/sign/in")
    public ResponseEntity<Void> login(RequestAdminLoginDto requestAdminLoginDto) {
        return null;
    }

    @Override
    @PostMapping("/images/{mimeImageId}")
    public ResponseEntity<Void> confirmMimeImage(Long mimeImageId, RequestConfirmMimeImageDto requestConfirmMimeImageDto) {
        return null;
    }

    @Override
    @GetMapping("/posts")
    public ResponseEntity<List<ResponseGetAdminPostsDto>> getAdminPosts() {
        return null;
    }
}
