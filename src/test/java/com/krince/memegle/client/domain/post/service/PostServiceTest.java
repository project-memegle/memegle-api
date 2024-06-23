package com.krince.memegle.client.domain.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.krince.memegle.client.domain.image.entity.Image;
import com.krince.memegle.client.domain.image.service.ImageServiceImpl;
import com.krince.memegle.client.domain.post.dto.request.RequestResistPostDto;
import com.krince.memegle.client.domain.post.dto.response.ResponsePostListDto;
import com.krince.memegle.client.domain.post.entity.Post;
import com.krince.memegle.client.domain.post.repository.PostQueryRepository;
import com.krince.memegle.client.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostQueryRepository postQueryRepository;

    @Mock
    private AmazonS3Client amazonS3Client;

    @Mock
    private ImageServiceImpl imageService;

    @Test
    @DisplayName("밈 이미지 등록 신청 테스트 - 성공")
    void resistPostSuccess() throws IOException {
        //given
        MultipartFile memeImage = mock(MultipartFile.class);
        when(memeImage.getOriginalFilename()).thenReturn("test.jpg");
        when(memeImage.getContentType()).thenReturn("image/jpeg");
        when(memeImage.getSize()).thenReturn(1L);
        when(memeImage.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));

        RequestResistPostDto requestResistPostDto = mock(RequestResistPostDto.class);
        when(requestResistPostDto.getContent()).thenReturn("testContent");

        when(amazonS3Client.getUrl(anyString(), anyString())).thenReturn(new java.net.URL("http://example.com"));

        //when
        postService.resistPost(memeImage, requestResistPostDto);

        //then
        verify(amazonS3Client).putObject(anyString(), anyString(), any(), any());
        verify(postRepository).save(any());
        verify(imageService).createMemeImage(any(), any());
    }

    @Test
    @DisplayName("밈 이미지 전체 조회 테스트")
    void getPosts() {
        ResponsePostListDto responsePostListDto1 = mock(ResponsePostListDto.class);
        List<ResponsePostListDto> responseDtos = List.of(responsePostListDto1);

        when(postQueryRepository.findAllByIsConfirm(anyBoolean())).thenReturn(responseDtos);
        List<ResponsePostListDto> responsePostListDtos = postService.getPosts();

        assertThat(responsePostListDtos.size()).isEqualTo(1);
    }
}