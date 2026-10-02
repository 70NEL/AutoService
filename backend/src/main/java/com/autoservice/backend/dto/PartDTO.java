package com.autoservice.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartDTO {
    private Long id;
    private String code;
    private String name;
    private String manufacturer;
    private String category;
    private Double price;
}