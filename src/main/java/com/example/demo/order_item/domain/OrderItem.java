package com.example.demo.order_item.domain;

import com.example.demo.item.domain.Item;
import com.example.demo.member.domain.Member;
import com.example.demo.orders.domain.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Orders orders;

    @Column
    private LocalDateTime createDate;

    @Builder
    public OrderItem(Long stockQuantity, Item item, Orders orders) throws Exception {
        this.stockQuantity = stockQuantity;
        this.item = item;
        this.orders = orders;
        this.createDate = LocalDateTime.now();
        this.item.removeQuantity(stockQuantity.intValue());
    }

}
