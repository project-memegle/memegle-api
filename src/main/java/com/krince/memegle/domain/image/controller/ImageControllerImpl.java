package com.krince.memegle.domain.image.controller;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.service.ImageService;
import com.krince.memegle.global.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Override
    @GetMapping("/category")
    public ResponseEntity<SuccessResponse<List<ViewImageDto>>> getCategoryImages(
            @RequestParam ImageCategory imageCategory,
            @ModelAttribute @Valid PageableDto pageableDto,
            CustomUserDetails userDetails) {

        List<ViewImageDto> viewImageDtos = imageService.getCategoryImages(imageCategory, pageableDto);
        ResponseCode responseCode = ResponseCode.OK;
        SuccessResponse<List<ViewImageDto>> responseBody = new SuccessResponse<>(responseCode, viewImageDtos);

        return ResponseEntity.status(responseCode.getHttpCode()).body(responseBody);
    }
}
