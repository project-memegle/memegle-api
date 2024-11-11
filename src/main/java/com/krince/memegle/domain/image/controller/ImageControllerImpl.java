package com.krince.memegle.domain.image.controller;

import com.krince.memegle.domain.image.dto.RegistImageDto;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.service.ImageService;
import com.krince.memegle.global.constant.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.*;

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

    @Override
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseCode> registMemeImage(
            @RequestParam ImageCategory imageCategory,
            @RequestPart MultipartFile memeImageFile,
            @RequestPart @NotBlank String tags,
            @RequestPart @NotNull String delimiter,
            CustomUserDetails userDetails
    ) throws IOException {
        RegistImageDto registImageDto = RegistImageDto.builder()
                .imageCategory(imageCategory)
                .memeImageFile(memeImageFile)
                .tags(tags)
                .delimiter(delimiter)
                .build();

        imageService.registMemeImage(registImageDto);
        ResponseCode responseCode = ResponseCode.NO_CONTENT;

        return ResponseEntity
                .status(responseCode.getHttpCode())
                .build();
    }
}
