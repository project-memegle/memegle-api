package com.krince.memegle.client.domain.image.entity;

import com.krince.memegle.client.domain.post.entity.Post;
import com.krince.memegle.client.domain.tag.entity.Tag;
import com.krince.memegle.client.domain.tag.entity.TagMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageTest {

    Image image;

    @BeforeEach
    void before() {
        image = Image.builder()
                .post(mock(Post.class))
                .imageUrl("https://test.com")
                .build();
    }
}