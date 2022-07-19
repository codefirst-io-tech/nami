package io.codefirst.nami.auth;

import java.util.Date;

public record TokenResource(String token, Date expireDate) {
}