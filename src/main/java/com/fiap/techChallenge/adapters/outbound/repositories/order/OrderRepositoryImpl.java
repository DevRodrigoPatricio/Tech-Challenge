package com.fiap.techChallenge.adapters.outbound.repositories.order;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderRepository;
import com.fiap.techChallenge.domain.order.OrderWithStatusDTO;
import com.fiap.techChallenge.utils.exceptions.EntityNotFoundException;
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
                .orElseThrow(() -> new EntityNotFoundException("Pedido")));
    }

    @Override
    public List<Order> listByClient(UUID customerId) {
        return OrderMapper.toDomainList(repository.findAllByCustomerId(customerId));
    }

    @Override
    public List<Order> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt) {
        return OrderMapper.toDomainList(repository.findAllByOrderDtBetween(initialDt, finalDt));
    }

    @Override
    public List<OrderWithStatusDTO> listTodayOrders(List<String> statusList, int finalizedMinutes) {
        List<Object[]> results = repository.findTodayOrders(statusList, finalizedMinutes);

        return results.stream()
                .map(row -> new OrderWithStatusDTO(
                UUID.fromString((String) row[0]),
                OrderStatus.valueOf((String) row[1]),
                ((Timestamp) row[2]).toLocalDateTime(),
                UUID.fromString((String) row[3]),
                (String) row[4],
                ((Timestamp) row[5]).toLocalDateTime(),
                ((Number) row[6]).intValue()
        ))
                .toList();
    }

}
