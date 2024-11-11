package com.krince.memegle.domain.tag.service.fake;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.entity.TagMap;
import com.krince.memegle.domain.tag.repository.TagMapRepository;
import com.krince.memegle.domain.tag.repository.TagRepository;
import com.krince.memegle.domain.tag.service.TagService;

import java.util.List;

public class FakeTagService implements TagService {

    private final TagRepository tagRepository;
    private final TagMapRepository tagMapRepository;

    public FakeTagService(TagRepository tagRepository, TagMapRepository tagMapRepository) {
        this.tagRepository = tagRepository;
        this.tagMapRepository = tagMapRepository;
    }

    @Override
    public List<Tag> getTags(String tags, String delimiter) {
        Tag tag1 = Tag.builder()
                .id(1L)
                .tagName("testTag1")
                .build();

        Tag tag2 = Tag.builder()
                .id(2L)
                .tagName("testTag2")
                .build();

        return List.of(tag1, tag2);
    }

    @Override
    public TagMap registTagMap(Image image, Tag tag) {
        return null;
    }
}
