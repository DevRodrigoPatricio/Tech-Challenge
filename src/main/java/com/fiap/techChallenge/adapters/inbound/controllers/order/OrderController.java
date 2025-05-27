package com.fiap.techChallenge.adapters.inbound.controllers.order;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.application.dto.order.OrderDTO;
import com.fiap.techChallenge.application.dto.order.OrderWithItemsAndStatusDTO;
import com.fiap.techChallenge.application.dto.order.projection.OrderWithStatusAndWaitMinutesProjection;
import com.fiap.techChallenge.application.dto.order.projection.OrderWithStatusProjection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.techChallenge.application.dto.order.UpdateOrderStatusHistoryDTO;
import com.fiap.techChallenge.application.useCases.order.OrderUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order", description = "APIs relacionadas aos Pedidos")
public class OrderController {

    private final OrderUseCase orderUseCase;

    public OrderController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @PostMapping("/save")
    @Operation(summary = "Save",
            description = "Salva um Pedido")
    public ResponseEntity<Order> save(@RequestBody @Valid OrderDTO order) {
        return ResponseEntity.ok(orderUseCase.save(order));
    }

    @PostMapping("/update-status")
    @Operation(summary = "Update Status",
            description = "Atualiza o status do Pedido")
    public ResponseEntity<Order> updateStatus(@RequestBody @Valid UpdateOrderStatusHistoryDTO status) {
        return ResponseEntity.ok(orderUseCase.updateStatus(status));
    }

    @PostMapping("/add-item/{orderId}/{productId}/{quantity}")
    @Operation(summary = "Add Item",
            description = "Adiciona um Produto ao Pedido")
    public ResponseEntity<Order> addItem(@PathVariable UUID orderId,
            @PathVariable UUID productId,
            @PathVariable @Min(value = 1, message = "A quantidade deve ser maior que zero") int quantity) {
        return ResponseEntity.ok(orderUseCase.addItem(orderId, productId, quantity));
    }

    @PostMapping("/remove-item/{orderId}/{productId}/{quantity}")
    @Operation(summary = "Remove Item",
            description = "Remove um Produto do Pedido")
    public ResponseEntity<Order> removeItem(@PathVariable UUID orderId, @PathVariable UUID productId, @PathVariable int quantity) {
        return ResponseEntity.ok(orderUseCase.removeItem(orderId, productId, quantity));
    }

    @GetMapping("/list-status")
    @Operation(summary = "List Status",
            description = "Lista as opções de status")
    public ResponseEntity<List<OrderStatus>> listStatus() {
        return ResponseEntity.ok(Arrays.asList(OrderStatus.values()));
    }

    @GetMapping("/find-by-id/{id}")
    @Operation(summary = "Find By ID",
            description = "Encontra um pedido pelo ID")
    public ResponseEntity<OrderWithItemsAndStatusDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderUseCase.findById(id));
    }

    @GetMapping("/list-by-period/{initialDt}/{finalDt}")
    @Operation(summary = "List By Period",
            description = "Encontra um pedido pelo periodo informado")
    public ResponseEntity<List<OrderWithStatusProjection>> listByPeriod(@PathVariable LocalDateTime initialDt, @PathVariable LocalDateTime finalDt) {
        return ResponseEntity.ok(orderUseCase.listByPeriod(initialDt, finalDt));
    }

    @GetMapping("/list-today-orders")
    @Operation(summary = "List Today Order",
            description = "Lista os pedidos em Andamento")
    public ResponseEntity<List<OrderWithStatusAndWaitMinutesProjection>> listTodayOrders() {
        return ResponseEntity.ok(orderUseCase.listTodayOrders());
    }

}
