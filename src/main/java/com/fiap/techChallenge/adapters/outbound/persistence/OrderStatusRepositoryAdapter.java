package com.fiap.techChallenge.adapters.outbound.persistence;

import com.fiap.techChallenge.adapters.outbound.repositories.SpringOrderStatusRepository;
import com.fiap.techChallenge.application.ports.OrderStatusRepositoryPort;
import com.fiap.techChallenge.domain.Order;
import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.adapters.outbound.mappers.OrderMapper;
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
}