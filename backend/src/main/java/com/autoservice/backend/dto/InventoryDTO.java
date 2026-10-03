package com.autoservice.backend.dto;

import com.autoservice.backend.model.Inventory;
import com.autoservice.backend.model.Part;
import com.autoservice.backend.model.ServiceLocation;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class InventoryDTO {
    private Long id;
    private ServiceLocationDTO serviceLocation;
    private PartDTO part;
    private Integer currentStock;

    public static InventoryDTO mapToDTO(Inventory inv) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inv.getId());
        Part part = inv.getPart();
        ServiceLocation location = inv.getServiceLocation();
        PartDTO partDTO = PartDTO.mapToDTO(part);
        ServiceLocationDTO locationDTO = ServiceLocationDTO.mapToDTO(location);

        dto.setPart(partDTO);
        dto.setServiceLocation(locationDTO);
        dto.setCurrentStock(inv.getCurrentStock());

        return dto;
    }
}
