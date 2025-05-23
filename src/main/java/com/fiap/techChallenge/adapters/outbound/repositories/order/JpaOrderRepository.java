package com.fiap.techChallenge.adapters.outbound.repositories.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID> {

    @Query(value = """
        SELECT 
            BIN_TO_UUID(o.id),
            history.status,
            history.date last_status_dt,
            BIN_TO_UUID(o.customer_id),
            u.name,
            o.order_dt,
            TIMESTAMPDIFF(MINUTE, o.order_dt, NOW()) AS wait_time_minutes
        FROM `order` o
    
        INNER JOIN order_status_history history
        ON history.order_id = o.id
    
        INNER JOIN (
            SELECT order_id, MAX(date) AS latest_date
            FROM order_status_history
            GROUP BY order_id
        ) latest ON history.order_id = latest.order_id AND history.date = latest.latest_date
        
        INNER JOIN user u
        ON u.id = o.customer_id
    
        WHERE history.status IN (:statusList)
            AND DATE(history.date) = CURDATE()
            AND (
            history.status != 'FINALIZADO' OR history.date >= DATE_SUB(NOW(), INTERVAL :finalizedMinutes MINUTE)
            )
        ORDER BY history.date
""", nativeQuery = true)
    List<Object[]> findTodayOrders(
            @Param("statusList") List<String> statusList,
            @Param("finalizedMinutes") int finalizedMinutes
    );

    List<OrderEntity> findAllByCustomerId(UUID id);

    List<OrderEntity> findAllByOrderDtBetween(LocalDateTime startDate, LocalDateTime endDate);

}
