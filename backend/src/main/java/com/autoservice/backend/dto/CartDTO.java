package com.autoservice.backend.dto;

import com.autoservice.backend.model.Cart;
import com.autoservice.backend.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private Long userId;
    private List<CartItemDTO> cartItemDTOList;

    public static CartDTO mapToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUserId());
        dto.setCartItemDTOList(cart.getCartItemList().stream().map(CartItemDTO::mapToDTO).toList());

        return dto;
    }
}
