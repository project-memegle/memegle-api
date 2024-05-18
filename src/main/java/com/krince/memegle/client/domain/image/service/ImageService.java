package com.krince.memegle.client.domain.image.service;

import com.krince.memegle.client.domain.image.entity.Image;
import com.krince.memegle.client.domain.post.entity.Post;

public interface ImageService {
    Image createMimeImage(String mimeImageUrl, Post post);

    Image saveMimeImage(Image image);
}
