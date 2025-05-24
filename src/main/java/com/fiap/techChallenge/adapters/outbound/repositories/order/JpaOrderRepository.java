package com.fiap.techChallenge.adapters.outbound.repositories.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.domain.order.projection.OrderWithStatusAndWaitMinutesProjection;
import com.fiap.techChallenge.domain.order.projection.OrderWithStatusProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.domain.order.projection.OrderItemProjection;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findAllByCustomerId(UUID id);

    @Query(value = """
        SELECT 
            BIN_TO_UUID(o.id) AS orderId,
            h.status AS status,
            h.date AS statusDt,
            BIN_TO_UUID(o.customer_id) AS customerId,
            u.name AS customerName,
            BIN_TO_UUID(o.attendant_id) AS attendantId,
            o.order_dt AS orderDt,
            TIMESTAMPDIFF(MINUTE, o.order_dt, NOW()) AS waitTimeMinutes
        FROM `order` o
    
        INNER JOIN order_status_history h
        ON h.order_id = o.id
    
        INNER JOIN (
            SELECT order_id, MAX(date) AS latest_date
            FROM order_status_history
            GROUP BY order_id
        ) latest ON h.order_id = latest.order_id AND h.date = latest.latest_date
        
        INNER JOIN user u
        ON u.id = o.customer_id
    
        WHERE h.status IN (:statusList)
            AND DATE(h.date) = CURDATE()
            AND (
            h.status != 'FINALIZADO' OR h.date >= DATE_SUB(NOW(), INTERVAL :finalizedMinutes MINUTE)
            )
        ORDER BY h.date
    """, nativeQuery = true)
    List<OrderWithStatusAndWaitMinutesProjection> findTodayOrders(
            @Param("statusList") List<String> statusList,
            @Param("finalizedMinutes") int finalizedMinutes
    );

    @Query(value = """
        SELECT 
            BIN_TO_UUID(o.id) AS orderId,
            h.status AS status,
            h.date AS statusDt,
            BIN_TO_UUID(o.customer_id) AS customerId,
            u.name AS customerName,
            BIN_TO_UUID(o.attendant_id) AS attendantId,
            o.order_dt AS orderDt
        FROM `order` o
        
        INNER JOIN order_status_history h
        ON h.order_id = o.id

        INNER JOIN (
            SELECT order_id, MAX(date) AS latest_date
            FROM order_status_history
            GROUP BY order_id
        ) latest ON h.order_id = latest.order_id AND h.date = latest.latest_date
        
        INNER JOIN user u
        ON u.id = o.customer_id

        WHERE BIN_TO_UUID(o.id) = :id
    """, nativeQuery = true)
    Optional<OrderWithStatusProjection> find(@Param("id") String id);

    @Query(value = """
        SELECT 
            BIN_TO_UUID(o.id) AS orderId,
            h.status AS status,
            h.date AS statusDt,
            BIN_TO_UUID(o.customer_id) AS customerId,
            u.name AS customerName,
            BIN_TO_UUID(o.attendant_id) AS attendantId,
            o.order_dt AS orderDt
        FROM `order` o
        
        INNER JOIN order_status_history h
        ON h.order_id = o.id

        INNER JOIN (
            SELECT order_id, MAX(date) AS latest_date
            FROM order_status_history
            GROUP BY order_id
        ) latest ON h.order_id = latest.order_id AND h.date = latest.latest_date
        
        INNER JOIN user u
        ON u.id = o.customer_id

        WHERE o.order_dt BETWEEN :startDt AND :endDt
    """, nativeQuery = true)
    List<OrderWithStatusProjection> findAllByOrderDt(@Param("startDt") LocalDateTime startDt, @Param("endDt") LocalDateTime endDt);

    @Query(value = """
        SELECT
            BIN_TO_UUID(i.product_id) AS productId,
            i.product_name AS productName,
            i.category AS category,
            i.quantity AS quantity,
            i.unit_price AS unitPrice
        FROM 
            order_items i
        INNER JOIN `order` o
        ON BIN_TO_UUID(o.id) = :id
    """, nativeQuery = true)
    List<OrderItemProjection> findItemsByOrderId(@Param("id") String id);
}
