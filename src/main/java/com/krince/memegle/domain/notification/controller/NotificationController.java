package com.krince.memegle.domain.notification.controller;

import com.krince.memegle.domain.notification.dto.IsUnReadNotificationDto;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.response.customexception.EmptyTokenExceptionResponse;
import com.krince.memegle.global.response.customexception.ExpiredTokenExceptionResponse;
import com.krince.memegle.global.response.customexception.InternalServerErrorExceptionResponse;
import com.krince.memegle.global.response.customexception.InvalidTokenExceptionResponse;
import com.krince.memegle.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.*;

@Tag(name = "알림", description = "알림 관련 API")
public interface NotificationController {

    @Operation(summary = "미열람 알림 유무 조회(미구현 api)", description = "읽지 않은 알림이 있는지 조회합니다.")
    @ApiResponse(description = "알림 상태 유무 조회 성공", responseCode = "20000")
    @ApiResponse(description = "유효하지 않은 토큰", responseCode = "40101",
    content = @Content(mediaType = APPLICATION_JSON_VALUE,
    schema = @Schema(implementation = InvalidTokenExceptionResponse.class)))
    @ApiResponse(description = "토큰 정보 누락", responseCode = "40103",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = EmptyTokenExceptionResponse.class)))
    @ApiResponse(description = "만료된 토큰", responseCode = "40104",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExpiredTokenExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<SuccessResponse<IsUnReadNotificationDto>> getIsUnReadNotification(@Parameter(hidden = true) CustomUserDetails userDetails);
}
