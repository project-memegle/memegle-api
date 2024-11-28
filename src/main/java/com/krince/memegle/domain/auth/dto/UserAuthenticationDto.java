package com.krince.memegle.domain.auth.dto;

import com.krince.memegle.global.constant.AuthenticationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UserAuthenticationDto {

    @Schema(title = "회원 이름", description = "2 ~ 15자 영어, 한글", example = "한지희")
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 15, message = "이름은 2자 이상 15자 이하여야합니다.")
    @Pattern(regexp = "(^[a-zA-Z]{2,15}$)|(^[가-힣]{2,15}$)", message = "영어, 한글만 허용됩니다.")
    private String userName;

    @Schema(title = "이메일", description = "이메일 형식만 허용", example = "joyHan@nufyn.com")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Size(min = 2, max = 255, message = "이메일은 2자 이상 255자 이하여야합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식을 확인해주세요.")
    private String email;

    @Schema(title = "인증 타입", description = "어떤 인증을 위해서 인증 코드 메일을 보내는지 결정합니다.", example = "SIGN_UP")
    @NotNull(message = "인증 타입은 필수값입니다.")
    private AuthenticationType authenticationType;
}
