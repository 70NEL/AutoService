package com.autoservice.backend.dto;

import com.autoservice.backend.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private PartDTO partDTO;
    private Double priceAtPurchase;
    private Integer quantity;

    public static OrderItemDTO mapToDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setQuantity(item.getQuantity());
        dto.setId(item.getId());
        dto.setPartDTO(PartDTO.mapToDTO(item.getPart()));
        dto.setPriceAtPurchase(item.getPriceAtPurchase());

        return dto;
    }

    public static OrderItemDTO mapFromCartItemDTOToOrderItemDTO(CartItemDTO cartItemDTO) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setPartDTO(cartItemDTO.getPartDTO());
        dto.setQuantity(cartItemDTO.getQuantity());
        dto.setPriceAtPurchase(cartItemDTO.getPartDTO().getPrice());

        return dto;
    }
}
