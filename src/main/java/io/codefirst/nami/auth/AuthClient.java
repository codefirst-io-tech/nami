package io.codefirst.nami.auth;

import io.codefirst.nami.user.UserClient;
import io.codefirst.nami.user.UserDto;
import io.codefirst.nami.user.UserResource;
import org.springframework.stereotype.Service;

@Service
public record AuthClient(UserClient userClient) {
    public TokenResource getToken(UserDto dto) {
        return userClient.authenticate(dto);
    }

    public UserResource register(UserDto userDto) {
        return userClient.register(userDto);
    }
}
