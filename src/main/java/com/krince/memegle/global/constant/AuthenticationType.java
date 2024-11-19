package com.krince.memegle.global.constant;

import lombok.Getter;

@Getter
public enum AuthenticationType {

    ID("id"),
    PASSWORD("password"),
    SIGN_UP("signUp");

    private final String stringValue;

    AuthenticationType(String stringValue) {
        this.stringValue = stringValue;
    }
}
