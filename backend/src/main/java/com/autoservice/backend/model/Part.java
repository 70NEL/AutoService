package com.autoservice.backend.model;

import com.autoservice.backend.enums.PartCategory;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String manufacturer;
    private PartCategory category;
    private Double price;
    private boolean active;
}
