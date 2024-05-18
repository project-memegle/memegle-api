package com.krince.memegle.admin.domain.image.service;


import com.krince.memegle.admin.domain.image.dto.request.RequestConfirmMimeImageDto;

public interface ImageService {

    void confirmMimeImage(Long mimeImageId, RequestConfirmMimeImageDto requestConfirmMimeImageDto);
}
