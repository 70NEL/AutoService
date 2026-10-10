package com.autoservice.backend.service;

import com.autoservice.backend.dto.AddToCartRequest;
import com.autoservice.backend.dto.CartDTO;
import com.autoservice.backend.dto.CartItemDTO;
import com.autoservice.backend.dto.PartDTO;
import com.autoservice.backend.model.Cart;
import com.autoservice.backend.model.CartItem;
import com.autoservice.backend.model.User;
import com.autoservice.backend.repository.CartItemRepository;
import com.autoservice.backend.repository.CartRepository;
import com.autoservice.backend.repository.PartRepository;
import com.autoservice.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PartRepository partRepository;
    private final UserRepository userRepository;

    @Transactional
    public CartDTO addItemToCart(String mail , Long partId, Integer quantity) {
        User user = userRepository.findUserByEmail(mail).orElseThrow(() -> new RuntimeException("The email doesnt exist in the data base"));
        Optional<Cart> cart = cartRepository.findCartByUserId(user.getId());

        if(cart.isEmpty()) {
            cart = Optional.of(new Cart());
            cart.get().setUserId(user.getId());
            cart.get().setCartItemList(new ArrayList<>());

            Cart saved = cartRepository.save(cart.get());
            cart.get().setId(saved.getId());// ma asigur ca desi nu exista initial prin save in repo primeste si el id, pentru a putea face interogari pe id pt optional<cartItem>
        }

        Optional<CartItem> cartItem = cartItemRepository.findByPartIdAndCartId(partId, cart.get().getId());

        if(cartItem.isEmpty()) {
            cartItem = Optional.of(new CartItem());
            cartItem.get().setCart(cart.get());
            cartItem.get().setPart(partRepository.findById(partId).orElseThrow(() -> new RuntimeException("The part you are trying to add to the cart does not exist")));
            cartItem.get().setQuantity(quantity);
            CartItem saved = cartItemRepository.save(cartItem.get());
            cartItem.get().setId(saved.getId());
            cart.get().getCartItemList().add(cartItem.get());
        }else {
            cartItem.get().setQuantity(cartItem.get().getQuantity() + quantity);
            cartItemRepository.save(cartItem.get());
        }

        CartDTO cartDTO = CartDTO.mapToDTO(cart.get());

        return cartDTO;
    }

}
