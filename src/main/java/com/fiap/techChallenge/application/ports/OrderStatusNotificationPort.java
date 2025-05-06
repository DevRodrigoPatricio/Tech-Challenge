package com.fiap.techChallenge.application.ports;

import com.fiap.techChallenge.domain.Order;

public interface OrderStatusNotificationPort {
    void notifyStatusChange(Order order);
}