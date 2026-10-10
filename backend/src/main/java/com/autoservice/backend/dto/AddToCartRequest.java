package com.autoservice.backend.dto;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long partId;
    private Long serviceLocationId;
    private Integer quantity;
}
