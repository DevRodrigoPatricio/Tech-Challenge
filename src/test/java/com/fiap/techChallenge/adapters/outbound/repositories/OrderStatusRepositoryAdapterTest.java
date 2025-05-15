package com.fiap.techChallenge.adapters.outbound.repositories;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.utils.mappers.OrderMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderStatusRepositoryAdapterTest {

    // @Mock
    // private JpaOrderStatusRepository springRepository;

    // @InjectMocks
    // private OrderStatusRepositoryImpl repositoryAdapter;

    // private final UUID orderId = UUID.randomUUID();

    // /**
    //  * Testa a busca de um pedido por ID no adaptador do repositório.
    //  * Verifica:
    //  * 1. Se o pedido é encontrado quando existe no repositório
    //  * 2. Se o mapeamento de OrderEntity para Order é feito corretamente
    //  * 3. Se os campos do pedido retornado estão com os valores esperados
    //  */
    // @Test
    // void shouldFindOrderById() {
    //     OrderEntity entity = new OrderEntity();
    //     entity.setId(orderId);
    //     entity.setStatus(OrderEntity.OrderStatus.READY);

    //     when(springRepository.findById(orderId)).thenReturn(Optional.of(entity));

    //     Optional<Order> result = repositoryAdapter.findById(orderId);

    //     assertTrue(result.isPresent());
    //     assertEquals(orderId, result.get().getId());
    //     assertEquals(OrderStatus.READY, result.get().getStatus());
    // }

    // /**
    //  * Testa o salvamento de um pedido através do adaptador do repositório.
    //  * Verifica:
    //  * 1. Se o pedido é convertido corretamente para OrderEntity antes de salvar
    //  * 2. Se o pedido salvo mantém os mesmos valores do pedido original
    //  * 3. Se o mapeamento de volta para Order funciona corretamente
    //  */
    // @Test
    // void shouldSaveOrder() {
    //     Order order = new Order(orderId, OrderStatus.IN_PREPARATION);
    //     OrderEntity entity = OrderMapper.toEntity(order);

    //     when(springRepository.save(any())).thenReturn(entity);

    //     Order saved = repositoryAdapter.save(order);

    //     assertEquals(orderId, saved.getId());
    //     assertEquals(OrderStatus.IN_PREPARATION, saved.getStatus());
    // }
}