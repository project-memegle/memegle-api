package com.krince.memegle.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class LoginIdDto {

    @Schema(title = "회원 로그인 아이디", example = "testloginid1")
    private String loginId;
}
