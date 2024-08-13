package com.krince.memegle.domain.image.controller;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.service.ImageService;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/client/images")
@RequiredArgsConstructor
public class ImageControllerImpl implements ImageController {

    private final ImageService imageService;

    @Override
    @GetMapping("/{imageId}")
    public ResponseEntity<SuccessResponse<ViewImageDto>> getImage(@PathVariable Long imageId, Authentication authentication, CustomUserDetails userDetails) {

        ViewImageDto viewImageDto = imageService.getImage(imageId);
        ResponseCode responseCode = ResponseCode.OK;
        SuccessResponse<ViewImageDto> responseBody = new SuccessResponse<>(responseCode, viewImageDto);
        return ResponseEntity.status(responseCode.getHttpCode()).body(responseBody);
    }
}
