package com.fiap.techChallenge.domain.order.status;

import com.fiap.techChallenge.domain.order.Order;

public interface OrderStatusNotificationPort {
    void notifyStatusChange(Order order);
}