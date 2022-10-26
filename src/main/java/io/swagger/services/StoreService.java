package io.swagger.services;

import io.swagger.model.Order;

import java.util.Map;
import java.util.Optional;

public interface StoreService {
    Order placeOrder(Order order);
    Optional<Order> getOrderById(Long orderId);
    Map<String, Integer> getInventory();
    void deleteOrder(Long orderId);
}
