package com.example.demo.orders.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private Long memberId;

    private Long itemId;

    private Long count;
}
