package com.krince.memegle.client.domain.tag.repository;

import com.krince.memegle.client.domain.image.entity.Image;
import com.krince.memegle.client.domain.image.repository.ImageRepository;
import com.krince.memegle.client.domain.post.entity.Post;
import com.krince.memegle.client.domain.post.repository.PostRepository;
import com.krince.memegle.client.domain.tag.entity.Tag;
import com.krince.memegle.client.domain.tag.entity.TagMap;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
class TagMapRepositoryTest {

    @Autowired
    TagMapRepository tagMapRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void before() {
    }

    @Test
    @DisplayName("해당 태그가 포함된 태그맵 전체 조회")
    void findAllByTagId() {
        //given
        Tag tag1 = Tag.builder().tagName("testTag1").build();
        Tag tag2 = Tag.builder().tagName("testTag2").build();
        Tag tag3 = Tag.builder().tagName("testTag3").build();
        Post post = Post.builder().content("testContent").isConfirm(true).build();
        Post savedPost = postRepository.save(post);
        Image image = Image.builder().imageUrl("https://testImage.com").post(savedPost).build();
        Image savedImage = imageRepository.save(image);


        TagMap tagMap1 = TagMap.builder().tag(tag1).image(savedImage).build();
        TagMap tagMap2 = TagMap.builder().tag(tag2).image(savedImage).build();
        TagMap tagMap3 = TagMap.builder().tag(tag3).image(savedImage).build();


        tagRepository.saveAll(List.of(tag1, tag2, tag3));

        List<TagMap> tagMaps = tagMapRepository.saveAll(List.of(tagMap1, tagMap2, tagMap3));

        //when
        List<TagMap> findTagMaps = tagMapRepository.findAllByTagId(tagMaps.get(0).getId());

        //then
        Assertions.assertThat(findTagMaps.size()).isEqualTo(1);
    }
}