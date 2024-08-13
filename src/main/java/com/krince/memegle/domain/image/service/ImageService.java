package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.ViewImageDto;

public interface ImageService {

    ViewImageDto getImage(Long imageId);

}
