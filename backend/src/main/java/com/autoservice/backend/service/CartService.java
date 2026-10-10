package com.autoservice.backend.service;

import com.autoservice.backend.dto.*;
import com.autoservice.backend.model.*;
import com.autoservice.backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PartRepository partRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ServiceLocationRepository serviceLocationRepository;

    @Transactional
    public CartDTO addItemToCart(String mail , Long partId, Long serviceLocationId, Integer quantity) {
        User user = userRepository.findUserByEmail(mail).orElseThrow(() -> new RuntimeException("The email doesnt exist in the data base"));
        ServiceLocation serviceLocation =  serviceLocationRepository.findById(serviceLocationId).orElseThrow(()-> new RuntimeException("Service does not exist"));
        Optional<Cart> cart = cartRepository.findCartByUserId(user.getId());

        if(cart.isEmpty()) {
            cart = Optional.of(new Cart());
            cart.get().setUserId(user.getId());
            cart.get().setCartItemList(new ArrayList<>());

            Cart saved = cartRepository.save(cart.get());
            cart.get().setId(saved.getId());// ma asigur ca desi nu exista initial prin save in repo primeste si el id, pentru a putea face interogari pe id pt optional<cartItem>
        }

        Optional<CartItem> cartItem = cartItemRepository.findByPartIdAndCartIdAndServiceLocationId(partId, cart.get().getId(), serviceLocationId);

        if(cartItem.isEmpty()) {
            cartItem = Optional.of(new CartItem());
            cartItem.get().setCart(cart.get());
            cartItem.get().setPart(partRepository.findById(partId).orElseThrow(() -> new RuntimeException("The part you are trying to add to the cart does not exist")));
            cartItem.get().setQuantity(quantity);
            cartItem.get().setServiceLocation(serviceLocation);
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

    @Transactional
    public CartDTO removeItemFromCart(String mail , Long partId, Long serviceLocationId,Integer quantity) {
        User user = userRepository.findUserByEmail(mail).orElseThrow(() -> new RuntimeException("The email doesnt exist in the data base"));
        Cart cart = cartRepository.findCartByUserId(user.getId()).orElseThrow(() -> new RuntimeException("The cart does not exist, i cannot remove any items"));

        CartItem cartItem = cartItemRepository.findByPartIdAndCartIdAndServiceLocationId(partId, cart.getId(), serviceLocationId).orElseThrow(() -> new RuntimeException("The cartItem does not exist, i cannot operate on it"));
        if(quantity >= cartItem.getQuantity()) {
            cart.getCartItemList().remove(cartItem);
            cartItemRepository.delete(cartItem);
        }else {
            cartItem.setQuantity(cartItem.getQuantity() - quantity);
            cartItemRepository.save(cartItem);
        }

        return CartDTO.mapToDTO(cart);
    }
}
