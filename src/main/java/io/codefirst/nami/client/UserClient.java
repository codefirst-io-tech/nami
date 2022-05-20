package io.codefirst.nami.client;

import io.codefirst.nami.mapper.UserMapper;
import io.codefirst.nami.model.User;
import io.codefirst.nami.resource.UserResource;
import io.codefirst.nami.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record UserClient(UserMapper userMapper, UserService userService) {
    public List<UserResource> findAll() {
        List<User> userList = userService.findAll();
        return userMapper.userToUserResource(userList);
    }
}
