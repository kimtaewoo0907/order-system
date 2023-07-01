package com.example.demo.order_item.service;

import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.order_item.domain.OrderItem;
import com.example.demo.order_item.repository.OrderItemRepository;
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

    public void cancel(Long myId) {
        OrderItem orderItem1 = orderItemRepository.findById(myId).orElse(null);
        orderItem1.getItem().addQuantity(orderItem1.getStockQuantity().intValue());
        orderItemRepository.save(orderItem1);
    }

    public List<OrderItem> findByOrderId(Long order_id) {
        List<OrderItem> orderItems = orderItemRepository.findByOrdersId(order_id);
        return orderItems;
    }

}
