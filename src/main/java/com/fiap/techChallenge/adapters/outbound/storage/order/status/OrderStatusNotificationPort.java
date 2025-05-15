package com.fiap.techChallenge.adapters.outbound.storage.order.status;

import com.fiap.techChallenge.domain.order.Order;

public interface OrderStatusNotificationPort {
    void notifyStatusChange(Order order);
}