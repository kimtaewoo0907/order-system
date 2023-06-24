package com.example.demo.orders.domain;

import com.example.demo.item.domain.Item;
import com.example.demo.member.domain.Address;
import com.example.demo.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 수량은 별개로 필요
    private Long stockQuantity;

    // 상품명 -> item_id로 대체가능
    @OneToOne
    @JoinColumn(nullable = false)
    private Item item;

    // 주문자(회원) 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member;

    private String status;

    private LocalDateTime createDate;

    @Builder
    public Orders(Long stockQuantity, Item item, Member member) {
        this.stockQuantity = stockQuantity;
        this.item = item;
        this.member = member;
        this.createDate = LocalDateTime.now();
    }

}
