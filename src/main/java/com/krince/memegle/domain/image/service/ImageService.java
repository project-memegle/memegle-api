package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.post.entity.Post;

public interface ImageService {
    Image createMimeImage(String mimeImageUrl, Post post);

    Image saveMimeImage(Image image);
}
