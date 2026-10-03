package com.autoservice.backend.service;

import com.autoservice.backend.dto.InventoryDTO;
import com.autoservice.backend.dto.PartDTO;
import com.autoservice.backend.dto.ServiceLocationDTO;
import com.autoservice.backend.model.Inventory;
import com.autoservice.backend.model.Part;
import com.autoservice.backend.model.ServiceLocation;
import com.autoservice.backend.repository.InventoryRepository;
import com.autoservice.backend.repository.PartRepository;
import com.autoservice.backend.repository.ServiceLocationRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final PartRepository partRepository;
    private final ServiceLocationRepository serviceLocationRepository;

    public InventoryDTO addStock(InventoryDTO dto) {
        ServiceLocation location = serviceLocationRepository.findById(dto.getServiceLocation().getId())
                .orElseThrow(() -> new RuntimeException("The desired location does not exists in order for me to add a stock in it!"));

        Part part = partRepository.findById(dto.getPart().getId())
                .orElseThrow(() -> new RuntimeException("The desired part does not exist in our catalogues!"));

        Inventory inventory = new Inventory();
        inventory.setServiceLocation(location);
        inventory.setPart(part);
        inventory.setCurrentStock(dto.getCurrentStock());

        Inventory saved = inventoryRepository.save(inventory);
        dto.setId(saved.getId());

        return dto;
    }

    @Transactional
    public InventoryDTO modifyInventory(Long id, InventoryDTO dto) {
        Inventory inv = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("The inventory you are trying to modify does not exist!"));
        inv.setCurrentStock(dto.getCurrentStock());
        inv.setPart(partRepository.findById(dto.getPart().getId()).orElseThrow(() -> new RuntimeException("The part is missing from our catalogue!")));
        inv.setServiceLocation(serviceLocationRepository.findById(dto.getServiceLocation().getId()).orElseThrow(() -> new RuntimeException("The service location you are trying to modify is missing !")));

        Inventory saved = inventoryRepository.save(inv);
        InventoryDTO invDTO = InventoryDTO.mapToDTO(saved);

        return invDTO;
    }
}
