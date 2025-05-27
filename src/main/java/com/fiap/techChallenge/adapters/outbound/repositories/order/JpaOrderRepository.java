package com.fiap.techChallenge.adapters.outbound.repositories.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.adapters.outbound.entities.order.OrderEntity;

import com.fiap.techChallenge.application.dto.order.projection.OrderWithStatusAndWaitMinutesProjection;
import com.fiap.techChallenge.application.dto.order.projection.OrderWithStatusProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.application.dto.order.projection.OrderItemProjection;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findAllByCustomerId(UUID id);

    @Query(value = """
    SELECT 
        BIN_TO_UUID(h.order_id) AS orderId,
        h.status AS status,
        h.date AS statusDt,
        BIN_TO_UUID(o.customer_id) AS customerId,
        c.name AS customerName,
        BIN_TO_UUID(h.attendant_id) AS attendantId,
        a.name AS attendantName,
        o.date AS orderDt,
        TIMESTAMPDIFF(MINUTE, o.date, NOW()) AS waitTimeMinutes
    FROM order_status_history h

    INNER JOIN (
        SELECT order_id, MAX(date) AS latest_date
        FROM order_status_history
        GROUP BY order_id
    ) latest ON h.order_id = latest.order_id AND h.date = latest.latest_date

    INNER JOIN `order` o ON o.id = h.order_id

    INNER JOIN user c ON c.id = o.customer_id

    LEFT JOIN user a ON a.id = h.attendant_id

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
        BIN_TO_UUID(h.order_id) AS orderId,
        h.status AS status,
        h.date AS statusDt,
        BIN_TO_UUID(o.customer_id) AS customerId,
        c.name AS customerName,
        BIN_TO_UUID(h.attendant_id) AS attendantId,
        a.name AS attendantName,
        o.price AS price,
        o.date AS orderDt
    FROM order_status_history h

    INNER JOIN (
        SELECT order_id, MAX(date) AS latest_date
        FROM order_status_history
        GROUP BY order_id
    ) latest ON h.order_id = latest.order_id AND h.date = latest.latest_date

    INNER JOIN `order` o ON o.id = h.order_id

    INNER JOIN user c ON c.id = o.customer_id

    LEFT JOIN user a ON a.id = h.attendant_id

    WHERE BIN_TO_UUID(h.order_id) = :id
    """, nativeQuery = true)
    Optional<OrderWithStatusProjection> findWithStatusById(@Param("id") String id);

    @Query(value = """
        SELECT 
            BIN_TO_UUID(o.id) AS orderId,
            h.status AS status,
            h.date AS statusDt,
            BIN_TO_UUID(o.customer_id) AS customerId,
            c.name AS customerName,
            BIN_TO_UUID(h.attendant_id) AS attendantId,
            a.name AS attendantName,
            o.price AS price,
            o.date AS orderDt
        FROM `order` o

        INNER JOIN order_status_history h ON h.order_id = o.id

        INNER JOIN (
            SELECT order_id, MAX(date) AS latest_date
            FROM order_status_history
            GROUP BY order_id
        ) latest ON h.order_id = latest.order_id AND h.date = latest.latest_date

        INNER JOIN user c ON c.id = o.customer_id

        LEFT JOIN user a ON a.id = h.attendant_id

        WHERE h.date BETWEEN :startDt AND :endDt

        ORDER BY 
            FIELD(h.status, 'RECEBIDO', 'EM_PREPARACAO', 'PRONTO', 'FINALIZADO', 'CANCELADO'),
            o.date
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
        ON o.id = i.order_id
        
        WHERE BIN_TO_UUID(o.id) = :id
    """, nativeQuery = true)
    List<OrderItemProjection> findItemsByOrderId(@Param("id") String id);
}
