package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl {

    private final ImageRepository imageRepository;
}
