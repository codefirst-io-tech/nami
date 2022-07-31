package io.codefirst.nami.security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codefirst.nami.exception.ErrorDto;
import io.codefirst.nami.exception.ErrorMessageType;
import io.codefirst.nami.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ApiJWTAuthorizationFilter extends BasicAuthenticationFilter {

    public ApiJWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        String token = getTokenInCookies(req.getCookies());
        if (StringUtils.isEmpty(token)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;

        try {
            authentication = getAuthentication(token);
        } catch (ExpiredJwtException exception) {
            ObjectMapper objectMapper = new ObjectMapper();
            ErrorDto dto = new ErrorDto(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(), "Oturumunuzun süresi dolmuş, lütfen tekrar giriş yapınız", new Date());
            res.resetBuffer();
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            res.getOutputStream().print(objectMapper.writeValueAsString(dto));
            res.flushBuffer();
            return;
        } catch (Exception exception) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (Objects.nonNull(token)) {
            Claims claims;
            claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            if (Objects.nonNull(username)) {
                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            }
            throw new UnauthorizedException(ErrorMessageType.UNAUTHORIZED.getMessage());
        }
        throw new UnauthorizedException(ErrorMessageType.UNAUTHORIZED.getMessage());
    }

    private String getTokenInCookies(Cookie[] cookies) {
        if (Objects.isNull(cookies)) {
            return null;
        }

        List<Cookie> cookieList = Arrays.asList(cookies);
        Optional<Cookie> optCookie = cookieList
                .stream()
                .filter(cookie -> cookie.getName().equals(SecurityConstant.TOKEN_COOKIE_NAME))
                .findFirst();

        return optCookie.map(Cookie::getValue).orElse(null);
    }
}
