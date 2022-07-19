package io.codefirst.nami.email;

import org.springframework.stereotype.Service;

@Service
public record EmailClient(EmailService emailService) {

    public void sendSimpleMail(EmailDetails details) {
        emailService.sendSimpleMail(details);
    }
}
