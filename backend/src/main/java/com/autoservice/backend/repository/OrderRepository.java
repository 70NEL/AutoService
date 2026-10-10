package com.autoservice.backend.repository;

import com.autoservice.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getOrdersByUserId(Long id);
}
