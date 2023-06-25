package com.example.demo.orders.repository;

import com.example.demo.member.domain.Member;
import com.example.demo.orders.domain.OrderStatus;
import com.example.demo.orders.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    // findByA를 하면 A컬럼을 where 조건으로 넣는 것
    List<Orders> findByMember(Member member);
    List<Orders> findByStatus(OrderStatus orderStatus);
    // 2개 이상의 컬럼으로 where 조건문을 걸 때는 and 포함
    List<Orders> findByMemberAndStatus(Member member,OrderStatus orderStatus);
}
