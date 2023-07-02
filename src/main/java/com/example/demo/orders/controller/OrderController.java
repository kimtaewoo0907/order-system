package com.example.demo.orders.controller;

import com.example.demo.item.controller.ItemDto;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.order_item.domain.OrderItem;
import com.example.demo.order_item.domain.OrderItemDto;
import com.example.demo.orders.domain.Orders;
import com.example.demo.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/order")
    public String orderCreateForm(Model model) {
        model.addAttribute("members", memberRepository.findAll());
        model.addAttribute("items", itemRepository.findAll());
        return "orders/orderForm";
    }

    @PostMapping("/order")
    public String orderCreate(OrderItemDto orderItemDto) throws Exception {
//        Orders orders1 = Orders.builder()
//                .member(memberRepository.findById(orderDto.getMemberId()).orElse(null))
//                .build();
        // orderitem까지 고려하여 로직이 복잡해지므로, service에서 로직처리
        orderService.create(orderItemDto);
        return "redirect:/";
    }

    @GetMapping("orders")
    // @ModelAttribute("orderSearch") 명시적으로 OrderDto와 mapping을 할 수도 있다.
    public String orderFindAll(Model model, OrderSearch orderSearch) {
        List<Orders> orders = orderService.findByFilter(orderSearch);
        model.addAttribute("orders", orders);
        return "orders/orderList";
    }

    @GetMapping("/orders/member")
    public String orderFindByMember(@RequestParam("id")Long myId, Model model, OrderSearch orderSearch) {
        List<Orders> orders = orderService.findByMemberId(myId);
        model.addAttribute("orders", orders);
        return "orders/orderList";
    }

    @PostMapping("orders/{id}/cancel")
    public String orderCancel(@PathVariable("id") Long myId) {
        orderService.cancel(myId);
        return "redirect:/orders";
    }

}
