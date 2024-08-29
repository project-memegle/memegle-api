package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.global.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;

import java.util.List;

public interface ImageService {

    ViewImageDto getImage(Long imageId);

    List<ViewImageDto> getCategoryImages(ImageCategory imageCategory, PageableDto pageableDto);

}
