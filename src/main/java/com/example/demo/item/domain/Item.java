package com.example.demo.item.domain;

import com.example.demo.member.domain.Address;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private LocalDateTime createDate;

    @Builder
    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createDate = LocalDateTime.now();
    }

    public void updateItem(int price, int stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void removeQuantity(int stockQuantity) throws Exception {
        int new_quantity = this.stockQuantity - stockQuantity;
        if(new_quantity<0) {
            throw new Exception();
        }
        this.stockQuantity = new_quantity;
    }

    public void addQuantity(int stockQuantity) {
        int new_quantity = this.stockQuantity + stockQuantity;
        this.stockQuantity = new_quantity;
    }

}
