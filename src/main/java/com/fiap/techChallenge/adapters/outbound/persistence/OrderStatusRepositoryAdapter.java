package com.fiap.techChallenge.adapters.outbound.persistence;

import com.fiap.techChallenge.adapters.outbound.repositories.SpringOrderStatusRepository;
import com.fiap.techChallenge.application.ports.OrderStatusRepositoryPort;
import com.fiap.techChallenge.domain.Order;
import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.adapters.outbound.mappers.OrderMapper;
import com.fiap.techChallenge.domain.exceptions.OrderNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class OrderStatusRepositoryAdapter implements OrderStatusRepositoryPort {

    private final SpringOrderStatusRepository springRepository;

    public OrderStatusRepositoryAdapter(SpringOrderStatusRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        return springRepository.findById(orderId)
                .map(OrderMapper::toDomain);
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderMapper.toEntity(order);
        OrderEntity savedEntity = springRepository.save(entity);
        return OrderMapper.toDomain(savedEntity);
    }


    @Override
    @Transactional
    public void updateOrderStatus(UUID orderId, Order.OrderStatus orderStatus) {
        Order order = springRepository.findById(orderId)
                .map(OrderMapper::toDomain)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.setStatus(orderStatus);

        OrderEntity orderEntity = OrderMapper.toEntity(order);

        OrderEntity orderEntityUpd = springRepository.findById(orderEntity.getId())
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        orderEntityUpd.setStatus(orderEntity.getStatus());
        springRepository.save(orderEntityUpd);
    }

}