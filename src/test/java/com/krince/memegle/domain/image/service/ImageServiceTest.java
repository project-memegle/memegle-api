package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.ImageRepository;
import com.krince.memegle.global.Criteria;
import com.krince.memegle.global.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@DisplayName("이미지 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @InjectMocks
    private ImageServiceImpl imageService;

    @Mock
    ImageRepository imageRepository;

    @Test
    @DisplayName("이미지 조회 테스트 - 성공")
    void getImageSuccess() {
        //given
        Image image = generateMockImage();
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));

        //when
        ViewImageDto viewImageDto = imageService.getImage(1L);

        //then
        assertThat(viewImageDto.getId()).isEqualTo(1L);
        assertThat(viewImageDto.getImageUrl()).isEqualTo("www.testImageUrl.com");
    }

    private Image generateMockImage() {
        Image image = mock(Image.class);
        when(image.getId()).thenReturn(1L);
        when(image.getImageUrl()).thenReturn("www.testImageUrl.com");
        when(image.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(image.getModifiedAt()).thenReturn(LocalDateTime.now());

        return image;
    }

    @Test
    @DisplayName("이미지 조회 테스트 - 없는 이미지")
    void getImageNotFindResource() {
        //given
        when(imageRepository.findById(anyLong())).thenThrow(NoSuchElementException.class);

        //when, then
        assertThatThrownBy(() -> imageService.getImage(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("카테고리 이미지 리스트 조회 - 성공")
    void getCategoryImages() {
        //given
        PageableDto mockPageableDto = mock(PageableDto.class);
        when(mockPageableDto.getPage()).thenReturn(1);
        when(mockPageableDto.getSize()).thenReturn(10);
        when(mockPageableDto.getCriteria()).thenReturn(Criteria.CREATED_AT);
        Page<Image> mockPage = mock(Page.class);
        when(imageRepository.findAllByImageCategory(any(), any())).thenReturn(mockPage);

        //when
        List<ViewImageDto> images = imageService.getCategoryImages(ImageCategory.MUDO, mockPageableDto);

        //then
        assertThat(images.size()).isEqualTo(0);
    }
}