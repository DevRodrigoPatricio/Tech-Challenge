package com.fiap.techChallenge.adapters.inbound.controllers.errorHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
    }
}
