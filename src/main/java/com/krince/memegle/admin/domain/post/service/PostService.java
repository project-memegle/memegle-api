package com.krince.memegle.admin.domain.post.service;

import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;
import com.krince.memegle.client.domain.post.entity.Post;

import java.util.List;

public interface PostService {
    List<ResponseGetAdminPostsDto> getAdminPosts();

    void deletePost(Post post);
}
