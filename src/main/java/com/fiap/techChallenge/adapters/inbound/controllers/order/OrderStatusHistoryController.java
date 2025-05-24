package com.fiap.techChallenge.adapters.inbound.controllers.order;

import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.application.dto.order.OrderStatusHistoryRequestDTO;
import com.fiap.techChallenge.application.services.order.OrderStatusHistoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order-status")
@Tag(name = "Histórico dos Status de Pedidos", description = "APIs referentes ao Histórico de Status de Pedidos")
public class OrderStatusHistoryController {

    private final OrderStatusHistoryServiceImpl service;

    public OrderStatusHistoryController(OrderStatusHistoryServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/save")
    @Operation(summary = "Save",
            description = "Cria um novo Status Para o Pedido informado")
    public ResponseEntity<OrderStatusHistory> save(@RequestBody @Valid OrderStatusHistoryRequestDTO request) {
        return ResponseEntity.ok(service.save(request));
    }

    @GetMapping("/find-by-id/{id}")
    @Operation(summary = "Find By ID",
            description = "Encontra um Status History pelo ID")
    public ResponseEntity<OrderStatusHistory> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-last-status/{orderId}")
    @Operation(summary = "Find Last Status",
            description = "Encontra o ultimo Status  do pedido informado")
    public ResponseEntity<OrderStatusHistory> findLast(@PathVariable UUID orderId) {
        return ResponseEntity.ok(service.findLast(orderId));
    }

    @GetMapping("/list/{orderId}")
    @Operation(summary = "List",
            description = "Encontra a lista dos status de um pedido")
    public ResponseEntity<List<OrderStatusHistory>> listByPeriod(@PathVariable UUID orderId) {
        return ResponseEntity.ok(service.list(orderId));
    }

}
