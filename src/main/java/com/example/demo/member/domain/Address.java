package com.example.demo.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

// 별도의 테이블로 존재하지 않고, 다른 entity에 첨부되는 것
//Embedded와 한쌍
@Embeddable
@Getter
@NoArgsConstructor
public class Address {
    private String city;
    private String street;
    private String zipcode;

    //생성자 방식
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
