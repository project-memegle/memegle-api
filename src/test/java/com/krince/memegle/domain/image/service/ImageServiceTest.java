package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
}