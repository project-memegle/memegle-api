package com.krince.memegle.domain.user.dto.request;

import com.krince.memegle.global.constant.AuthenticationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ChangePasswordDto {

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

    @Schema(title = "인증 타입", description = "어떤 인증을 위해서 인증 코드 메일을 보내는지 결정합니다.", example = "SIGN_UP")
    @NotNull(message = "인증 타입은 필수값입니다.")
    private AuthenticationType authenticationType;

    @Schema(title = "로그인 아이디", description = "회원 로그인 아이디(6 ~ 15 영어 소문자, 숫자 조합)", example = "testloginid1")
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 6, max = 15, message = "아이디는 6자 이상 15자 이하여야합니다.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "영어 소문자와 숫자만 사용할 수 있습니다.")
    private String loginId;

    @Schema(title = "비밀번호", description = "8 ~ 20자", example = "TestPassword1")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "8 ~ 20자 이내여야합니다.")
    @Pattern(regexp = "^(?=\\S+$).{8,20}$", message = "비밀번호는 공백 없이 8자 이상 20자 이하여야 합니다.")
    private String password;
}
