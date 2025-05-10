package com.fiap.techChallenge.application.services;

import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.status.OrderStatusNotificationPort;
import com.fiap.techChallenge.domain.order.status.OrderStatusRepository;
import com.fiap.techChallenge.utils.exceptions.InvalidOrderStatusTransitionException;
import com.fiap.techChallenge.utils.exceptions.OrderNotFoundException;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class OrderStatusService {

    private final OrderStatusRepository repository;
    private final OrderStatusNotificationPort notificationPort;

    public OrderStatusService(OrderStatusRepository repository,
                              OrderStatusNotificationPort notificationPort) {
        this.repository = repository;
        this.notificationPort = notificationPort;
    }

    public Order preparation(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        try {
            order.preparation();
            Order savedOrder = repository.save(order);
            notificationPort.notifyStatusChange(savedOrder);
            return savedOrder;
        } catch (InvalidOrderStatusTransitionException e) {
            throw new IllegalStateException("Failed to start order preparation: " + e.getMessage(), e);
        }
    }

    public Order ready(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        try {
            order.ready();
            Order savedOrder = repository.save(order);
            notificationPort.notifyStatusChange(savedOrder);
            return savedOrder;
        } catch (InvalidOrderStatusTransitionException e) {
            throw new IllegalStateException("Failed to mark order as ready: " + e.getMessage(), e);
        }
    }

    public Order delivered(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        try {
            order.delivered();
            Order savedOrder = repository.save(order);
            notificationPort.notifyStatusChange(savedOrder);
            return savedOrder;
        } catch (InvalidOrderStatusTransitionException e) {
            throw new IllegalStateException("Failed to mark order as delivered: " + e.getMessage(), e);
        }
    }

    public Order finished(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        try {
            order.finished();
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
                case IN_PREPARATION -> order.preparation();
                case READY -> order.ready();
                case DELIVERED -> order.delivered();
                case FINISHED -> order.finished();
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