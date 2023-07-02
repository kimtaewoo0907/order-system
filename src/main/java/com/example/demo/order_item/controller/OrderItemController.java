package com.example.demo.order_item.controller;

import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.order_item.domain.OrderItem;
import com.example.demo.order_item.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("orderitems")
    // @ModelAttribute("orderSearch") 명시적으로 OrderDto와 mapping을 할 수도 있다.
    public String orderItemFindAll(@RequestParam("id")Long order_id, Model model) {
        List<OrderItem> orderItems = orderItemService.findByOrderId(order_id);
        model.addAttribute("order_item", orderItems);
        return "orders/ordersDetail";
    }

    

}
