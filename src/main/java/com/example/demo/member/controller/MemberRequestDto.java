package com.example.demo.member.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberRequestDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;
}
