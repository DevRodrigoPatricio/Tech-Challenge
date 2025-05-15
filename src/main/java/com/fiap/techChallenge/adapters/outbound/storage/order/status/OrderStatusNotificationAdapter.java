package com.fiap.techChallenge.adapters.outbound.storage.order.status;

import org.springframework.stereotype.Component;

import com.fiap.techChallenge.domain.order.Order;

@Component
public class OrderStatusNotificationAdapter implements OrderStatusNotificationPort {

    @Override
    public void notifyStatusChange(Order order) {
        System.out.println("Notificação: Pedido " + order.getId() +
                " agora está " + "order.getStatus()");
    }
}