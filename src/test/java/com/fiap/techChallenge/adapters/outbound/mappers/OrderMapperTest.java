package com.fiap.techChallenge.adapters.outbound.mappers;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.Order.OrderStatus;
import com.fiap.techChallenge.utils.mappers.OrderMapper;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    /**
     * Testa a conversão de OrderEntity (camada de persistência) para Order (domínio)
     * Verifica:
     * 1. Se o ID é mapeado corretamente
     * 2. Se o status é convertido adequadamente entre os enums
     * 3. Se os timestamps são preservados na conversão
     */
    @Test
    void shouldConvertEntityToDomain() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        OrderEntity entity = new OrderEntity();
        entity.setId(id);
        entity.setStatus(OrderEntity.OrderStatus.PRONTO);
        entity.setHorarioPronto(now);

        Order domain = OrderMapper.toDomain(entity);

        assertEquals(id, domain.getId());
        assertEquals(OrderStatus.PRONTO, domain.getStatus());
        assertEquals(now, domain.getHorarioPronto());
    }

    /**
     * Testa a conversão de Order (domínio) para OrderEntity (persistência)
     * Verifica:
     * 1. Se o ID é mantido igual
     * 2. Se o status é convertido corretamente
     * 3. Se os campos de tempo são copiados adequadamente
     */
    @Test
    void shouldConvertDomainToEntity() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Order domain = new Order(id, OrderStatus.EM_PREPARACAO);
        domain.setInicioPreparo(now);

        OrderEntity entity = OrderMapper.toEntity(domain);

        assertEquals(id, entity.getId());
        assertEquals(OrderEntity.OrderStatus.EM_PREPARACAO, entity.getStatus());
        assertEquals(now, entity.getInicioPreparo());
    }

    /**
     * Testa o comportamento do mapper com entrada nula
     * Verifica:
     * 1. Se o metodo retorna null quando recebe null
     * 2. Se não lança exceções com entrada inválida
     */
    @Test
    void shouldHandleNullInput() {
        assertNull(OrderMapper.toDomain(null));
        assertNull(OrderMapper.toEntity(null));
    }
}