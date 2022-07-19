package io.codefirst.nami.user;

import io.codefirst.nami.app.NamiConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(NamiConstant.API_PREFIX + "/user")
record UserController(UserClient userClient) {
    @GetMapping("/all")
    List<UserResource> all() {
        return userClient.findAll();
    }
}
