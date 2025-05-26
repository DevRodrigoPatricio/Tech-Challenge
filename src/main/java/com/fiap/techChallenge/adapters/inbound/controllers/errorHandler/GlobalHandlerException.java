package com.fiap.techChallenge.adapters.inbound.controllers.errorHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fiap.techChallenge.domain.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.domain.exceptions.order.InvalidOrderStatusException;
import com.fiap.techChallenge.domain.exceptions.order.InvalidOrderStatusTransitionException;
import com.fiap.techChallenge.domain.exceptions.order.WrongCategoryOrderException;
import com.fiap.techChallenge.domain.exceptions.product.NameAlreadyRegisteredException;
import com.fiap.techChallenge.domain.exceptions.product.ProductNotAvaiableException;
import com.fiap.techChallenge.domain.exceptions.user.UserAlreadyExistsException;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler({
        EntityNotFoundException.class,
        IllegalArgumentException.class,
        UserAlreadyExistsException.class,
        NameAlreadyRegisteredException.class,
        InvalidOrderStatusException.class,
        NullPointerException.class,
        ProductNotAvaiableException.class,
        InvalidOrderStatusTransitionException.class,
        WrongCategoryOrderException.class,
        SQLIntegrityConstraintViolationException.class,})
    public ResponseEntity<ErrorResponse> handleExceptions(Exception ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<HashMap<String, String>> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> {
                    HashMap<String, String> error = new HashMap<>();
                    error.put("field", e.getField());
                    error.put("message", e.getDefaultMessage());
                    return error;
                })
                .toList();

        HashMap<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.put("message", "Erro(s) de validação");
        response.put("errors", erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleEnumPathVariableException(MethodArgumentTypeMismatchException e) {
        Class<?> targetType = e.getRequiredType();
        
        if (targetType != null) {
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
}
