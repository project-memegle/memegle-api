package com.krince.memegle.domain.image.controller;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.service.ImageServiceImpl;
import com.krince.memegle.global.constant.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tags({
        @Tag("test"),
        @Tag("unitTest")
})
@WebMvcTest(value = ImageController.class)
@DisplayName("이미지 컨트롤러 테스트(ImageController)")
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageServiceImpl imageService;

    @Nested
    @DisplayName("이미지 조회")
    class GetImage {

        @Nested
        @DisplayName("성공")
        class GetImageSuccess {

            @Test
            @WithMockUser
            @DisplayName("success")
            void getImage() throws Exception {
                //given
                String uri = "/apis/client/images/1";
                ViewImageDto mockViewImageDto = mock(ViewImageDto.class);
                when(imageService.getImage(any())).thenReturn(mockViewImageDto);

                //when, then
                mockMvc.perform(get(uri)
                                .with(csrf()))
                        .andExpect(status().isOk())
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("카테고리 이미지 리스트 조회")
    class GetCategoryImages {

        @Nested
        @DisplayName("성공")
        class GetCategoryImagesSuccess {

            @Test
            @WithMockUser
            @DisplayName("success")
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
                                .param("criteria", "CREATED_AT")
                                .with(csrf()))
                        .andExpect(status().isOk())
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("밈 이미지 등록 요청")
    class RegistMemeImage {

        @Nested
        @DisplayName("성공")
        class RegistMemeImageSuccess {

            @Test
            @DisplayName("success")
            @WithMockUser(username = "testUser")
            void registMemeImage() throws Exception {
                //given
                String uri = "/apis/client/images";
                String imageCategory = "MUDO";
                MockPart mockTags = new MockPart("tags", "공격 호통".getBytes());
                MockPart mockDelimiter = new MockPart("delimiter", " ".getBytes());
                MockMultipartFile mockMultipartFile = new MockMultipartFile(
                        "memeImageFile",
                        "testImage.jpg",
                        "image/jpeg",
                        "test image content".getBytes()
                );

                //when, then
                mockMvc.perform(multipart(uri)
                                .file(mockMultipartFile)
                                .part(mockTags)
                                .part(mockDelimiter)
                                .queryParam("imageCategory", imageCategory)
                                .with(csrf()))
                        .andDo(print())
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("실패")
        class RegistMemeImageFail {

            @Test
            @DisplayName("로그인 하지 않은 유저는 밈 이미지 등록 요청을 할 수 없다.")
            @WithAnonymousUser
            void registMemeImageNotLogin() throws Exception {
                //given
                String uri = "/apis/client/images";
                String imageCategory = "MUDO";
                MockPart mockTags = new MockPart("tags", "공격 호통".getBytes());
                MockPart mockDelimiter = new MockPart("delimiter", " ".getBytes());
                MockMultipartFile mockMultipartFile = new MockMultipartFile(
                        "memeImageFile",
                        "testImage.jpg",
                        "image/jpeg",
                        "test image content".getBytes()
                );

                //when, then
                mockMvc.perform(multipart(uri)
                                .file(mockMultipartFile)
                                .part(mockTags)
                                .part(mockDelimiter)
                                .queryParam("imageCategory", imageCategory)
                                .with(csrf()))
                        .andDo(print())
                        .andExpect(status().isUnauthorized());
            }
        }
    }
}