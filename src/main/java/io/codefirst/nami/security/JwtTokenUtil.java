package io.codefirst.nami.security;

import io.codefirst.nami.auth.TokenResource;
import io.codefirst.nami.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenUtil {
    public static final long JWT_TOKEN_VALIDITY = 12 * 60 * 60 * 1000L;

    public static TokenResource generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        Date expireDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY);
        String token = doGenerateToken(claims, user.getUsername(), String.valueOf(user.getId()), expireDate);
        return new TokenResource(token, expireDate);
    }

    public static Cookie generateCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    private static String doGenerateToken(
            Map<String, Object> claims, String subject, String id, Date expireDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.SECRET)
                .compact();
    }

}