package io.codefirst.nami.controller;

import io.codefirst.nami.client.AuthClient;
import io.codefirst.nami.constant.NamiConstant;
import io.codefirst.nami.dto.UserDto;
import io.codefirst.nami.resource.TokenResource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(NamiConstant.API_PREFIX + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthClient authClient;

    @PostMapping(value = "/login")
    public TokenResource login(@RequestBody @Valid UserDto dto) {
        return authClient.getToken(dto);
    }

    @PostMapping("/register")
    public void save(@RequestBody @Valid UserDto userDto) {
        authClient.register(userDto);
    }
}
