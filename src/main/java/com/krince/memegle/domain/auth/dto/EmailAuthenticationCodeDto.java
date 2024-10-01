package com.krince.memegle.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class EmailAuthenticationCodeDto {

    @Schema(title = "이메일", description = "이메일 형식만 허용", example = "joyHan@nufyn.com")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Size(min = 2, max = 255, message = "이메일은 2자 이상 255자 이하여야합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식을 확인해주세요.")
    private String email;

    @Schema(title = "인증코드", description = "6자리 영어 대문자, 숫자 조합", example = "1Q2W3E")
    @NotBlank(message = "인증코드를 입력해주세요.")
    @Size(min = 6, max = 6, message = "인증코드는 정확히 6자리여야 합니다.")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "인증코드는 영어 대문자와 숫자로만 이루어져야 합니다.")
    private String authenticationCode;
}