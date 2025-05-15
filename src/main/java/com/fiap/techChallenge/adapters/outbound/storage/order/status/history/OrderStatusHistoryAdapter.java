package com.fiap.techChallenge.adapters.outbound.storage.order.status.history;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRepository;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRequest;

@Component
public class OrderStatusHistoryAdapter implements OrderStatusHistoryPort {

    private final OrderStatusHistoryRepository repository;

    public OrderStatusHistoryAdapter(OrderStatusHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<OrderStatusHistory> findById(UUID orderStatusHistoryId) {
        return repository.findById(orderStatusHistoryId);
    }

    @Override
    public OrderStatusHistory save(OrderStatusHistoryRequest request) {
        this.validateIfStatusAlreadyExists(request.getOrderId(), request.getStatus());

        OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
        orderStatusHistory.setId(null);
        orderStatusHistory.setOrderId(request.getOrderId());
        orderStatusHistory.setStatus(request.getStatus());
        orderStatusHistory.setDate(LocalDateTime.now());
        return repository.save(orderStatusHistory);
    }

    @Override
    public List<OrderStatusHistory> list(UUID orderId) {
        return repository.list(orderId);
    }

    @Override
    public Optional<OrderStatusHistory> findLast(UUID orderId) {
        return repository.findLast(orderId);
    }

    public boolean validateIfStatusAlreadyExists(UUID orderId, OrderStatus status) {

        if (repository.existsByOrderIdAndStatus(orderId, status)) {
            throw new IllegalArgumentException("Status já registrado para esse Pedido");

        } else {
            return false;
        }
    }
}
