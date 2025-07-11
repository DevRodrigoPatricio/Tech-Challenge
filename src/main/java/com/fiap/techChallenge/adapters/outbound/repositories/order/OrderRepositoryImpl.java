package com.fiap.techChallenge.adapters.outbound.repositories.order;

import com.fiap.techChallenge.adapters.outbound.entities.order.OrderEntity;
import com.fiap.techChallenge.domain.core.order.Order;
import com.fiap.techChallenge.domain.core.order.OrderItem;
import com.fiap.techChallenge.domain.core.order.OrderRepository;
import com.fiap.techChallenge.domain.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.application.dto.order.OrderWithItemsAndStatusDTO;
import com.fiap.techChallenge.application.dto.order.projection.OrderItemProjection;
import com.fiap.techChallenge.application.dto.order.projection.OrderWithStatusAndWaitMinutesProjection;
import com.fiap.techChallenge.application.dto.order.projection.OrderWithStatusProjection;
import com.fiap.techChallenge.utils.mappers.OrderMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository repository;
    private final OrderMapper orderMapper;

    public OrderRepositoryImpl(JpaOrderRepository repository, OrderMapper orderMapper) {
        this.repository = repository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = orderMapper.toEntity(order);
        entity = repository.save(entity);

        return orderMapper.toDomain(entity);
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
        OrderEntity order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido"));
        return orderMapper.toDomain(order);
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
        return orderMapper.toItemDomainList(repository.findItemsByOrderId(id.toString()));
    }

}
