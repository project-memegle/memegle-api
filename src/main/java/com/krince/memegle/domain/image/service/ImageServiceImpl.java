package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.ImageRepository;
import com.krince.memegle.domain.post.entity.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public Image createMimeImage(String mimeImageUrl, Post post) {

        return Image.builder().
                post(post)
                .imageUrl(mimeImageUrl)
                .build();
    }

    @Override
    @Transactional
    public Image saveMimeImage(Image image) {

        return imageRepository.save(image);
    }
}
