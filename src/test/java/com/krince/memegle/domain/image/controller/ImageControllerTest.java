package com.krince.memegle.domain.image.controller;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.service.ImageServiceImpl;
import com.krince.memegle.global.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("이미지 컨트롤러 테스트")
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageServiceImpl imageService;

    @Test
    @DisplayName("이미지 조회 테스트 - 성공")
    void getImage() throws Exception {
        //given
        String uri = "/apis/client/images/1";
        ViewImageDto mockViewImageDto = mock(ViewImageDto.class);
        when(imageService.getImage(any())).thenReturn(mockViewImageDto);

        //when, then
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("카테고리 이미지 리스트 조회 테스트 - 성공")
    void getCategoryImages() throws Exception {
        //given
        String uri = "/apis/client/images/category";
        PageableDto mockPageableDto = mock(PageableDto.class);
        ViewImageDto mockViewImageDto = mock(ViewImageDto.class);
        List<ViewImageDto> viewImageDtos = List.of(mockViewImageDto);
        when(imageService.getCategoryImages(ImageCategory.MUDO, mockPageableDto)).thenReturn(viewImageDtos);

        //when, then
        mockMvc.perform(get(uri)
                        .param("imageCategory", ImageCategory.MUDO.toString())
                        .param("page", "1")
                        .param("size", "10")
                        .param("criteria", "CREATED_AT"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}