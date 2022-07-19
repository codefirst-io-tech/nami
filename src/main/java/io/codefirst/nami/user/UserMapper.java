package io.codefirst.nami.user;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
interface UserMapper {
    User ToUser(UserDto userDto);

    List<User> ToUser(List<UserDto> userDto);

    UserResource ToUserResource(User user);

    List<UserResource> ToUserResource(List<User> user);
}
