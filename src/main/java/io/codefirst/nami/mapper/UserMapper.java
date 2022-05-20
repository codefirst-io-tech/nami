package io.codefirst.nami.mapper;

import io.codefirst.nami.dto.UserDto;
import io.codefirst.nami.model.User;
import io.codefirst.nami.resource.UserResource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDto);

    List<User> userDtoToUser(List<UserDto> userDto);

    UserResource userToUserResource(User user);

    List<UserResource> userToUserResource(List<User> user);
}
