package com.krince.memegle.domain.tag.service;

import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.entity.TagMap;
import com.krince.memegle.domain.tag.repository.TagMapRepository;
import com.krince.memegle.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TagDomainServiceImpl implements TagDomainService {

    private final TagRepository tagRepository;
    private final TagMapRepository tagMapRepository;

    @Override
    public List<Tag> getTagListFromTagNameList(String[] tagNameList) {
        return Arrays.stream(tagNameList)
                .map(tag -> tagRepository.findByTagName(tag)
                        .orElseThrow(NoSuchElementException::new))
                .toList();
    }

    @Override
    public List<TagMap> registTagMapList(List<TagMap> tagMapList) {
        return tagMapRepository.saveAll(tagMapList);
    }
}
