package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.ImageRepository;
import com.krince.memegle.global.constant.ImageCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class ImageDomainServiceImpl implements ImageDomainService {

    private final ImageRepository imageRepository;

    @Override
    public Image getImageFromId(Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Page<Image> getPageableImagesFromImageCategory(ImageCategory imageCategory, Pageable pageable) {
        return imageRepository.findAllByImageCategory(imageCategory, pageable);
    }

    @Override
    public Image registImage(Image image) {
        return imageRepository.save(image);
    }
}
