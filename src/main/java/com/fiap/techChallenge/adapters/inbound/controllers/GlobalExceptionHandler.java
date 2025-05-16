package com.fiap.techChallenge.adapters.inbound.controllers;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fiap.techChallenge.utils.exceptions.InvalidOrderStatusTransitionException;
import com.fiap.techChallenge.utils.exceptions.NameAlreadyRegisteredException;
import com.fiap.techChallenge.utils.exceptions.WrongCategoryOrderException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleInvalidEnumValueException(HttpMessageNotReadableException e) {
        String message = e.getMessage();

        if (e.getCause() instanceof InvalidFormatException formatEx && formatEx.getTargetType().isEnum()) {
            Class<?> enumClass = formatEx.getTargetType();
            Object[] constants = enumClass.getEnumConstants();
            String correctValues = Arrays.toString(constants);
            String field = formatEx.getPath().get(0).getFieldName();

            message = String.format(
                    "Valor inválido para o campo '%s'. Valores aceitos: %s",
                    field,
                    correctValues
            );
        }

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(WrongCategoryOrderException.class)
    public ResponseEntity<String> handleWrongCategoryOrderException(
            WrongCategoryOrderException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({InvalidOrderStatusTransitionException.class})
    public ResponseEntity<String> handleBadRequest(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({NameAlreadyRegisteredException.class})
    public ResponseEntity<String> handleNameAlreadyRegisteredException(NameAlreadyRegisteredException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
