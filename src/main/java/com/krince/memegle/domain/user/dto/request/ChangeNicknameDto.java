package com.krince.memegle.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ChangeNicknameDto {

    @Schema(title = "닉네임", description = "2 ~ 20 한글과 영문 조합", example = "testNickname1")
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 20, message = "2 ~ 20자 이내여야합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]*$", message = "한글, 영문, 숫자만 사용할 수 있습니다.")
    private String nickname;
}
