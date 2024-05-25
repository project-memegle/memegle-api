package com.krince.memegle.client.domain.post.service;

import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;
import com.krince.memegle.admin.domain.post.repository.AdminPostRepository;
import com.krince.memegle.admin.domain.post.service.PostService;
import com.krince.memegle.client.domain.image.entity.Image;
import com.krince.memegle.client.domain.post.entity.Post;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private AdminPostRepository adminPostRepository;

    @Test
    @DisplayName("밈 이미지 전체 조회 테스트")
    void getPosts() {
        Post post1 = mock(Post.class);
        Image image1 = mock(Image.class);
        when(post1.getImages()).thenReturn(List.of(image1));
        List<Post> posts = List.of(post1);

        when(adminPostRepository.findAllByIsConfirm(anyBoolean())).thenReturn(posts);
        List<ResponseGetAdminPostsDto> responseGetAdminPostsDtos = postService.getAdminPosts();

        assertThat(responseGetAdminPostsDtos.size()).isEqualTo(1);
    }
}