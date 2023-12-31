package com.app.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProductDatabase,Integer> {

    @Query("SELECT i FROM OrderProductDatabase i WHERE i.orderId = :orderId")
    List<OrderProductDatabase> findByOrderId(@Param("orderId")int orderId);
}
