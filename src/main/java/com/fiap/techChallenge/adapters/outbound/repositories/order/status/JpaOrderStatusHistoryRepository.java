package com.fiap.techChallenge.adapters.outbound.repositories.order.status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.adapters.outbound.entities.OrderStatusHistoryEntity;
import com.fiap.techChallenge.domain.enums.OrderStatus;

@Repository
public interface JpaOrderStatusHistoryRepository extends JpaRepository<OrderStatusHistoryEntity, UUID> {

    boolean existsByOrderIdAndStatus(UUID orderId, OrderStatus status);

    List<OrderStatusHistoryEntity> findAllByOrderIdOrderByDateDesc(UUID orderId);

    Optional<OrderStatusHistoryEntity> findFirstByOrderIdOrderByDateDesc(UUID orderId);

    @Query(value = """
    SELECT 
        BIN_TO_UUID(history.order_id),
        history.status,
        history.date,
        BIN_TO_UUID(o.customer_id),
        u.name,
        o.order_dt,
        TIMESTAMPDIFF(MINUTE, o.order_dt, NOW()) AS wait_time_minutes
    FROM order_status_history history

    INNER JOIN (
        SELECT order_id, MAX(date) AS latest_date
        FROM order_status_history
        GROUP BY order_id
    ) latest ON history.order_id = latest.order_id AND history.date = latest.latest_date

    INNER JOIN `order` o
    ON o.id = history.order_id

    INNER JOIN user u
    ON u.id = o.customer_id

    WHERE history.status IN (:statusList)
      AND DATE(history.date) = CURDATE()
      AND (
          history.status != 'FINALIZADO' OR history.date >= DATE_SUB(NOW(), INTERVAL :finalizedMinutes MINUTE)
      )
    ORDER BY history.date
""", nativeQuery = true)
    List<Object[]> findTodayOrderStatus(
            @Param("statusList") List<String> statusList,
            @Param("finalizedMinutes") int finalizedMinutes
    );

}
