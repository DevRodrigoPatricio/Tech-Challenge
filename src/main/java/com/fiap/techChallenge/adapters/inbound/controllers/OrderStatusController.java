package com.fiap.techChallenge.adapters.inbound.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fiap.techChallenge.utils.exceptions.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fiap.techChallenge.application.services.OrderStatusHistoryService;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRequest;

@RestController
@RequestMapping("/api/order-status")
@Tag(name = "Historico dos Status de Pedidos", description = "APIs referentes ao Histórico de Status de Pedidos")
public class OrderStatusController {

    private final OrderStatusHistoryService service;

    public OrderStatusController(OrderStatusHistoryService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @Operation(summary = "Cria um novo Status Para o Pedido informado",
            description = "Muda o status para EM_PREPARACAO (requer status RECEBIDO)")
    public ResponseEntity<OrderStatusHistory> save(@RequestBody OrderStatusHistoryRequest request) {
        return ResponseEntity.ok(service.save(request));
    }

    @GetMapping("/find-by-id/{id}")
    @Operation(summary = "Find By ID",
            description = "Encontra um Status History pelo ID")
    public ResponseEntity<Optional<OrderStatusHistory>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-last-status/{orderId}")
    @Operation(summary = "Find Last Status",
            description = "Encontra o ultimo Status  do pedido informado")
    public ResponseEntity<Optional<OrderStatusHistory>> findLast(@PathVariable UUID orderId) {
        return ResponseEntity.ok(service.findLast(orderId));
    }

    @GetMapping("/list/{orderId}")
    @Operation(summary = "List",
            description = "Encontra a lista dos status de um pedido")
    public ResponseEntity<List<OrderStatusHistory>> listByPeriod(@PathVariable UUID orderId) {
        return ResponseEntity.ok(service.list(orderId));
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<String> handleNotFound(Exception e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler({InvalidOrderStatusTransitionException.class})
    public ResponseEntity<String> handleBadRequest(Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

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
}
