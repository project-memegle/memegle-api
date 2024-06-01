package com.krince.memegle.admin.domain.image.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.admin.domain.image.dto.request.RequestConfirmMemeImageDto;
import com.krince.memegle.admin.domain.image.service.ImageService;
import com.krince.memegle.global.Role;
import com.krince.memegle.global.security.CustomUserDetails;
import com.krince.memegle.global.security.CustomUserDetailsService;
import com.krince.memegle.global.security.JwtProvider;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ImageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ImageService imageService;

    @MockBean
    CustomUserDetailsService userDetailsService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("밈 이미지 승인, 반려처리 테스트")
    void confirmMemeImage() throws Exception {
        //given
        RequestConfirmMemeImageDto requestConfirmMemeImageDto = mock(RequestConfirmMemeImageDto.class);
        when(userDetailsService.loadUserById(1L)).thenReturn(new CustomUserDetails(1L, Role.ROLE_ADMIN));
        String token = jwtProvider.createAccessToken(1L, Role.ROLE_ADMIN);
        doNothing().when(imageService).confirmMemeImage(1L, requestConfirmMemeImageDto);

        //when, then
        mockMvc.perform(
                        post("/api/admin/images/{memeImageId}", 1L)
                                .header("Authorization", token)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestConfirmMemeImageDto))
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}