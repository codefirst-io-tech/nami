package io.codefirst.nami.exception;

import org.springframework.http.HttpStatus;

public final class UsernameNotFoundException extends AbstractException {
    public UsernameNotFoundException(String message) {
        super(message);
    }

    @Override
    HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
