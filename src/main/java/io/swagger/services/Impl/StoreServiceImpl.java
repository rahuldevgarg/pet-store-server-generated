package io.swagger.services.Impl;

import io.swagger.repository.OrderRepository;
import io.swagger.services.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    OrderRepository orderRepository;
}
