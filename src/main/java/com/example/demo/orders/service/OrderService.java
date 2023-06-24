package com.example.demo.orders.service;

import com.example.demo.member.domain.Member;
import com.example.demo.orders.domain.Orders;
import com.example.demo.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void create(Orders orders) throws SQLException {
        orderRepository.save(orders);
    }

    public List<Orders> findAll() {
        List<Orders> orders = orderRepository.findAll();
        return orders;
    }
}
