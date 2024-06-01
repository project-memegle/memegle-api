package com.krince.memegle.client.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.client.domain.post.dto.request.RequestResistPostDto;
import com.krince.memegle.client.domain.post.dto.response.ResponsePostListDto;
import com.krince.memegle.client.domain.post.service.PostService;
import com.krince.memegle.global.response.ResponseCode;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.hamcrest.Matchers.anything;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void before() {
    }

    @Test
    @DisplayName("게시물 전체 조회 테스트")
    void getPostsTest() throws Exception {
        //given
        ResponsePostListDto mockResponseDto = mock(ResponsePostListDto.class);
        List<ResponsePostListDto> posts = List.of(mockResponseDto);
        ResponseCode responseCode = ResponseCode.OK;

        //when
        when(postService.getPosts()).thenReturn(posts);

        //then
        mockMvc.perform(get("/api/posts").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(responseCode.getIsSuccess()))
                .andExpect(jsonPath("status").value(responseCode.getHttpStatus()))
                .andExpect(jsonPath("code").value(responseCode.getCode()))
                .andExpect(jsonPath("message").value(responseCode.getMessage()))
                .andExpect(jsonPath("results").value(anything()))
                .andExpect(jsonPath("results[0].postId").value(anything()))
                .andExpect(jsonPath("results[0].postImageUrl").value(anything()))
                .andExpect(jsonPath("results[0].likeCount").value(anything()))
                .andExpect(jsonPath("results[0].createdAt").value(anything()));
    }

    @Test
    @DisplayName("밈 이미지 등록 신청 테스트")
    void resistPostTest() throws Exception {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("mimeImage", "test.jpg", "image/jpeg", "testContent".getBytes());
        RequestResistPostDto requestResistPostDto = RequestResistPostDto.builder().content("testContent").build();
        String mappingDto = objectMapper.writeValueAsString(requestResistPostDto);
        MockMultipartFile dto = new MockMultipartFile("dto", "dto.json", "application/json", mappingDto.getBytes());

        // when
        doNothing().when(postService).resistPost(any(MultipartFile.class), any(RequestResistPostDto.class));

        //then
        mockMvc.perform(multipart("/api/posts")
                        .file(mockMultipartFile)
                        .file(dto))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
