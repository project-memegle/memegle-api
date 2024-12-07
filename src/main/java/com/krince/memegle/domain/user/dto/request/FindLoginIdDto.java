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
public class FindLoginIdDto {

    @Schema(title = "이메일 인증코드", example = "1Q2W3E")
    @NotBlank(message = "이메일 인증코드를 입력해주세요.")
    @Size(min = 6, max = 6, message = "인증 코드는 6자입니다.")
    private String authenticationCode;


    @Schema(title = "인증 타입", description = "어떤 인증을 위해서 인증 코드 메일을 보내는지 결정합니다.", example = "ID")
    @NotNull(message = "인증 타입은 필수값입니다.")
    private AuthenticationType authenticationType;

    @Schema(title = "이메일", description = "이메일 형식만 허용", example = "joyHan@nufyn.com")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Size(min = 2, max = 255, message = "이메일은 2자 이상 255자 이하여야합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식을 확인해주세요.")
    private String email;
}
