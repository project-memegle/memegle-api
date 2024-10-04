package com.krince.memegle.util;

import java.util.List;

public class PermitAllUrlsUtil {

    public static List<String> permitAllUrls = List.of(
            "/apis/client/users/sign/**",
            "/apis/client/images/**",
            "/apis/client/auth/email/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    public static List<String> permitAllGetUrls = List.of(
            "/apis/client/categories"
    );

    public static String[] getPermitAllUrls() {
        return permitAllUrls.toArray(new String[0]);
    }

    public static String[] getPermitAllGetUrls() {
        return permitAllGetUrls.toArray(new String[0]);}
}
