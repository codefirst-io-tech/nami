package io.codefirst.nami.auth;

import io.codefirst.nami.app.NamiConstant;
import io.codefirst.nami.security.JwtTokenUtil;
import io.codefirst.nami.security.SecurityConstant;
import io.codefirst.nami.user.UserDto;
import io.codefirst.nami.user.UserResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(NamiConstant.API_PREFIX + "/auth")
record AuthController(AuthClient authClient) {

    @PostMapping(value = "/login")
    void login(@RequestBody @Valid UserDto dto, HttpServletResponse response) {
        TokenResource token = authClient.getToken(dto);
        Cookie cookie = JwtTokenUtil.generateCookie(SecurityConstant.TOKEN_COOKIE_NAME, token.token());
        response.addCookie(cookie);
    }

    @PostMapping("/register")
    UserResource save(@RequestBody @Valid UserDto userDto) {
        return authClient.register(userDto);
    }
}
