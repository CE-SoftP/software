package com.app.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProductDatabase,Integer> {

    @Query("SELECT i FROM orderProductDB i WHERE i.orderId = :orderId")
    List<OrderProductDatabase> findByOrderID(@Param("orderId")int orderId);
}
