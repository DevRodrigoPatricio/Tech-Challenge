package com.fiap.techChallenge.adapters.outbound.notification;

import com.fiap.techChallenge.adapters.outbound.storage.order.status.OrderStatusNotificationAdapter;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.Order.OrderStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class OrderStatusNotificationAdapterTest {

    @InjectMocks
    private OrderStatusNotificationAdapter notificationAdapter;

    // Precisa implementar como vai ser enviado a notificação
    @Test
    void shouldNotifyStatusChange() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, OrderStatus.READY);

        notificationAdapter.notifyStatusChange(order);
    }
}