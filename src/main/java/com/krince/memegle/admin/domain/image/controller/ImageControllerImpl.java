package com.krince.memegle.admin.domain.image.controller;

import com.krince.memegle.admin.domain.image.dto.request.RequestConfirmMimeImageDto;
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
    @PostMapping("/{mimeImageId}")
    public ResponseEntity<Void> confirmMimeImage(Long mimeImageId, RequestConfirmMimeImageDto requestConfirmMimeImageDto) {

        imageService.confirmMimeImage(mimeImageId, requestConfirmMimeImageDto);

        return ResponseEntity.noContent().build();
    }
}
