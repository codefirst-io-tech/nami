package io.codefirst.nami.service;

import io.codefirst.nami.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        if (Objects.nonNull(user)) {
            return buildUserForAuthentication(user);
        } else {
            throw new UsernameNotFoundException("user with username " + username + " does not exist.");
        }
    }

    private UserDetails buildUserForAuthentication(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), null);
    }
}

