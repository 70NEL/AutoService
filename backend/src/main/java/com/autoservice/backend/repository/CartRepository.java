package com.autoservice.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.autoservice.backend.model.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByUserId(Long id);
}
