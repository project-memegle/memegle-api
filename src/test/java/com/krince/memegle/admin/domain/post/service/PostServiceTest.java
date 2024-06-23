package com.krince.memegle.admin.domain.post.service;

import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;
import com.krince.memegle.admin.domain.post.repository.AdminPostQueryRepository;
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

    @Mock
    AdminPostQueryRepository adminPostQueryRepository;

    @Test
    @DisplayName("관리자 메인 페이지 조회 - 승인 대기 게시물 있음")
    void getAdminPosts() {
        //given
        ResponseGetAdminPostsDto responseGetAdminPostsDto1 = mock(ResponseGetAdminPostsDto.class);

        when(adminPostQueryRepository.findAllByIsConfirm(false)).thenReturn(List.of(responseGetAdminPostsDto1));

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