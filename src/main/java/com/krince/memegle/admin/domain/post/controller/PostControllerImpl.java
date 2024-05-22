package com.krince.memegle.admin.domain.post.controller;

import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;
import com.krince.memegle.admin.domain.post.service.PostService;
import com.krince.memegle.global.response.GlobalResponseDto;
import com.krince.memegle.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("adminPostController")
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {

    private final PostService postService;

    @Override
    @GetMapping()
    public ResponseEntity<GlobalResponseDto<List<ResponseGetAdminPostsDto>>> getAdminPosts() {

        List<ResponseGetAdminPostsDto> responseGetAdminPostsDtos = postService.getAdminPosts();
        ResponseCode status = ResponseCode.OK;
        GlobalResponseDto<List<ResponseGetAdminPostsDto>> response = new GlobalResponseDto<>(status, responseGetAdminPostsDtos);

        return ResponseEntity.ok().body(response);
    }
}
