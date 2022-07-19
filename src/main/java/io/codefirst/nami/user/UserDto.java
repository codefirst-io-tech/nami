package io.codefirst.nami.user;

import javax.validation.constraints.NotNull;

public record UserDto(@NotNull(message = "Username cannot be null") String username,
                      @NotNull(message = "Password cannot be null") String password) {
}
