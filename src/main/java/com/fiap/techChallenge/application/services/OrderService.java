package com.fiap.techChallenge.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.techChallenge.adapters.outbound.storage.order.OrderPort;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderRequest;

@Service
public class OrderService {

    private final OrderPort port;

    public OrderService(OrderPort port) {
        this.port = port;
    }

    public Order save(OrderRequest order) {
        return port.save(order);
    }

    public Order addItem(UUID id, UUID productId, int quantity) {
        return port.addItem(id, productId, quantity);
    }

    public Order removeItem(UUID id, UUID productId, int quantity) {
        return port.removeItem(id, productId, quantity);
    }

    public Optional<Order> findById(UUID id) {
        return port.findById(id);
    }

    public List<Order> listByClient(UUID customerId) {
        return port.listByClient(customerId);
    }

    public List<Order> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt) {
        return port.listByPeriod(initialDt, finalDt);
    }

    public void delete(UUID id) {
        port.delete(id);
    }
}
