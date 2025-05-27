package com.fiap.techChallenge.utils.mappers;

import com.fiap.techChallenge.adapters.outbound.entities.order.OrderEntity;
import com.fiap.techChallenge.adapters.outbound.entities.order.OrderItemEmbeddable;
import com.fiap.techChallenge.adapters.outbound.entities.order.OrderStatusEmbeddable;
import com.fiap.techChallenge.adapters.outbound.entities.user.CustomerEntity;
import com.fiap.techChallenge.domain.order.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderItem;
import com.fiap.techChallenge.domain.user.customer.Customer;
import com.fiap.techChallenge.application.dto.order.projection.OrderItemProjection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static List<OrderItem> toItemDomainList(List<OrderItemProjection> projection) {
        List<OrderItem> domainList = new ArrayList<>();

        domainList.addAll(
                projection.stream()
                        .map(OrderMapper::toItemDomain)
                        .toList()
        );

        return domainList;
    }

    public static OrderItem toItemDomain(OrderItemProjection projection) {
        if (projection == null) {
            return null;
        }

        OrderItem item = new OrderItem(
                projection.getProductId(),
                projection.getProductName(),
                projection.getQuantity(),
                projection.getUnitPrice(),
                projection.getCategory()
        );

        return item;
    }

    public static Order toDomain(OrderEntity entity) {
        if (entity == null) {
            return null;
        }

        List<OrderItem> items = entity.getItems().stream().map(i -> new OrderItem(
                i.getProductId(),
                i.getProductName(),
                i.getQuantity(),
                i.getUnitPrice(),
                i.getCategory()
        )).collect(Collectors.toList());

        List<OrderStatusHistory> statusHistory = entity.getStatusHistory().stream().map(s -> new OrderStatusHistory(
                s.getAttendantId(),
                s.getStatus(),
                s.getDate()
        )).collect(Collectors.toList());

        var customerMapper = new CustomerMapper();
        Customer customer = customerMapper.toDomain(entity.getCustomer());

        Order order = new Order(
                entity.getId(),
                items,
                statusHistory,
                customer,
                entity.getPrice(),
                entity.getDate()
        );

        return order;
    }

    public static OrderEntity toEntity(Order domain) {
        if (domain == null) {
            return null;
        }

        List<OrderItemEmbeddable> items = domain.getItems().stream()
                .map(i -> new OrderItemEmbeddable(
                i.getProductId(),
                i.getProductName(),
                i.getQuantity(),
                i.getUnitPrice(),
                i.getCategory()
        )).collect(Collectors.toList());

        List<OrderStatusEmbeddable> statusHistory = domain.getStatusHistory().stream()
                .map(s -> new OrderStatusEmbeddable(
                s.getAttendantId(),
                s.getStatus(),
                s.getDate()
        )).collect(Collectors.toList());

        CustomerMapper customerMapper = new CustomerMapper();
        CustomerEntity customer = customerMapper.toEntity(domain.getCustomer());

        OrderEntity entity = new OrderEntity(
                domain.getId(),
                items,
                statusHistory,
                customer,
                domain.getPrice(),
                domain.getDate()
        );

        return entity;
    }

    public static List<Order> toDomainList(List<OrderEntity> entities) {
        List<Order> domainList = new ArrayList<>();

        domainList.addAll(
                entities.stream()
                        .map(OrderMapper::toDomain)
                        .toList()
        );

        return domainList;
    }
}
