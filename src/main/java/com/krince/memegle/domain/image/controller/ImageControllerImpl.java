package com.krince.memegle.domain.image.controller;

import com.krince.memegle.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageControllerImpl {

    private final ImageService imageService;
}
