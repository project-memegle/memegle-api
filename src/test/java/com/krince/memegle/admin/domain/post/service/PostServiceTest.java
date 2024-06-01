package com.krince.memegle.admin.domain.post.service;

import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;
import com.krince.memegle.admin.domain.post.repository.AdminPostRepository;
import com.krince.memegle.client.domain.image.entity.Image;
import com.krince.memegle.client.domain.post.entity.Post;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    PostServiceImpl postService;

    @Mock
    AdminPostRepository postRepository;

    @Test
    @DisplayName("관리자 메인 페이지 조회 - 승인 대기 게시물 있음")
    void getAdminPosts() {
        //given
        Post post1 = mock(Post.class);
        Image image1 = mock(Image.class);

        when(post1.getId()).thenReturn(1L);
        when(post1.getContent()).thenReturn("테스트 이자쉭아~");
        when(post1.getCreatedAt()).thenReturn(new Date());
        when(post1.getImages()).thenReturn(List.of(image1));
        when(image1.getId()).thenReturn(1L);
        when(image1.getImageUrl()).thenReturn("https://test.com");
        when(postRepository.findAllByIsConfirm(false)).thenReturn(List.of(post1));

        //when
        List<ResponseGetAdminPostsDto> responseGetAdminPostsDtos = postService.getAdminPosts();

        //then
        assertThat(responseGetAdminPostsDtos.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("관리자 메인 페이지 조회 - 승인 대기 게시물 없음")
    void getAdminPostsEmpty() {
        //given, when
        List<ResponseGetAdminPostsDto> responseGetAdminPostsDtos = postService.getAdminPosts();

        //then
        assertThat(responseGetAdminPostsDtos.size()).isEqualTo(0);
    }

    @Test
    void deletePost() {
    }
}