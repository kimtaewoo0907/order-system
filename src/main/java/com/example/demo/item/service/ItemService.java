package com.example.demo.item.service;

import com.example.demo.item.controller.ItemDto;
import com.example.demo.item.domain.Item;
import com.example.demo.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public void create(Item item) throws SQLException {
        itemRepository.save(item);
    }

    public List<Item> findAll() {
        List<Item> items = itemRepository.findAll();
        return items;
    }

    public Item findById(Long myId) {
        return itemRepository.findById(myId).orElse(null);
    }

    public void update(Long myId, ItemDto itemDto) {
        // 조회하고, save
        Item item = itemRepository.findById(myId).orElse(null);
        item.updateItem(itemDto.getPrice(), itemDto.getStockQuantity());
        itemRepository.save(item);
    }

    public void delete(Long myId) {
        Item item = itemRepository.findById(myId).orElse(null);
        itemRepository.delete(item);
    }

}
