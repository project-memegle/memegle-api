package com.krince.memegle.client.domain.like.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;

@Tag(name = "좋아요", description = "좋아요 관련 API")
@PreAuthorize("hasRole('ROLE_USER')")
public interface LikeController {
}
