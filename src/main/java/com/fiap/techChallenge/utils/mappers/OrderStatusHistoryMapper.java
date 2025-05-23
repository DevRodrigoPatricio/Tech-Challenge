package com.fiap.techChallenge.utils.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fiap.techChallenge.adapters.outbound.entities.order.OrderStatusHistoryEntity;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;

public class OrderStatusHistoryMapper {

    public static OrderStatusHistory toDomain(OrderStatusHistoryEntity entity) {
        if (entity == null) {
            return null;
        }

        OrderStatusHistory domain = new OrderStatusHistory(
                entity.getId(),
                entity.getOrderId(),
                entity.getStatus(),
                entity.getDate()
        );

        return domain;
    }

    public static OrderStatusHistoryEntity toEntity(OrderStatusHistory domain) {
        if (domain == null) {
            return null;
        }

        OrderStatusHistoryEntity entity = new OrderStatusHistoryEntity(
                domain.getId(),
                domain.getOrderId(),
                domain.getStatus(),
                domain.getDate()
        );

        return entity;
    }

    public static List<OrderStatusHistory> toDomainList(List<OrderStatusHistoryEntity> entities) {
        List<OrderStatusHistory> domainList = new ArrayList<>();

        domainList.addAll(
                entities.stream()
                        .map(OrderStatusHistoryMapper::toDomain)
                        .collect(Collectors.toList())
        );

        return domainList;
    }

}
