package com.krince.memegle.domain.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;

@Tag(name = "회원", description = "회원 관련 API")
@PreAuthorize("hasRole('ROLE_USER')")
public interface UserController {
}
