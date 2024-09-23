package com.krince.memegle.domain.image.dto;

import com.krince.memegle.global.ImageCategory;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class RegistImageDto {

    private MultipartFile memeImageFile;
    private ImageCategory imageCategory;
    private String tags;
    private String delimiter;
}
