package com.example.demo.member.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;
    private int orderCount;
}
