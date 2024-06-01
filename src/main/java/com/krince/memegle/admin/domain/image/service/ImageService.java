package com.krince.memegle.admin.domain.image.service;


import com.krince.memegle.admin.domain.image.dto.request.RequestConfirmMemeImageDto;

public interface ImageService {

    void confirmMemeImage(Long memeImageId, RequestConfirmMemeImageDto requestConfirmMemeImageDto);
}
