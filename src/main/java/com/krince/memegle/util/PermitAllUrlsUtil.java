package com.krince.memegle.util;

import java.util.List;

public class PermitAllUrlsUtil {

    public static List<String> permitAllUrls = List.of(
            "/apis/client/auth/email/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/favicon.ico"
    );

    public static List<String> permitAllGetUrls = List.of(
            "/apis/client/categories",
            "/apis/client/images/{imageId}",
            "/apis/client/images/category",
            "/apis/client/users/login-id"
    );

    public static List<String> permitAllPostUrls = List.of(
            "/apis/client/users/sign/up",
            "/apis/client/users/sign/in",
            "/apis/client/users/login-id"
    );

    public static List<String> permitAllPutUrls = List.of(
            "/apis/client/users/password"
    );

    public static List<String> permitAllDeleteUrls = List.of();

    public static String[] getPermitAllUrls() {
        return permitAllUrls.toArray(new String[0]);
    }

    public static String[] getPermitAllGetUrls() {
        return permitAllGetUrls.toArray(new String[0]);
    }

    public static String[] getPermitAllPostUrls() {
        return permitAllPostUrls.toArray(new String[0]);
    }

    public static String[] getPermitAllPutUrls() {
        return permitAllPutUrls.toArray(new String[0]);
    }

    public static String[] getPermitAllDeleteUrls() {
        return permitAllDeleteUrls.toArray(new String[0]);
    }
}
