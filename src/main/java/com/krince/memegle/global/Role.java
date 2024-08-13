package com.krince.memegle.global;

import lombok.Getter;

public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_GUEST("ROLE_GUEST"),
    ROLE_ADMIN("ROLE_ADMIN");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }
}
