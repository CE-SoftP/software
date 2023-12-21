package com.app.order;

import com.app.Installation.InstallationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class orderService {
    @Autowired
    private orderRepository orderRepository;
    @Autowired
    private orderProductRepository orderProductRepository;
    public List<orderDB> getOrderByCustomerId(int userId) {
        return orderRepository.findByCustomerId(userId);
    }

    public List<orderProductDB> getOrderProductByID(int orderID) {
        return orderProductRepository.findByOrderID(orderID);
    }

    public List<orderDB> getOrderByConfAdmin(String confAdmin) {
        return orderRepository.findByConfAdmin(confAdmin);
    }

    public List<orderDB> getOrderByPopUpUser(String popUpUser) {
        return orderRepository.findByPopUpUser(popUpUser);
    }
}
