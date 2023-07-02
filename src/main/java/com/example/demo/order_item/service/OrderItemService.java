package com.example.demo.order_item.service;

import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.order_item.domain.OrderItem;
import com.example.demo.order_item.repository.OrderItemRepository;
import com.example.demo.orders.domain.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;


    public void create(OrderItem orderItem) throws SQLException {
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> findAll() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems;
    }

    public List<OrderItem> findByOrderId(Long order_id) {
        List<OrderItem> orderItems = orderItemRepository.findByOrdersId(order_id);
        return orderItems;
    }


}
