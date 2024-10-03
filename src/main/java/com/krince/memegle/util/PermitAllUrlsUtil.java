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

    public static String[] getPermitAllUrls() {
        return permitAllUrls.toArray(new String[0]);
    }
}
