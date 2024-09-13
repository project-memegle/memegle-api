package com.krince.memegle.domain.tag.repository;

import com.krince.memegle.domain.tag.entity.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("테그 리포지토리 테스트")
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("태그 이름으로 조회 테스트")
    void findByTagName() {
        //given
        String tagName = "TEST_TAG_NAME";
        Tag tag = Tag.builder()
                .tagName(tagName)
                .build();
        tagRepository.save(tag);

        //when
        Tag findTag = tagRepository.findByTagName(tagName).orElseThrow();

        //then
        assertThat(findTag.getTagName()).isEqualTo(tagName);
    }

    @Test
    @DisplayName("없는 태그로 조회하면 예외가 발생한다.")
    void wrongTagNameFindByTagName() {
        //given
        String wrongTagName = "WRONG_TAG_NAME";

        // when, then
        assertThrows(NoSuchElementException.class, () -> tagRepository.findByTagName(wrongTagName).orElseThrow());
    }
}