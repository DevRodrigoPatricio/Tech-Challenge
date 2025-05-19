package com.fiap.techChallenge.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import java.util.UUID;

import com.fiap.techChallenge.adapters.outbound.storage.order.status.history.OrderStatusHistoryPort;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRequest;
import com.fiap.techChallenge.domain.order.status.OrderStatusWithClientAndWaitTimeDTO;

@Service
public class OrderStatusHistoryService {

    private final OrderStatusHistoryPort port;

    public OrderStatusHistoryService(OrderStatusHistoryPort port) {
        this.port = port;
    }

    public OrderStatusHistory save(OrderStatusHistoryRequest request) {
        return port.save(request);
    }

    public Optional<OrderStatusHistory> findById(UUID id) {
        return port.findById(id);
    }

    public List<OrderStatusHistory> list(UUID orderId) {
        return port.list(orderId);
    }

    public List<OrderStatusWithClientAndWaitTimeDTO> listTodayOrderStatus() {
        return port.listTodayOrderStatus();
    }

    public Optional<OrderStatusHistory> findLast(UUID orderId) {
        return port.findLast(orderId);
    }
}
