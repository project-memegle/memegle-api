package com.krince.memegle.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


public interface ResponseCode {
    Boolean getIsSuccess();
    String getHttpStatus();
    Integer getCode();
    String getMessage();
}