package com.example.demo.orders.domain;

import com.example.demo.item.domain.Item;
import com.example.demo.member.domain.Address;
import com.example.demo.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.criterion.Order;

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

    // 주문자(회원) 정보 1:n
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member;

    // EnumType.STRING을 주지 않으면 DB에 순서숫자가 들어가게 된다
    // 즉, 0,1 등의 숫자 값이 디폴트
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column
    private LocalDateTime createDate;

    @Builder
    public Orders(Long stockQuantity, Item item, Member member) throws Exception {
        this.member = member;
        this.status = OrderStatus.ORDER;
        this.createDate = LocalDateTime.now();
        // Orders 객체 안에 Item객체를 OneToOne으로 가지고 있기 때문에, item객체에 quantity를
        // 변경시키는 removeQuantity를 호출하고, Orders만 save하여도 Item테이블에 item객체가 변경이 된다
//        this.item.removeQuantity(stockQuantity.intValue());
    }

    public void updateCancelStatus() {
        this.status = OrderStatus.CANCEL;
    }

}
