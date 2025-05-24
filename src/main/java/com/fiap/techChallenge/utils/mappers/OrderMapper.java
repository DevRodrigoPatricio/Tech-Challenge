package com.fiap.techChallenge.utils.mappers;

import com.fiap.techChallenge.adapters.outbound.entities.order.OrderEntity;
import com.fiap.techChallenge.adapters.outbound.entities.order.OrderItemEmbeddable;
import com.fiap.techChallenge.adapters.outbound.entities.user.AttendantEntity;
import com.fiap.techChallenge.adapters.outbound.entities.user.CustomerEntity;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderItem;
import com.fiap.techChallenge.application.dto.order.projection.OrderItemProjection;
import com.fiap.techChallenge.domain.user.attendant.Attendant;
import com.fiap.techChallenge.domain.user.customer.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static List<OrderItem> itemToDomainList(List<OrderItemProjection> projection) {
        List<OrderItem> domainList = new ArrayList<>();

        domainList.addAll(
                projection.stream()
                        .map(OrderMapper::itemToDomain)
                        .toList()
        );

        return domainList;
    }

    public static OrderItem itemToDomain(OrderItemProjection projection) {
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

        var customerMapper = new CustomerMapper();
        Customer customer = customerMapper.toDomain(entity.getCustomer());

        var attendantMapper = new AttendantMapper();
        Attendant attendant = attendantMapper.toDomain(entity.getAttendant());

        Order order = new Order(
                entity.getId(),
                items,
                customer,
                attendant,
                entity.getPrice(),
                entity.getOrderDt()
        );

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

        var customerMapper = new CustomerMapper();
        CustomerEntity customer = customerMapper.toEntity(domain.getCustomer());

        var attendantMapper = new AttendantMapper();
        AttendantEntity attendant = attendantMapper.toEntity(domain.getAttendant());

        OrderEntity entity = new OrderEntity();
        entity.setId(domain.getId());
        entity.setItems(embeddables);
        entity.setCustomer(customer);
        entity.setAttendant(attendant);
        entity.setPrice(domain.getPrice());
        entity.setOrderDt(domain.getOrderDt());

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
