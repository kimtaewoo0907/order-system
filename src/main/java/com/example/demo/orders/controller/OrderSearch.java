package com.example.demo.orders.controller;

import com.example.demo.orders.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String memberName;

    private OrderStatus orderStatus;
}
