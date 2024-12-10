package com.krince.memegle.domain.tag.service;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.entity.TagMap;

import java.util.List;

public interface TagApplicationService {

    List<Tag> getTags(String tags, String delimiter);

    TagMap registTagMap(Image image, Tag tag);
}
