package com.autoservice.backend.repository;

import com.autoservice.backend.model.CartItem;
import com.autoservice.backend.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByPartIdAndCartIdAndServiceLocationId(Long partId, Long cartId, Long serviceId);
}
