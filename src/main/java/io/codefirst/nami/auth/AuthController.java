package io.codefirst.nami.auth;

import io.codefirst.nami.app.NamiConstant;
import io.codefirst.nami.user.UserDto;
import io.codefirst.nami.user.UserResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(NamiConstant.API_PREFIX + "/auth")
record AuthController(AuthClient authClient) {
    @PostMapping(value = "/login")
    TokenResource login(@RequestBody @Valid UserDto dto) {
        return authClient.getToken(dto);
    }

    @PostMapping("/register")
    UserResource save(@RequestBody @Valid UserDto userDto) {
        return authClient.register(userDto);
    }
}
