package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.RegistImageDto;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.global.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    ViewImageDto getImage(Long imageId);

    List<ViewImageDto> getCategoryImages(ImageCategory imageCategory, PageableDto pageableDto);

    String registMemeImage(RegistImageDto registImageDto) throws IOException;
}
