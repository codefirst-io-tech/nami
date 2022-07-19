package io.codefirst.nami.user;

import io.codefirst.nami.auth.TokenResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record UserClient(UserMapper userMapper, UserService userService) {
    public List<UserResource> findAll() {
        List<User> userList = userService.findAll();
        return userMapper.ToUserResource(userList);
    }

    public TokenResource authenticate(UserDto dto) {
        User user = userMapper.ToUser(dto);
        return userService.authenticate(user);
    }

    public UserResource register(UserDto userDto) {
        User user = userMapper.ToUser(userDto);
        User savedUser = userService.save(user);
        return userMapper.ToUserResource(savedUser);
    }
}
