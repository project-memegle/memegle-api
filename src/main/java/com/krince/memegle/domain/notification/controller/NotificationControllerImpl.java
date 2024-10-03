package com.krince.memegle.domain.notification.controller;

import com.krince.memegle.domain.notification.dto.IsUnReadNotificationDto;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/client/notifications")
@RequiredArgsConstructor
public class NotificationControllerImpl implements NotificationController {

    @Override
    @GetMapping("/state")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<IsUnReadNotificationDto>> getIsUnReadNotification(CustomUserDetails userDetails) {
        return null;
    }
}
