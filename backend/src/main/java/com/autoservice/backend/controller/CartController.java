package com.autoservice.backend.controller;

import com.autoservice.backend.dto.AddToCartRequest;
import com.autoservice.backend.dto.CartDTO;
import com.autoservice.backend.service.CartService;
import com.autoservice.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(
            Principal userPrincipal,
            @RequestBody AddToCartRequest request) {

        CartDTO cartDTO = cartService.addItemToCart(userPrincipal.getName(), request.getPartId(), request.getQuantity());

        return ResponseEntity.ok(cartDTO);
    }
}
