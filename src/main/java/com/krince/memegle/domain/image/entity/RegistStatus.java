package com.krince.memegle.domain.image.entity;

public enum RegistStatus {
    REGIST("REGIST"),
    WAITING("WAITING");

    String registStatus;

    RegistStatus(String registStatus) {
        this.registStatus = registStatus;
    }

    public String getValue() {
        return registStatus;
    }
}
