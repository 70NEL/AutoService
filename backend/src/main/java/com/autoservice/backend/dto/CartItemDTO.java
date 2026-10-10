package com.autoservice.backend.dto;

import com.autoservice.backend.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private PartDTO partDTO;
    private Integer quantity;

    public static CartItemDTO mapToDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setPartDTO(PartDTO.mapToDTO(cartItem.getPart()));
        dto.setQuantity(cartItem.getQuantity());

        return dto;
    }
}
