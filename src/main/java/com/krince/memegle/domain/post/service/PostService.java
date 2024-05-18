package com.krince.memegle.domain.post.service;

import com.krince.memegle.domain.post.dto.response.ResponsePostListDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    void resistPost(MultipartFile mimeImage, String content);

    List<ResponsePostListDto> getPosts();
}
