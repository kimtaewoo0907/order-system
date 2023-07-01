package com.example.demo.orders.service;

import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.order_item.domain.OrderItem;
import com.example.demo.order_item.domain.OrderItemDto;
import com.example.demo.order_item.repository.OrderItemRepository;
import com.example.demo.orders.controller.OrderDto;
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
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    public void create(OrderItemDto orderItemDto) throws Exception {
        // JPA가 order를 building했을 때, item테이블의 변경예정인 값을 임시 저장하고 있다가, 
        // order를 save 할 때, item 테이블도 같이 save(update)
        Member member1 = memberRepository.findById(orderItemDto.getMemberId()).orElse(null);
        Orders orders = Orders.builder()
                        .member(member1)
                        .build();
        orderRepository.save(orders);

        for(int i=0; i<orderItemDto.getItemId().size(); i++) {
            OrderItem orderItem = OrderItem.builder()
                    .item(itemRepository.findById(orderItemDto.getItemId().get(i)).orElse(null))
                    .stockQuantity(orderItemDto.getCount().get(i))
                    // order객체는 현재로서 findById 할 수 있는 매개변수가 없다
                    // 그래서, 위에 생성한 order객체를 orderItem에 바로 insert시킬 수 있다
                    // (아직 DB에 저장이 되지 않았음에도 불구하고, 임시저장돼 있는 상태로도 insert가 가능)
                    .orders(orders)
                    .build();
            orderItemRepository.save(orderItem);
        }
    }

    public List<Orders> findAll() {
        List<Orders> orders = orderRepository.findAll();
        return orders;
    }

    public void cancel(Long myId) {
        Orders order1 = orderRepository.findById(myId).orElse(null);
        order1.updateCancelStatus();
//        order1.getItem().addQuantity(order1.getStockQuantity().intValue());
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
