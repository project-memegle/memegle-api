package com.krince.memegle.admin.domain.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "관리자 로그인 api request body")
public class RequestAdminLoginDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Schema(description = "관리자 로그인 아이디", example = "admin")
    private String adminId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(description = "관리자 비밀번호", example = "password1234!")
    private String password;
}
