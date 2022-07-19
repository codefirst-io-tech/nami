package io.codefirst.nami.email;

import io.codefirst.nami.app.ConfigLoaderUtil;
import io.codefirst.nami.exception.BadRequestException;
import io.codefirst.nami.exception.ErrorMessageType;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public record EmailService(JavaMailSender javaMailSender) {
    public void sendSimpleMail(EmailDetails details) {
        try {
            String sender = ConfigLoaderUtil.getProperty("spring.mail.username");
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.recipient());
            mailMessage.setText(details.msgBody());
            mailMessage.setSubject(details.subject());
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("Error while Sending Mail : " + e.getMessage());
            throw new BadRequestException(ErrorMessageType.ERROR_MAIL_SEND.getMessage());
        }
    }
}