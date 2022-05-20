package io.codefirst.nami.controller;

import io.codefirst.nami.client.UserClient;
import io.codefirst.nami.constant.NamiConstant;
import io.codefirst.nami.resource.UserResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(NamiConstant.API_PREFIX + "/user")
public record UserController(UserClient userClient) {
    @GetMapping("/all")
    public List<UserResource> all() {
        return userClient.findAll();
    }
}
