package com.krince.memegle.admin.domain.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "RequestAdminLoginDto")
public class RequestAdminLoginDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Schema(description = "관리자 로그인 아이디")
    private String adminId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(description = "관리자 비밀번호")
    private String password;
}
