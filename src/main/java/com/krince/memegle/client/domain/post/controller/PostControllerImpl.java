package com.krince.memegle.client.domain.post.controller;


import com.krince.memegle.client.domain.post.dto.request.RequestResistPostDto;
import com.krince.memegle.client.domain.post.dto.response.ResponsePostListDto;
import com.krince.memegle.client.domain.post.service.PostService;
import com.krince.memegle.global.response.GlobalResponseDto;
import com.krince.memegle.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {

    private final PostService postService;

    @Override
    @GetMapping()
    public ResponseEntity<GlobalResponseDto<List<ResponsePostListDto>>> getPosts() {
        List<ResponsePostListDto> posts = postService.getPosts();
        ResponseCode status = ResponseCode.OK;
        GlobalResponseDto<List<ResponsePostListDto>> response = new GlobalResponseDto<>(status, posts);

        return ResponseEntity.ok().body(response);
    }

    @Override
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Void> resistPost( MultipartFile mimeImage, RequestResistPostDto requestResistPostDto) {
        postService.resistPost(mimeImage, requestResistPostDto);

        return ResponseEntity.noContent().build();
    }
}
