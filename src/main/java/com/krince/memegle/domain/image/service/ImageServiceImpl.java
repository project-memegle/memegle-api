package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public ViewImageDto getImage(Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(NoSuchElementException::new);

        return generateViewImageDto(image);
    }

    private ViewImageDto generateViewImageDto(Image image) {
        return ViewImageDto.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .createdAt(image.getCreatedAt())
                .modifiedAt(image.getModifiedAt())
                .build();
    }
}
