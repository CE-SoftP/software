package com.app.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderDatabase, Integer> {

    @Query("SELECT i FROM orderDB i WHERE i.customerId = :customerId")
    List<OrderDatabase> findByCustomerId(@Param("customerId")int userId);

    @Query("SELECT i FROM orderDB i WHERE i.confAdmin = :confAdmin")
    List<OrderDatabase> findByConfAdmin(@Param("confAdmin")String confAdmin);

    @Query("SELECT i FROM orderDB i WHERE i.popUpUser = :popUpUser AND i.customerId = :customerId")
    List<OrderDatabase> findByPopUpUser(@Param("popUpUser")String popUpUser , @Param("customerId")int customerId );
}
