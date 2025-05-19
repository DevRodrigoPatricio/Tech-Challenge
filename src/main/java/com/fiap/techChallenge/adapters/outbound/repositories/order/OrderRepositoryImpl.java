package com.fiap.techChallenge.adapters.outbound.repositories.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderRepository;
import com.fiap.techChallenge.utils.exceptions.DomainException;
import com.fiap.techChallenge.utils.mappers.OrderMapper;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository repository;

    public OrderRepositoryImpl(JpaOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderMapper.toEntity(order);
        entity = repository.save(entity);

        return OrderMapper.toDomain(entity);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return repository.findById(id).map(OrderMapper::toDomain);
    }

    @Override
    public Order validate(UUID id) {
        return OrderMapper.toDomain(repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado pelo ID: " + id)));
    }

    @Override
    public List<Order> listByClient(UUID customerId) {
        return OrderMapper.toDomainList(repository.findAllByCustomerId(customerId));
    }

    @Override
    public List<Order> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt) {
        return OrderMapper.toDomainList(repository.findAllByOrderDtBetween(initialDt, finalDt));
    }

    public class OrderNotFoundException extends DomainException {

        public OrderNotFoundException(String message) {
            super(message);
        }
    }
}
