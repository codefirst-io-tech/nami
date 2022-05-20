package io.codefirst.nami.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class BadRequestException extends AbstractException {
    public BadRequestException(String message) {
        super(message);
    }

    @Override
    HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
