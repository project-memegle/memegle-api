package com.krince.memegle.admin.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;
import com.krince.memegle.admin.domain.post.service.PostService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
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

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void before() {

    }

    @Test
    @DisplayName("개발자 메인 페이지 조회 테스트")
    void getAdminPostsTest() throws Exception {
        ResponseGetAdminPostsDto postDto1 = ResponseGetAdminPostsDto.builder().build();
        List<ResponseGetAdminPostsDto> posts = Arrays.asList(postDto1);
        when(postService.getAdminPosts()).thenReturn(posts);

        mockMvc.perform(
                        get("/api/admin/posts")
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("status").value("OK"))
                .andExpect(jsonPath("code").value(20000))
                .andExpect(jsonPath("message").value("요청이 성공적으로 처리되었습니다. 요청한 데이터를 반환합니다."))
                .andExpect(jsonPath("results[0].postId").value(anything()))
                .andExpect(jsonPath("results[0].imageId").value(anything()))
                .andExpect(jsonPath("results[0].mimeImageUrl").value(anything()))
                .andExpect(jsonPath("results[0].content").value(anything()))
                .andExpect(jsonPath("results[0].createdAt").value(anything()));
    }

}