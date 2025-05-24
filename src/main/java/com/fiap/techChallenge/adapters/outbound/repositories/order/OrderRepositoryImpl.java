package com.fiap.techChallenge.adapters.outbound.repositories.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderItem;
import com.fiap.techChallenge.domain.order.OrderRepository;
import com.fiap.techChallenge.domain.order.dto.OrderWithItemsAndStatusDTO;
import com.fiap.techChallenge.domain.order.projection.OrderItemProjection;
import com.fiap.techChallenge.domain.order.projection.OrderWithStatusAndWaitMinutesProjection;
import com.fiap.techChallenge.domain.order.projection.OrderWithStatusProjection;
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
    public Optional<OrderWithItemsAndStatusDTO> findById(UUID id) {
        Optional<OrderWithStatusProjection> orderDTO = repository.findWithStatusById(id.toString());

        return orderDTO.map(order -> {
            List<OrderItemProjection> items = repository.findItemsByOrderId(id.toString());

            return new OrderWithItemsAndStatusDTO(
                    order.getOrderId(),
                    order.getStatus(),
                    order.getStatusDt(),
                    order.getAttendantId(),
                    order.getAttendantName(),
                    order.getCustomerId(),
                    order.getCustomerName(),
                    order.getPrice(),
                    order.getOrderDt(),
                    items
            );
        });
    }

    @Override
    public Order validate(UUID id) {
        return OrderMapper.toDomain(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido")));
    }

    @Override
    public List<OrderWithStatusProjection> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt) {
        return repository.findAllByOrderDt(initialDt, finalDt);
    }

    @Override
    public List<OrderWithStatusAndWaitMinutesProjection> listTodayOrders(List<String> statusList, int finalizedMinutes) {
        return repository.findTodayOrders(statusList, finalizedMinutes);
    }

    @Override
    public List<OrderItem> findItemsById(UUID id) {
        return OrderMapper.itemToDomainList(repository.findItemsByOrderId(id.toString()));
    }

}
