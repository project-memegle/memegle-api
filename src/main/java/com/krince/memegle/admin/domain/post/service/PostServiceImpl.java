package com.krince.memegle.admin.domain.post.service;

import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;
import com.krince.memegle.admin.domain.post.repository.AdminPostRepository;
import com.krince.memegle.client.domain.post.entity.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminPostService")
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final AdminPostRepository adminPostRepository;

    @Override
    public List<ResponseGetAdminPostsDto> getAdminPosts() {

        boolean isConfirm = false;
        List<Post> findPosts = adminPostRepository.findAllByIsConfirm(isConfirm);

        return findPosts.stream().map(ResponseGetAdminPostsDto::fromDto).toList();
    }

    @Override
    @Transactional
    public void deletePost(Post post) {

        adminPostRepository.delete(post);
    }
}
