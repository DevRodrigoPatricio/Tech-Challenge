package com.fiap.techChallenge.adapters.inbound.controllers;

import java.util.Arrays;
import java.util.UUID;

import org.hibernate.PropertyValueException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fiap.techChallenge.utils.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.utils.exceptions.InvalidOrderStatusException;
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

    @SuppressWarnings("null")
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleEnumPathVariableException(MethodArgumentTypeMismatchException e) {
        if (e.getRequiredType() != null) {
            Class<?> targetType = e.getRequiredType();
            String field = e.getName();
            String invalidValue = String.valueOf(e.getValue());

            if (targetType.isEnum()) {
                Object[] constants = targetType.getEnumConstants();
                String correctValues = Arrays.toString(constants);
                String message = String.format(
                        "Valor inválido '%s' para o campo '%s'. Valores aceitos: %s",
                        invalidValue,
                        field,
                        correctValues
                );
                return ResponseEntity.badRequest().body(message);

            } else if (UUID.class.equals(targetType)) {
                String message = String.format(
                        "Valor inválido '%s' para o campo '%s'. Deve ser um UUID válido.",
                        invalidValue,
                        field
                );
                return ResponseEntity.badRequest().body(message);
            }
        }

        return ResponseEntity.badRequest().body("Parâmetro inválido.");
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<String> handlePropertyValueException(PropertyValueException e) {
        return ResponseEntity.badRequest()
                .body(String.format("O campo '%s' é obrigatório.", e.getPropertyName()));
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

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({InvalidOrderStatusException.class})
    public ResponseEntity<String> handleInvalidOrderStatusException(InvalidOrderStatusException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
