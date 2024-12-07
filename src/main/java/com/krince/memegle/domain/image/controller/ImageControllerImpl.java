package com.krince.memegle.domain.image.controller;

import com.krince.memegle.domain.image.dto.ImageIdDto;
import com.krince.memegle.domain.image.dto.RegistImageDto;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.service.ImageService;
import com.krince.memegle.global.constant.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;
import com.krince.memegle.global.exception.UndevelopedApiException;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.response.SuccessResponseCode;
import com.krince.memegle.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.krince.memegle.global.response.SuccessResponseCode.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/apis/client/images")
@RequiredArgsConstructor
public class ImageControllerImpl implements ImageController {

    private final ImageService imageService;

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

        return ResponseEntity
                .status(NO_CONTENT.getHttpCode())
                .build();
    }

    @Override
    @GetMapping("/{imageId}")
    public ResponseEntity<SuccessResponse<ViewImageDto>> getImage(@PathVariable Long imageId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        ViewImageDto viewImageDto = imageService.getImage(imageId);
        SuccessResponseCode responseCode = OK;
        SuccessResponse<ViewImageDto> responseBody = new SuccessResponse<>(responseCode, viewImageDto);

        return ResponseEntity.status(responseCode.getHttpCode()).body(responseBody);
    }

    @Override
    @GetMapping("/bookmark")
    public ResponseEntity<SuccessResponse<ViewImageDto>> getBookmarkImages(@AuthenticationPrincipal CustomUserDetails userDetails) {
        throw new UndevelopedApiException();
    }

    @ApiResponse(description = "이미지 즐겨찾기 추가 및 삭제 성공", responseCode = "20400", content = @Content)
    @Override
    @PostMapping("/bookmark")
    public ResponseEntity<ResponseCode> changeBookmarkState(
            @RequestBody @Valid ImageIdDto imageIdDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        throw new UndevelopedApiException();
    }

    @Override
    @GetMapping("/category")
    public ResponseEntity<SuccessResponse<List<ViewImageDto>>> getCategoryImages(
            @RequestParam ImageCategory imageCategory,
            @ModelAttribute @Valid PageableDto pageableDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<ViewImageDto> viewImageDtos = imageService.getCategoryImages(imageCategory, pageableDto);
        SuccessResponseCode responseCode = OK;
        SuccessResponse<List<ViewImageDto>> responseBody = new SuccessResponse<>(responseCode, viewImageDtos);

        return ResponseEntity.status(responseCode.getHttpCode()).body(responseBody);
    }

    @Override
    @GetMapping("/tag")
    public ResponseEntity<SuccessResponse<List<ViewImageDto>>> getTagImages(
            @RequestParam @NotBlank @Valid String tagName,
            @ModelAttribute @Valid PageableDto pageableDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        throw new UndevelopedApiException();
    }
}
