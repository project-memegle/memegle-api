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

        Boolean isConfirm = false;

        List<Post> findPosts = adminPostRepository.findAllByIsConfirm(isConfirm);
        List<ResponseGetAdminPostsDto> responseGetAdminPostsDtos = findPosts.stream().map(post -> {
            return ResponseGetAdminPostsDto.builder()
                    .postId(post.getId())
                    .imageId(post.getImages().get(0).getId())
                    .mimeImageUrl(post.getImages().get(0).getImageUrl())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .build();
        }).toList();

        return responseGetAdminPostsDtos;
    }

    @Override
    @Transactional
    public void deletePost(Post post) {

        adminPostRepository.delete(post);
    }
}
