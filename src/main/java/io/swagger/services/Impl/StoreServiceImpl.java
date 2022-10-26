package io.swagger.services.Impl;

import io.swagger.model.Order;
import io.swagger.repository.OrderRepository;
import io.swagger.services.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order placeOrder(Order order) {
        order.setId(null);
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public Map<String, Integer> getInventory() {
        List<Order> orders = orderRepository.findAll();
        return getStatusQuantityMap(orders);
    }

    private static Map<String, Integer> getStatusQuantityMap(List<Order> orders) {
        Map<String, Integer> inventory = new HashMap<>();
        orders.forEach(t->{
            Integer quantity = inventory.getOrDefault(t.getStatus().toString(),0);
            inventory.put(t.getStatus().toString(),quantity+t.getQuantity());
        });
        return inventory;
    }

    @Override
    public void deleteOrder(Long orderId) {

    }
}
