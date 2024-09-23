package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.RegistImageDto;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.ImageRepository;
import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.entity.TagMap;
import com.krince.memegle.domain.tag.service.TagService;
import com.krince.memegle.global.Criteria;
import com.krince.memegle.global.ImageCategory;
import com.krince.memegle.global.aws.S3Service;
import com.krince.memegle.global.dto.PageableDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Mock
    S3Service s3Service;

    @Mock
    TagService tagService;

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

    @Test
    @DisplayName("밈 이미지 등록 신청 테스트")
    void registMemeImage() throws IOException {
        //given
        String testImageURL = "https://www.testImageURL.com";
        String tags = "testTag1 testTag2 testTag3";
        String delimiter = " ";

        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        RegistImageDto mockRegistImageDto = mock(RegistImageDto.class);
        Tag mockTag = mock(Tag.class);
        Image mockImage = mock(Image.class);
        TagMap mockTagMap = mock(TagMap.class);

        when(mockRegistImageDto.getImageCategory()).thenReturn(ImageCategory.MUDO);
        when(mockRegistImageDto.getMemeImageFile()).thenReturn(mockMultipartFile);
        when(mockRegistImageDto.getTags()).thenReturn(tags);
        when(mockRegistImageDto.getDelimiter()).thenReturn(delimiter);

        when(tagService.getTags(any(), any())).thenReturn(List.of(mockTag, mockTag, mockTag));
        when(s3Service.uploadFile(any())).thenReturn("https://www.testImageURL.com");
        when(imageRepository.save(any())).thenReturn(mockImage);
        when(tagService.registTagMap(any(), any())).thenReturn(mockTagMap);

        //when
        String imageURL = imageService.registMemeImage(mockRegistImageDto);

        //then
        assertThat(imageURL).isEqualTo(testImageURL);
    }
}