package com.example.demo.orders.controller;

import com.example.demo.item.controller.ItemDto;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.orders.domain.Orders;
import com.example.demo.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String orderCreate(OrderDto orderDto) throws Exception {
        Orders orders1 = Orders.builder()
                .stockQuantity(orderDto.getCount())
                .item(itemRepository.findById(orderDto.getItemId()). orElse(null))
                .member(memberRepository.findById(orderDto.getMemberId()).orElse(null))
                .build();
        orderService.create(orders1);
        return "redirect:/";
    }

    @GetMapping("orders")
    // @ModelAttribute("orderSearch") 명시적으로 OrderDto와 mapping을 할 수도 있다.
    public String orderFindAll(Model model, OrderSearch orderSearch) {
        List<Orders> orders = orderService.findByFilter(orderSearch);
        model.addAttribute("orders", orders);
        return "orders/orderList";
    }

    @PostMapping("orders/{id}/cancel")
    public String orderCancel(@PathVariable("id") Long myId) {
        orderService.cancel(myId);
        return "redirect:/orders";
    }
}
