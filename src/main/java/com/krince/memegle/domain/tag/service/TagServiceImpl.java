package com.krince.memegle.domain.tag.service;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.entity.TagMap;
import com.krince.memegle.domain.tag.repository.TagMapRepository;
import com.krince.memegle.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapRepository tagMapRepository;

    @Override
    public List<Tag> getTags(String tags, String delimiter) {
        return Arrays.stream(tags.trim().split(delimiter))
                .map(tag -> tagRepository.findByTagName(tag)
                        .orElseThrow(NoSuchElementException::new))
                .toList();
    }

    @Override
    @Transactional
    public TagMap registTagMap(Image image, Tag tag) {
        TagMap tagMap = TagMap.builder()
                .image(image)
                .tag(tag)
                .build();

        return tagMapRepository.save(tagMap);
    }
}
