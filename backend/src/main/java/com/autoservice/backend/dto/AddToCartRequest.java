package com.autoservice.backend.dto;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long partId;
    private Integer quantity;
}
