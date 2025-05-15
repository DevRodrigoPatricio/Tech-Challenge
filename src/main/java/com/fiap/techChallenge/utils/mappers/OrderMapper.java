package com.fiap.techChallenge.utils.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.adapters.outbound.entities.OrderItemEmbeddable;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderItem;

public class OrderMapper {

    public static Order toDomain(OrderEntity entity) {
        if (entity == null) {
            return null;
        }

        List<OrderItem> items = entity.getItems().stream().map(i -> new OrderItem(
                i.getProductId(), i.getProductName(),
                i.getQuantity(),
                i.getUnitPrice(),
                i.getCategory()
        )).collect(Collectors.toList());

        Order order = new Order(entity.getId(),
                items, entity.getClientId(), entity.getAttendantId(),
                entity.getPrice(), entity.getOrderDt());

        return order;
    }

    public static OrderEntity toEntity(Order domain) {
        if (domain == null) {
            return null;
        }

        List<OrderItemEmbeddable> embeddables = domain.getItems().stream()
                .map(i -> new OrderItemEmbeddable(
                i.getProductId(),
                i.getProductName(),
                i.getQuantity(),
                i.getUnitPrice(),
                i.getCategory()
        )).collect(Collectors.toList());

        OrderEntity entity = new OrderEntity();
        entity.setId(domain.getId());
        entity.setItems(embeddables);
        entity.setClientId(domain.getClientId());
        entity.setAttendantId(domain.getAttendantId());
        entity.setPrice(domain.getPrice());
        entity.setOrderDt(domain.getOrderDt());

        return entity;
    }

    public static List<Order> toDomainList(List<OrderEntity> entities) {
        List<Order> domainList = new ArrayList<>();

        domainList.addAll(
                entities.stream()
                        .map(OrderMapper::toDomain)
                        .collect(Collectors.toList())
        );

        return domainList;
    }
}
