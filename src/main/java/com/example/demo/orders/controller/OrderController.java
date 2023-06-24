package com.example.demo.orders.controller;

import com.example.demo.orders.domain.Orders;
import com.example.demo.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("order")
    public String orderCreateForm(Model model) {
        return "orders/orderForm";
    }

    @PostMapping("order")
    public String orderCreate() throws SQLException {
        Orders orders1 = Orders.builder()
                .stockQuantity()
                .item()
                .member()
                .build();
        orderService.create(orders1);
        return "redirect:/orders";
    }

    @GetMapping("orders")
    public String orderFindAll(Model model) {
        List<Orders> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders/orderList";
    }
}
