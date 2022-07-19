package io.codefirst.nami.email;

import io.codefirst.nami.app.NamiConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(NamiConstant.API_PREFIX + "/mail")
@RequiredArgsConstructor
class EmailController {

    final EmailClient emailClient;

    @GetMapping("/test")
    void all(EmailDetails emailDetails) {
        emailClient.sendSimpleMail(emailDetails);
    }
}
