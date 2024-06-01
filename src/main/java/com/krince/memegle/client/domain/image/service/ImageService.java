package com.krince.memegle.client.domain.image.service;

import com.krince.memegle.client.domain.image.entity.Image;
import com.krince.memegle.client.domain.post.entity.Post;

public interface ImageService {
    Image createMemeImage(String mimeImageUrl, Post post);

    Image saveMemeImage(Image image);
}
