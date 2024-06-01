package com.krince.memegle.admin.domain.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.krince.memegle.admin.domain.image.dto.request.RequestConfirmMemeImageDto;
import com.krince.memegle.admin.domain.image.repository.AdminImageRepository;
import com.krince.memegle.admin.domain.post.service.PostServiceImpl;
import com.krince.memegle.client.domain.image.entity.Image;
import com.krince.memegle.client.domain.post.entity.Post;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;


@Transactional
@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @InjectMocks
    ImageServiceImpl imageService;

    @Mock
    AdminImageRepository imageRepository;

    @Mock
    AmazonS3Client amazonS3Client;

    @Mock
    PostServiceImpl postService;

    @Test
    @DisplayName("밈 이미지 승인 테스트")
    void confirmMemeImageIsConfirm() {
        //given
        RequestConfirmMemeImageDto requestConfirmMemeImageDto = mock(RequestConfirmMemeImageDto.class);
        Image image = mock(Image.class);
        Post post = mock(Post.class);

        when(requestConfirmMemeImageDto.getIsConfirm()).thenReturn(true);
        when(image.getPost()).thenReturn(post);
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));

        //when
        imageService.confirmMemeImage(1L, requestConfirmMemeImageDto);

        //then
        verify(post).changeIsConfirm(true);
        verify(image, never()).getImageUrl();
        verify(imageRepository, never()).delete(image);
        verify(postService, never()).deletePost(post);
    }

    @Test
    @DisplayName("밈 이미지 기각 테스트")
    void confirmMemeImageIsNotConfirm() {
        //given
        RequestConfirmMemeImageDto requestConfirmMemeImageDto = mock(RequestConfirmMemeImageDto.class);
        Image image = mock(Image.class);
        Post post = mock(Post.class);

        when(requestConfirmMemeImageDto.getIsConfirm()).thenReturn(false);
        when(image.getPost()).thenReturn(post);
        when(image.getImageUrl()).thenReturn("https://amazonaws.com/testImage.jpeg");
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));

        //when
        imageService.confirmMemeImage(1L, requestConfirmMemeImageDto);

        //then
        verify(imageRepository).delete(image);
        verify(postService).deletePost(post);

        verify(post, never()).changeIsConfirm(true);
    }
}