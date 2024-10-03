package com.krince.memegle.domain.notification.dto;

import lombok.*;

import static lombok.AccessLevel.*;

@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class IsUnReadNotificationDto {

    private boolean isUnReadNotification;

    public boolean getIsUnReadNotification() {
        return isUnReadNotification;
    }
}
