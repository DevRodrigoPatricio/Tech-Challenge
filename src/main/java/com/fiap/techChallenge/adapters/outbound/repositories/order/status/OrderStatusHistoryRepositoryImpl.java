package com.fiap.techChallenge.adapters.outbound.repositories.order.status;

import com.fiap.techChallenge.adapters.outbound.entities.order.OrderStatusHistoryEntity;
import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRepository;
import com.fiap.techChallenge.utils.mappers.OrderStatusHistoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderStatusHistoryRepositoryImpl implements OrderStatusHistoryRepository {

    private final JpaOrderStatusHistoryRepository repository;

    public OrderStatusHistoryRepositoryImpl(JpaOrderStatusHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<OrderStatusHistory> findById(UUID orderStatusHistoryId) {
        return repository.findById(orderStatusHistoryId)
                .map(OrderStatusHistoryMapper::toDomain);
    }

    @Override
    public OrderStatusHistory save(OrderStatusHistory orderStatusHistory) {
        OrderStatusHistoryEntity entity = OrderStatusHistoryMapper.toEntity(orderStatusHistory);

        return OrderStatusHistoryMapper.toDomain(repository.save(entity));
    }

    @Override
    public List<OrderStatusHistory> list(UUID orderId) {
        return OrderStatusHistoryMapper.toDomainList(repository.findAllByOrderIdOrderByDateDesc(orderId));
    }

    @Override
    public Optional<OrderStatusHistory> findLast(UUID orderId) {
        return repository.findFirstByOrderIdOrderByDateDesc(orderId)
                .map(OrderStatusHistoryMapper::toDomain);
    }

    @Override
    public boolean existsByOrderIdAndStatus(UUID orderId, OrderStatus status) {
        return repository.existsByOrderIdAndStatus(orderId, status);
    }

}
