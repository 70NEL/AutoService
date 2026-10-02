package com.autoservice.backend.dto;

import com.autoservice.backend.model.Part;
import com.autoservice.backend.model.ServiceLocation;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class InventoryDTO {
    private Long id;
    private ServiceLocationDTO serviceLocation;
    private PartDTO part;
    private Integer currentStock;
}
