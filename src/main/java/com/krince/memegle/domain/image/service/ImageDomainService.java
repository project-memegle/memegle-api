package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.global.constant.ImageCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImageDomainService {

    Image getImageFromId(Long imageId);

    Page<Image> getPageableImagesFromImageCategory(ImageCategory imageCategory, Pageable pageable);

    Image registImage(Image image);
}
