package com.example.demo.orders.service;

import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.controller.MemberDto;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.orders.controller.OrderSearch;
import com.example.demo.orders.domain.Orders;
import com.example.demo.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;


    public void create(Orders orders) throws SQLException {
        // JPA가 order를 building했을 때, item테이블의 변경예정인 값을 임시 저장하고 있다가, 
        // order를 save 할 때, item 테이블도 같이 save(update)
        orderRepository.save(orders);
    }

    public List<Orders> findAll() {
        List<Orders> orders = orderRepository.findAll();
        return orders;
    }

    public void cancel(Long myId) {
        Orders order1 = orderRepository.findById(myId).orElse(null);
        order1.updateCancelStatus();
        order1.getItem().addQuantity(order1.getStockQuantity().intValue());
        orderRepository.save(order1);
    }

    public List<Orders> findByFilter(OrderSearch orderSearch) {
        List<Orders> orders = new ArrayList<>();
        if(isNullOrEmpty(orderSearch.getMemberName()) && orderSearch.getOrderStatus() == null) {
            orders = orderRepository.findAll();
        } else if(!isNullOrEmpty(orderSearch.getMemberName()) && orderSearch.getOrderStatus() == null) {
            // memberName을 가지고, member 찾아오고 member를 가지고 order를 찾는다
            List<Member> members = memberRepository.findByName(orderSearch.getMemberName());
            for(Member m1 : members) {
                List<Orders> orderList = orderRepository.findByMember(m1);
                for(Orders o1 : orderList) {
                    orders.add(o1);
                }
            }
        } else if(isNullOrEmpty(orderSearch.getMemberName()) && orderSearch.getOrderStatus() != null) {
            orders = orderRepository.findByStatus(orderSearch.getOrderStatus());
        } else {
            for(Member m1 : memberRepository.findByName(orderSearch.getMemberName())) {
                for(Orders o1 : orderRepository.findByMemberAndStatus(m1, orderSearch.getOrderStatus())) {
                    orders.add(o1);
                }
            }
        }
        return orders;
    }

    private boolean isNullOrEmpty(String str) {
        if(str == null) {
            return true;
        } else if(str != null && str.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
