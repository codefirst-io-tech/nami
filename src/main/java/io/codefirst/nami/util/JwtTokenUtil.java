package io.codefirst.nami.util;

import io.codefirst.nami.constant.SecurityConstant;
import io.codefirst.nami.model.User;
import io.codefirst.nami.resource.TokenResource;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenUtil {
    public static final long JWT_TOKEN_VALIDITY = 12 * 60 * 60 * 1000L;
    public static final String TOKEN_PREFIX = "Bearer ";

    public static TokenResource generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        Date expireDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY);
        String token = TOKEN_PREFIX + doGenerateToken(claims, user.getUsername(), String.valueOf(user.getId()), expireDate);
        return new TokenResource(token, expireDate);
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