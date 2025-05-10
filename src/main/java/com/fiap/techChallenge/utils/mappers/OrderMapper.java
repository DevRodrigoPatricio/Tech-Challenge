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

        order.setStartPreparation(entity.getStartPreparation());
        order.setReadySchedule(entity.getReadySchedule());
        order.setDeliveryTime(entity.getDeliveryTime());
        order.setFinalizedSchedule(entity.getFinalizedSchedule());

        return order;
    }

    public static OrderEntity toEntity(Order domain) {
        if (domain == null) {
            return null;
        }

        OrderEntity entity = new OrderEntity();
        entity.setId(domain.getId());
        entity.setStatus(OrderEntity.OrderStatus.valueOf(domain.getStatus().name()));
        entity.setStartPreparation(domain.getStartPreparation());
        entity.setReadySchedule(domain.getReadySchedule());
        entity.setDeliveryTime(domain.getDeliveryTime());
        entity.setFinalizedSchedule(domain.getFinalizedSchedule());

        return entity;
    }
}