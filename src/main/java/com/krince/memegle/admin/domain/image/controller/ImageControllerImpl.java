package com.krince.memegle.admin.domain.image.controller;

import com.krince.memegle.admin.domain.image.dto.request.RequestConfirmMemeImageDto;
import com.krince.memegle.admin.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminImageController")
@RequestMapping("/api/admin/images")
@RequiredArgsConstructor
public class ImageControllerImpl implements ImageController {

    private final ImageService imageService;

    @Override
    @PostMapping("/{memeImageId}")
    public ResponseEntity<Void> confirmMemeImage(Long memeImageId, RequestConfirmMemeImageDto requestConfirmMemeImageDto) {
        imageService.confirmMemeImage(memeImageId, requestConfirmMemeImageDto);

        return ResponseEntity.noContent().build();
    }
}
