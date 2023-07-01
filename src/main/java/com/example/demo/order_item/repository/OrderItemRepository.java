package com.example.demo.order_item.repository;

import com.example.demo.order_item.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrdersId (Long order_id);
}
