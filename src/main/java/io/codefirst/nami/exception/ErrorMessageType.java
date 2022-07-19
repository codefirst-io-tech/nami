package io.codefirst.nami.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessageType {
    USERNAME_NOT_FOUND("Username bulunamadı."),
    USERNAME_ALREADY_EXIST("Kullanıcı adı daha önceden alınmış."),
    USERNAME_AND_PASSWORD_NOT_MATCH("Kullanıcı adı veya parola doğru değil"),

    ERROR_MAIL_SEND("Mail gönderilirken hata meydana geldi.");

    private final String message;
}
