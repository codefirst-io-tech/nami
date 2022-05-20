package io.codefirst.nami.dto;

import java.util.Date;

public record ErrorDto(int resultCode, String result, String errorMessage, Date time) {
}
