package com.autoservice.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceLocationDTO {
    private Long id;
    private String locationName;
    private String address;
    private String city;
}