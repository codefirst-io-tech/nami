package io.codefirst.nami.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstant {
    public static final String SECRET = "!44VeRy$3cUr3S3creT";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String BASIC_AUTH_PREFIX = "Basic ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
}
