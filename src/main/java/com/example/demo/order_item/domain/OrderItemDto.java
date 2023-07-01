package com.example.demo.order_item.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderItemDto {

    private Long memberId;
    private List<Long> itemId;
    private List<Long> count;

}
