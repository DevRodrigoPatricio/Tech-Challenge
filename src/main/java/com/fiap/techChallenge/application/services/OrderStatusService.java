package com.fiap.techChallenge.application.services;

import com.fiap.techChallenge.application.ports.OrderStatusNotificationPort;
import com.fiap.techChallenge.application.ports.OrderStatusRepositoryPort;
import com.fiap.techChallenge.domain.Order;
import com.fiap.techChallenge.domain.exceptions.InvalidOrderStatusTransitionException;
import com.fiap.techChallenge.domain.exceptions.OrderNotFoundException;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class OrderStatusService {

    private final OrderStatusRepositoryPort repository;
    private final OrderStatusNotificationPort notificationPort;

    public OrderStatusService(OrderStatusRepositoryPort repository,
                              OrderStatusNotificationPort notificationPort) {
        this.repository = repository;
        this.notificationPort = notificationPort;
    }

    public Order preparo(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        try {
            order.preparo();
            Order savedOrder = repository.save(order);
            notificationPort.notifyStatusChange(savedOrder);
            return savedOrder;
        } catch (InvalidOrderStatusTransitionException e) {
            throw new IllegalStateException("Failed to start order preparation: " + e.getMessage(), e);
        }
    }

    public Order pronto(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        try {
            order.pronto();
            Order savedOrder = repository.save(order);
            notificationPort.notifyStatusChange(savedOrder);
            return savedOrder;
        } catch (InvalidOrderStatusTransitionException e) {
            throw new IllegalStateException("Failed to mark order as ready: " + e.getMessage(), e);
        }
    }

    public Order entregue(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        try {
            order.entregue();
            Order savedOrder = repository.save(order);
            notificationPort.notifyStatusChange(savedOrder);
            return savedOrder;
        } catch (InvalidOrderStatusTransitionException e) {
            throw new IllegalStateException("Failed to mark order as delivered: " + e.getMessage(), e);
        }
    }

    public Order finalizado(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        try {
            order.finalizado();
            Order savedOrder = repository.save(order);
            notificationPort.notifyStatusChange(savedOrder);
            return savedOrder;
        } catch (InvalidOrderStatusTransitionException e) {
            throw new IllegalStateException("Failed to mark order as delivered: " + e.getMessage(), e);
        }
    }

    public Order getStatus(UUID orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public Order updateStatus(UUID orderId, String status) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        try {
            switch (Order.OrderStatus.valueOf(status)) {
                case EM_PREPARACAO -> order.preparo();
                case PRONTO -> order.pronto();
                case ENTREGUE -> order.entregue();
                case FINALIZADO -> order.finalizado();
                default -> throw new IllegalArgumentException("Invalid status: " + status);
            }

            Order savedOrder = repository.save(order);
            notificationPort.notifyStatusChange(savedOrder);
            return savedOrder;
        } catch (InvalidOrderStatusTransitionException e) {
            throw new IllegalStateException("Failed to update order status: " + e.getMessage(), e);
        }
    }
}