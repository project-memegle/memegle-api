package com.krince.memegle.admin.domain.post.controller;

import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;
import com.krince.memegle.admin.domain.post.service.PostService;
import com.krince.memegle.global.Role;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.security.CustomUserDetails;
import com.krince.memegle.global.security.CustomUserDetailsService;
import com.krince.memegle.global.security.JwtProvider;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.anything;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class PostControllerTest {

    @MockBean
    CustomUserDetailsService userDetailsService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @BeforeEach
    public void before() {

    }

    @Test
    @DisplayName("관리자 메인 페이지 조회 테스트")
    void getAdminPostsTest() throws Exception {
        when(userDetailsService.loadUserById(1L)).thenReturn(new CustomUserDetails(1L, Role.ROLE_ADMIN));
        String token = jwtProvider.createAccessToken(1L, Role.ROLE_ADMIN);
        ResponseCode responseCode = ResponseCode.OK;

        ResponseGetAdminPostsDto postDto1 = ResponseGetAdminPostsDto.builder().build();
        List<ResponseGetAdminPostsDto> posts = List.of(postDto1);
        when(postService.getAdminPosts()).thenReturn(posts);

        mockMvc.perform(
                        get("/api/admin/posts")
                                .header("Authorization", token)
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(responseCode.getIsSuccess()))
                .andExpect(jsonPath("status").value(responseCode.getHttpStatus()))
                .andExpect(jsonPath("code").value(responseCode.getCode()))
                .andExpect(jsonPath("message").value(responseCode.getMessage()))
                .andExpect(jsonPath("results[0].postId").value(anything()))
                .andExpect(jsonPath("results[0].imageId").value(anything()))
                .andExpect(jsonPath("results[0].mimeImageUrl").value(anything()))
                .andExpect(jsonPath("results[0].content").value(anything()))
                .andExpect(jsonPath("results[0].createdAt").value(anything()));
    }

}