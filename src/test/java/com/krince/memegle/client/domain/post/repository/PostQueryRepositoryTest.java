package com.krince.memegle.client.domain.post.repository;

import com.krince.memegle.client.domain.image.entity.Image;
import com.krince.memegle.client.domain.image.repository.ImageRepository;
import com.krince.memegle.client.domain.post.dto.response.ResponsePostListDto;
import com.krince.memegle.client.domain.post.entity.Post;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostQueryRepositoryTest {

    @Autowired
    PostQueryRepository queryRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ImageRepository imageRepository;

    @BeforeEach
    void before() {
        Post post1 = Post.builder()
                .content("testContent1")
                .isConfirm(true)
                .build();

        Post post2 = Post.builder()
                .content("testContent2")
                .isConfirm(true)
                .build();

        Image image1 = Image.builder()
                .post(post1)
                .imageUrl("https://test1.com")
                .build();

        Image image2 = Image.builder()
                .post(post1)
                .imageUrl("https://test2.com")
                .build();

        Image image3 = Image.builder()
                .post(post1)
                .imageUrl("https://test3.com")
                .build();

        postRepository.save(post1);
        postRepository.save(post2);
        imageRepository.save(image1);
        imageRepository.save(image2);
        imageRepository.save(image3);

    }

    @Test
    @DisplayName("queryDSL로 이미지 포함한 게시물 조회")
    void findAllByIsConfirm() {
        //given, when
        List<ResponsePostListDto> findResponsePostListDtos = queryRepository.findAllByIsConfirm(false);

        //then
        assertThat(findResponsePostListDtos.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("queryDSL로 이미지 포함한 게시물 조회(조회 성공)")
    void findAllByIsConfirmSuccess() {
        //given
        List<ResponsePostListDto> findResponsePostListDtos = queryRepository.findAllByIsConfirm(true);

        //when

        //then
        assertThat(findResponsePostListDtos.size()).isEqualTo(2);
    }
}