package com.example.demo.item.controller;

import com.example.demo.item.domain.Item;
import com.example.demo.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }

    @GetMapping("items/new")
    public String itemCreateForm(Model model) {
        model.addAttribute("form", new ItemDto());
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String itemCreate(ItemDto itemDto) throws SQLException {
        Item item1 = Item.builder()
                .name(itemDto.getName())
                .price(itemDto.getPrice())
                .stockQuantity(itemDto.getStockQuantity())
                .build();
        itemService.create(item1);
        return "redirect:/items";
    }

    @GetMapping("items")
    public String itemFindAll(Model model) {
        List<Item> item = itemService.findAll();
        model.addAttribute("items", item);
        return "items/itemList";
    }

    @GetMapping("item/{id}/edit")
    public String itemUpdateForm(@PathVariable("id") Long myId, Model model) {
        // get 요청의 parameter 넣는 방법 2가지 1)pathvariable 2)RequestParam(Form을 쓰는 방법)
        Item item = itemService.findById(myId);
        model.addAttribute("form", item);
        return "items/updateItemForm";
    }

    @PostMapping("item/{id}/edit")
    public String itemUpdate(@PathVariable("id") Long myId, Model model, ItemDto itemDto) {
        itemService.update(myId, itemDto);
        return "redirect:/items";
    }

    @GetMapping("item/{id}/delete")
    public String itemDelete(@PathVariable("id") Long myId) {
        itemService.delete(myId);
        return "redirect:/items";
    }

}
