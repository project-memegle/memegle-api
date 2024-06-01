package com.krince.memegle.client.domain.post.service;

import com.krince.memegle.client.domain.post.dto.request.RequestResistPostDto;
import com.krince.memegle.client.domain.post.dto.response.ResponsePostListDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    void resistPost(MultipartFile mimeImage, RequestResistPostDto requestResistPostDto) throws IOException;

    List<ResponsePostListDto> getPosts();
}
