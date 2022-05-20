package io.codefirst.nami.resource;

import java.util.Date;

public record TokenResource(String token, Date expireDate) {
}