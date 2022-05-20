package io.codefirst.nami.service;

import io.codefirst.nami.exception.BadRequestException;
import io.codefirst.nami.exception.UsernameNotFoundException;
import io.codefirst.nami.model.User;
import io.codefirst.nami.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void whenUsernameNotFound_thenExceptionIsCorrect() {

        User user = new User();
        user.setUsername("abidino");
        user.setPassword("test");

        UserService userService = new UserService(userRepository, passwordEncoder);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.authenticate(user));
    }

    @Test
    void whenUsernameAndPasswordNotMatch_thenExceptionIsCorrect() {
        User user = new User();
        user.setUsername("abidino");
        user.setPassword("test");

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(new User(null, "abidino", "test")));
        Mockito.when(passwordEncoder.matches(any(), any())).thenReturn(false);
        UserService userService = new UserService(userRepository, passwordEncoder);
        Assertions.assertThrows(BadRequestException.class, () -> userService.authenticate(user));
    }

    @Test
    void whenAuthenticate_thenReturnTokenResource() {
        User user = new User();
        user.setUsername("abidino");
        user.setPassword("test");

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(new User(5L, "abidino", "test")));
        Mockito.when(passwordEncoder.matches(any(), any())).thenReturn(true);
        UserService userService = new UserService(userRepository, passwordEncoder);
        Assertions.assertNotNull(userService.authenticate(user).token());
        Assertions.assertNotNull(userService.authenticate(user).expireDate());
    }
}
