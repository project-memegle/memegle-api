package com.krince.memegle.admin.domain.post.service;

import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;
import com.krince.memegle.admin.domain.post.repository.AdminPostRepository;
import com.krince.memegle.admin.domain.post.repository.AdminPostQueryRepository;
import com.krince.memegle.client.domain.post.entity.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminPostService")
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final AdminPostRepository adminPostRepository;
    private final AdminPostQueryRepository adminPostQueryRepository;

    @Override
    public List<ResponseGetAdminPostsDto> getAdminPosts() {

        boolean isConfirm = false;

        return adminPostQueryRepository.findAllByIsConfirm(isConfirm);
    }

    @Override
    @Transactional
    public void deletePost(Post post) {

        adminPostRepository.delete(post);
    }
}
