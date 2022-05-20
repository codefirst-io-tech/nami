package io.codefirst.nami.service;

import io.codefirst.nami.exception.BadRequestException;
import io.codefirst.nami.exception.UsernameNotFoundException;
import io.codefirst.nami.model.User;
import io.codefirst.nami.repository.UserRepository;
import io.codefirst.nami.resource.TokenResource;
import io.codefirst.nami.type.ErrorMessageType;
import io.codefirst.nami.util.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        Optional<User> optionalUser = findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            throw new BadRequestException(ErrorMessageType.USERNAME_ALREADY_EXIST.getMessage());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public TokenResource authenticate(User authenticateUser) {
        User user = findByUsername(authenticateUser.getUsername()).orElseThrow(() -> new UsernameNotFoundException(ErrorMessageType.USERNAME_NOT_FOUND.getMessage()));
        if (passwordEncoder.matches(authenticateUser.getPassword(), user.getPassword())) {
            return JwtTokenUtil.generateToken(user);
        } else {
            throw new BadRequestException(ErrorMessageType.USERNAME_AND_PASSWORD_NOT_MATCH.getMessage());
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
