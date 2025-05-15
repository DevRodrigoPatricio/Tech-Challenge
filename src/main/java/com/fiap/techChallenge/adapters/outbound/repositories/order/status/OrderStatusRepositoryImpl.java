package com.fiap.techChallenge.adapters.outbound.repositories.order.status;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.status.OrderStatusRepository;
import com.fiap.techChallenge.utils.exceptions.OrderNotFoundException;
import com.fiap.techChallenge.utils.mappers.OrderMapper;

import jakarta.transaction.Transactional;

@Component
public class OrderStatusRepositoryImpl implements OrderStatusRepository {

    private final JpaOrderStatusRepository springRepository;

    public OrderStatusRepositoryImpl(JpaOrderStatusRepository springRepository) {
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
    public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        Order order = springRepository.findById(orderId)
                .map(OrderMapper::toDomain)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        //order.setStatus(orderStatus);

        OrderEntity orderEntity = OrderMapper.toEntity(order);

        OrderEntity orderEntityUpd = springRepository.findById(orderEntity.getId())
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        //orderEntityUpd.setStatus(orderEntity.getStatus());
        springRepository.save(orderEntityUpd);
    }

}