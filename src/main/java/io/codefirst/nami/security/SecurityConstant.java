package io.codefirst.nami.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstant {
    public static final String SECRET = "!44VeRy$3cUr3S3creT";
    public static final String TOKEN_COOKIE_NAME = "token";
}
