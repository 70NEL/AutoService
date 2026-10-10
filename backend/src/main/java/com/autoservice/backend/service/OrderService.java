package com.autoservice.backend.service;

import com.autoservice.backend.dto.OrderDTO;
import com.autoservice.backend.model.*;
import com.autoservice.backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;

    public void deleteOrder(Long id) {
        if(!orderRepository.existsById(id)) {
            throw new RuntimeException("The order you are trying to delete does not exists!");
        }
        orderRepository.deleteById(id);
    }

    @Transactional
    public OrderDTO placeOrder(String mail) {
        User user = userRepository.findUserByEmail(mail).orElseThrow(() -> new RuntimeException("There is no user with such an email in order for me to place an order for him/her"));

        Cart cart = cartRepository.findCartByUserId(user.getId()).orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItemList().isEmpty()) {
            throw new RuntimeException("Cannot place order with an empty cart!");
        }

        Order order = new Order();
        order.setUserId(cart.getUserId());
        order.setCreatedAt(LocalDateTime.now());

        Double totalSum = 0.0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(CartItem cartItem: cart.getCartItemList()) {
            Inventory inventory = inventoryRepository.findByPartIdAndServiceLocationId(cartItem.getPart().getId(), cartItem.getServiceLocation().getId()).orElseThrow(() -> new RuntimeException("No inventory for these ids"));

            if(inventory.getCurrentStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + cartItem.getPart().getName() +
                        " at location " + cartItem.getServiceLocation().getLocationName());
            }

            inventory.setCurrentStock(inventory.getCurrentStock() - cartItem.getQuantity());
            inventoryRepository.save(inventory);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPart(cartItem.getPart());
            orderItem.setPriceAtPurchase(cartItem.getPart().getPrice());

            totalSum += orderItem.getPriceAtPurchase() * orderItem.getQuantity();
            orderItemList.add(orderItem);
        }

        order.setTotalPrice(totalSum);
        order.setOrderItemList(orderItemList);

        Order saved = orderRepository.save(order);

        cartItemRepository.deleteAll(cart.getCartItemList());
        cart.getCartItemList().clear();

        return OrderDTO.mapToDTO(saved);
    }

    @Transactional
    public List<OrderDTO> getOrdersByUser(String mail) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        User user = userRepository.findUserByEmail(mail).orElseThrow(() -> new RuntimeException("user not found"));
        List<Order> orderList = orderRepository.getOrdersByUserId(user.getId());
        for(Order order : orderList) {
            orderDTOList.add(OrderDTO.mapToDTO(order));
        }

        return orderDTOList;
    }
}
