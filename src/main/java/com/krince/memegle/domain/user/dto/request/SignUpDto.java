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
public class SignUpDto {

    @Schema(title = "로그인 아이디", description = "회원 로그인 아이디(6 ~ 15 영어, 숫자 조합)", example = "testLoginId1")
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 6, max = 15, message = "아이디는 6자 이상 15자 이하여야합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어와 숫자만 사용할 수 있습니다.")
    private String loginId;

    @Schema(title = "비밀번호", description = "8 ~ 20자 영어 소문자, 대문자, 숫자, 특수문자 필수 조합", example = "TestPassword1!")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "8 ~ 20자 이내여야합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "영어 소문자, 대문자, 숫자, 특수문자의 조합이어야합니다.")
    private String password;

    @Schema(title = "이름", description = "2 ~ 30자 한글과 영문 조합", example = "테스트")
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 30, message = "2 ~ 30자 이내여야합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "한글과 영문만 사용할 수 있습니다.")
    private String name;

    @Schema(title = "닉네임", description = "2 ~ 20 한글과 영문 조합", example = "testNickname1")
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 20, message = "2 ~ 20자 이내여야합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]*$", message = "한글, 영문, 숫자만 사용할 수 있습니다.")
    private String nickname;
}
