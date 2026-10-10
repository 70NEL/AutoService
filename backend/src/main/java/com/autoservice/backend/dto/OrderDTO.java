package com.autoservice.backend.dto;

import com.autoservice.backend.model.Order;
import com.autoservice.backend.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> orderItemList;

    public static OrderDTO mapToDTO(Order order) {
        if (order == null) return null;
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setUserId(order.getUserId());
        dto.setOrderItemList(order.getOrderItemList().stream().map(OrderItemDTO::mapToDTO).toList());

        return dto;
    }
}
