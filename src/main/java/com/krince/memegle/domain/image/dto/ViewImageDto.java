package com.krince.memegle.domain.image.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewImageDto {

    private Long id;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
