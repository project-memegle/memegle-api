package com.krince.memegle.domain.post.controller;

import com.krince.memegle.domain.post.dto.response.ResponsePostListDto;
import com.krince.memegle.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {

    private final PostService postService;

    @Override
    @GetMapping()
    public ResponseEntity<List<ResponsePostListDto>> getPosts() {
        List<ResponsePostListDto> posts = postService.getPosts();

        return ResponseEntity.status(200).body(posts);
    }

    @Override
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Void> resistPost(MultipartFile mimeImage, String content) {
        postService.resistPost(mimeImage, content);

        return ResponseEntity.status(204).build();
    }
}
