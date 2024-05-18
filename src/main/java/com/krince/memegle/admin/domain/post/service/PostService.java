package com.krince.memegle.admin.domain.post.service;

import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;

import java.util.List;

public interface PostService {
    List<ResponseGetAdminPostsDto> getAdminPosts();
}
