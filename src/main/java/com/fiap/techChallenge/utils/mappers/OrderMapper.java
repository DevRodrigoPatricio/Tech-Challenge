package com.fiap.techChallenge.utils.mappers;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.domain.order.Order;

public class OrderMapper {

    public static Order toDomain(OrderEntity entity) {
        if (entity == null) {
            return null;
        }

        Order order = new Order(entity.getId(),
                Order.OrderStatus.valueOf(entity.getStatus().name()));

        order.setInicioPreparo(entity.getInicioPreparo());
        order.setHorarioPronto(entity.getHorarioPronto());
        order.setHorarioEntregue(entity.getHorarioEntregue());
        order.setHorarioFinalizado(entity.getHorarioFinalizado());

        return order;
    }

    public static OrderEntity toEntity(Order domain) {
        if (domain == null) {
            return null;
        }

        OrderEntity entity = new OrderEntity();
        entity.setId(domain.getId());
        entity.setStatus(OrderEntity.OrderStatus.valueOf(domain.getStatus().name()));
        entity.setInicioPreparo(domain.getInicioPreparo());
        entity.setHorarioPronto(domain.getHorarioPronto());
        entity.setHorarioEntregue(domain.getHorarioEntregue());
        entity.setHorarioFinalizado(domain.getHorarioFinalizado());

        return entity;
    }
}