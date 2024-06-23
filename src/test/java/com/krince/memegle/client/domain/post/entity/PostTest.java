package com.krince.memegle.client.domain.post.entity;

import com.krince.memegle.client.domain.image.entity.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PostTest {

    Post post;
    Image image1;

    @BeforeEach
    void before() {
        post = Post.builder()
                .content("testContent")
                .build();

        image1 = Image.builder()
                .imageUrl("https://testImage1.com")
                .post(post)
                .build();
    }

    @Test
    void changeIsConfirmTest() {
        //given
        Boolean isConfirm = true;

        //when
        post.changeIsConfirm(isConfirm);

        //then
        assertThat(post.getIsConfirm()).isEqualTo(isConfirm);
    }
}