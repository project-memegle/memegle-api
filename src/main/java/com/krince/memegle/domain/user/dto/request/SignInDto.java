package com.krince.memegle.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInDto {

    @Schema(title = "로그인 아이디", description = "회원 로그인 아이디(6 ~ 15 영어 소문자, 숫자 조합)", example = "testloginid1")
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 6, max = 15, message = "아이디는 6자 이상 15자 이하여야합니다.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "영어 소문자와 숫자만 사용할 수 있습니다.")
    private String loginId;

    @Schema(title = "비밀번호", description = "8 ~ 20자", example = "TestPassword1!")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "8 ~ 20자 이내여야합니다.")
    @Pattern(regexp = "^(?=\\S+$).{8,20}$", message = "비밀번호는 공백 없이 8자 이상 20자 이하여야 합니다.")
    private String password;
}
