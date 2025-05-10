package com.fiap.techChallenge.adapters.inbound.controllers;

import com.fiap.techChallenge.application.services.OrderStatusService;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.utils.exceptions.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Status de Pedidos", description = "Endpoints para gerenciamento do status de pedidos")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @PatchMapping("/{orderId}/preparar")
    @Operation(summary = "Iniciar preparação do pedido",
            description = "Muda o status para EM_PREPARACAO (requer status RECEBIDO)")
    public ResponseEntity<Order> prepareOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderStatusService.preparation(orderId));
    }

    @PatchMapping("/{orderId}/pronto")
    @Operation(summary = "Marcar pedido como pronto",
            description = "Muda o status para PRONTO (requer status EM_PREPARACAO)")
    public ResponseEntity<Order> markAsReady(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderStatusService.ready(orderId));
    }

    @PatchMapping("/{orderId}/entregue")
    @Operation(summary = "Marcar pedido como entregue",
            description = "Muda o status para ENTREGUE (requer status PRONTO)")
    public ResponseEntity<Order> markAsDelivered(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderStatusService.delivered(orderId));
    }

    @PatchMapping("/{orderId}/finalizar")
    @Operation(summary = "Finalizar pedido",
            description = "Muda status para FINALIZADO (requer status ENTREGUE)")
    public ResponseEntity<Order> finalizeOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderStatusService.finished(orderId));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Consultar status do pedido",
            description = "Retorna o status atual do pedido")
    public ResponseEntity<Order> checkStatus(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderStatusService.getStatus(orderId));
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<String> handleNotFound(Exception e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler({InvalidOrderStatusTransitionException.class})
    public ResponseEntity<String> handleBadRequest(Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}