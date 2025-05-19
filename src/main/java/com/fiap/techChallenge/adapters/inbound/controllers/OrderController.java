package com.fiap.techChallenge.adapters.inbound.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.techChallenge.application.services.OrderService;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderRequest;
import com.fiap.techChallenge.utils.exceptions.WrongCategoryOrderException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order", description = "APIs relacionadas aos Pedidos")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @Operation(summary = "Save",
            description = "Salva um Pedido")
    public ResponseEntity<Order> save(@RequestBody OrderRequest order) {
        return ResponseEntity.ok(service.save(order));
    }

    @PostMapping("/add-item/{orderId}/{productId}/{quantity}")
    @Operation(summary = "Add Item",
            description = "Adiciona um Produto ao Pedido")
    public ResponseEntity<Order> addItem(@PathVariable UUID orderId,@PathVariable UUID productId,@PathVariable int quantity) {
        return ResponseEntity.ok(service.addItem(orderId, productId, quantity));
    }

    @PostMapping("/remove-item/{orderId}/{productId}/{quantity}")
    @Operation(summary = "Remove Item",
            description = "Remove um Produto do Pedido")
    public ResponseEntity<Order> removeItem(@PathVariable UUID orderId, @PathVariable UUID productId, @PathVariable int quantity) {
        return ResponseEntity.ok(service.removeItem(orderId, productId, quantity));
    }

    @GetMapping("/find-by-id/{id}")
    @Operation(summary = "Find By ID",
            description = "Encontra um pedido pelo ID")
    public ResponseEntity<Optional<Order>> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/list-by-client/{consumerId}")
    @Operation(summary = "List By Client",
            description = "Encontra um pedido pelo ID do cliente")
    public ResponseEntity<List<Order>> listByClient(@PathVariable UUID consumerId) {
        return ResponseEntity.ok(service.listByClient(consumerId));
    }

    @GetMapping("/list-by-period/{initialDt}/{finalDt}")
    @Operation(summary = "List By Period",
            description = "Encontra um pedido pelo periodo informado")
    public ResponseEntity<List<Order>> listByPeriod(@PathVariable LocalDateTime initialDt, @PathVariable LocalDateTime finalDt) {
        return ResponseEntity.ok(service.listByPeriod(initialDt, finalDt));
    }

    @PostMapping("/cancel-order/{id}")
    @Operation(summary = "Cancel Order",
            description = "Cancela um Pedido")
    public ResponseEntity<Optional<Order>> cancelOrder(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({WrongCategoryOrderException.class})
    public ResponseEntity<String> handleWrongCategoryOrderException(WrongCategoryOrderException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
