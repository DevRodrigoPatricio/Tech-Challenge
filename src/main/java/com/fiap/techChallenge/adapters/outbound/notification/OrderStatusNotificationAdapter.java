package com.fiap.techChallenge.adapters.outbound.notification;

import com.fiap.techChallenge.application.ports.OrderStatusNotificationPort;
import com.fiap.techChallenge.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusNotificationAdapter implements OrderStatusNotificationPort {

    @Override
    public void notifyStatusChange(Order order) {
        System.out.println("Notificação: Pedido " + order.getId() +
                " agora está " + order.getStatus());
    }
}