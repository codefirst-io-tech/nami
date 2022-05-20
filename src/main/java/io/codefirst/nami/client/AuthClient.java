package io.codefirst.nami.client;

import io.codefirst.nami.dto.UserDto;
import io.codefirst.nami.mapper.UserMapper;
import io.codefirst.nami.model.User;
import io.codefirst.nami.resource.TokenResource;
import io.codefirst.nami.service.UserService;
import org.springframework.stereotype.Service;

@Service
public record AuthClient(UserService userService, UserMapper userMapper) {
    public TokenResource getToken(UserDto dto) {
        User authenticateUser = userMapper.userDtoToUser(dto);
        return userService.authenticate(authenticateUser);
    }

    public void register(UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        userService.save(user);
    }
}
