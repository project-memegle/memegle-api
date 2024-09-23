package com.krince.memegle.domain.tag.service;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.entity.TagMap;
import com.krince.memegle.domain.tag.repository.TagMapRepository;
import com.krince.memegle.domain.tag.repository.TagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("태그 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks
    TagServiceImpl tagService;

    @Mock
    TagRepository tagRepository;

    @Mock
    TagMapRepository tagMapRepository;

    @Test
    @DisplayName("태그 이름으로 태그 리스트 조회")
    void getTags() {
        //given
        String tags = "testTag1 testTag2 testTag3 testTag4 testTag5 testTag6";
        String delimiter = " ";
        Tag mockTag = mock(Tag.class);
        when(tagRepository.findByTagName(any())).thenReturn(Optional.of(mockTag));

        //when
        List<Tag> findTagList = tagService.getTags(tags, delimiter);

        //then
        assertThat(findTagList.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("태그맵 등록 테스트")
    void registTagMap() {
        //given
        Image mockImage = mock(Image.class);
        Tag mockTag = mock(Tag.class);
        TagMap mockTagMap = mock(TagMap.class);
        when(tagMapRepository.save(any())).thenReturn(mockTagMap);

        //when
        TagMap savedTagMap = tagService.registTagMap(mockImage, mockTag);

        //then
        verify(tagMapRepository, times(1)).save(any());
        assertThat(savedTagMap).isInstanceOf(TagMap.class);
    }
}