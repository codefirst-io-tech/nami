package io.codefirst.nami.security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codefirst.nami.exception.ErrorDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ApiJWTAuthorizationFilter extends BasicAuthenticationFilter {

    public ApiJWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        String authorizationHeader = req.getHeader(SecurityConstant.HEADER_AUTHORIZATION);

        if (authorizationHeader == null || !(authorizationHeader.startsWith(SecurityConstant.TOKEN_PREFIX) || authorizationHeader.startsWith(SecurityConstant.BASIC_AUTH_PREFIX))) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;

        try {
            authentication = getAuthentication(req);
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

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstant.HEADER_AUTHORIZATION);
        if (Objects.nonNull(token)) {

            Claims claims;
            claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.SECRET)
                    .parseClaimsJws(token.replace(SecurityConstant.TOKEN_PREFIX, ""))
                    .getBody();

            String username = claims.getSubject();

            if (Objects.nonNull(username)) {
                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
