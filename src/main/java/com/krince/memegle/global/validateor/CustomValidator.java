package com.krince.memegle.global.validateor;

public class CustomValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static void validateEmailFormat(String email) {
        boolean isEmailFormat = email.matches(EMAIL_REGEX);
        if (!isEmailFormat) {
            throw new IllegalArgumentException("이메일 형식을 확인해주세요.");
        }
    }
}
