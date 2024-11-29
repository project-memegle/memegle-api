package com.krince.memegle.domain.user.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {

    private String accessToken;

    private String refreshToken;
}
