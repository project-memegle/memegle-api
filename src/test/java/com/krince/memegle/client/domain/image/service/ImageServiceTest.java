package com.krince.memegle.client.domain.image.service;

import com.krince.memegle.client.domain.image.entity.Image;
import com.krince.memegle.client.domain.image.repository.ImageRepository;
import com.krince.memegle.client.domain.post.entity.Post;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @InjectMocks
    ImageServiceImpl imageService;

    @Mock
    ImageRepository imageRepository;

    @Test
    @DisplayName("밈 이미지 생성 테스트")
    void createMemeImage() {
        //given
        String memeImageUrl = "https://testMemeImageUrl.com";
        Post mockPost = mock(Post.class);

        //when
        Image createImage = imageService.createMemeImage(memeImageUrl, mockPost);

        //then
        assertThat(createImage.getPost()).isEqualTo(mockPost);
        assertThat(createImage.getImageUrl()).isEqualTo(memeImageUrl);
    }

    @Test
    void saveMemeImage() {
        //given
        String memeImageUrl = "https://testMemeImageUrl.com";
        Post mockPost = mock(Post.class);
        Image createImage = imageService.createMemeImage(memeImageUrl, mockPost);
        when(imageRepository.save(createImage)).thenReturn(createImage);

        //when
        Image savedImage = imageService.saveMemeImage(createImage);

        //then
        assertThat(savedImage).isEqualTo(createImage);
        assertThat(savedImage.getImageUrl()).isEqualTo(memeImageUrl);
    }
}