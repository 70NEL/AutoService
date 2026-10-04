package com.autoservice.backend.dto;

import com.autoservice.backend.enums.PartCategory;
import com.autoservice.backend.model.Part;
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
    private PartCategory category;
    private Double price;
    private boolean active;

    public static PartDTO mapToDTO(Part part) {
        if(part == null) return null;
        PartDTO dto = new PartDTO();
        dto.setId(part.getId());
        dto.setPrice((part.getPrice()));
        dto.setCode(part.getCode());
        dto.setCategory(part.getCategory());
        dto.setName(part.getName());
        dto.setManufacturer(dto.getManufacturer());
        dto.setActive(part.isActive());
        return dto;
    }
}