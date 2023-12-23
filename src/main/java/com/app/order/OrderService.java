package com.app.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository , OrderProductRepository orderProductRepository){
        this.orderRepository=orderRepository;
        this.orderProductRepository=orderProductRepository;
    }
    public List<OrderDatabase> getOrderByCustomerId(int userId) {
        return orderRepository.findByCustomerId(userId);
    }

    public List<OrderProductDatabase> getOrderProductByID(int orderID) {
        return orderProductRepository.findByOrderID(orderID);
    }

    public List<OrderDatabase> getOrderByConfAdmin(String confAdmin) {
        return orderRepository.findByConfAdmin(confAdmin);
    }

    public List<OrderDatabase> getOrderByPopUpUser(String popUpUser , int customerId) {
        return orderRepository.findByPopUpUser(popUpUser , customerId);
    }
}
