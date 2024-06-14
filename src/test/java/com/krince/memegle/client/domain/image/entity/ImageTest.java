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

    @Test
    @DisplayName("태그가 없는 이미지의 태그 조회")
    void getTagNamesEmptyTags() {
        //given
        Image image = this.image;

        //when
        List<String> tagNames = image.getTagNames();

        //then
        assertThat(tagNames.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("태그가 있는 이미지의 태그 조회")
    void getTagNames() {
        //given
        String tagName = "testTagName";
        Tag tag = mock(Tag.class);
        TagMap tagMap = mock(TagMap.class);
        when(tagMap.getTag()).thenReturn(tag);
        when(tag.getTagName()).thenReturn(tagName);
        Image image = Image.builder()
                .post(mock(Post.class))
                .tagMaps(List.of(tagMap))
                .imageUrl("https://test.com")
                .build();

        //when
        List<String> tagNames = image.getTagNames();

        //then
        assertThat(tagNames.size()).isEqualTo(1);
        assertThat(tagNames.get(0)).isEqualTo(tagName);
    }
}