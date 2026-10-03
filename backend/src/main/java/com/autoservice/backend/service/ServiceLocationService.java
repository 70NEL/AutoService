package com.autoservice.backend.service;

import com.autoservice.backend.dto.ServiceLocationDTO;
import com.autoservice.backend.model.ServiceLocation;
import com.autoservice.backend.repository.ServiceLocationRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ServiceLocationService {
    private final ServiceLocationRepository serviceLocationRepository;

    public ServiceLocationDTO createServiceLocation(ServiceLocationDTO dto) {
        ServiceLocation srv = new ServiceLocation();

        srv.setCity(dto.getCity());
        srv.setAddress(dto.getAddress());
        srv.setLocationName(dto.getLocationName());

        ServiceLocation srvWithId = serviceLocationRepository.save(srv);
        dto.setId(srvWithId.getId());

        return dto;
    }

    private ServiceLocationDTO mapToDTO(ServiceLocation srv) {
        ServiceLocationDTO dto = new ServiceLocationDTO();
        dto.setId(srv.getId());
        dto.setAddress(srv.getAddress());
        dto.setLocationName(srv.getLocationName());
        dto.setCity(srv.getCity());

        return dto;
    }

    public ServiceLocationDTO findServiceLocationById(Long id) {
        ServiceLocation srv = serviceLocationRepository.findById(id).orElseThrow(() -> new RuntimeException("The service location with the specified id does not exist"));
        return mapToDTO(srv);
    }

    public void deleteServiceLocation(Long id) {
        if(!serviceLocationRepository.existsById(id)) {
            throw new RuntimeException("The service location you tried to delete does not exist!");
        }

        serviceLocationRepository.deleteById(id);
    }

    public ServiceLocationDTO updateServiceLocation(Long id, ServiceLocationDTO dto) {
        ServiceLocation srv = serviceLocationRepository.findById(id).orElseThrow(() -> new RuntimeException("The service location you are trying to update does not exist!"));

        srv.setLocationName(dto.getLocationName());
        srv.setCity(dto.getCity());
        srv.setAddress(dto.getAddress());

        ServiceLocation upt = serviceLocationRepository.save(srv);
        return mapToDTO(upt);
    }

    public List<ServiceLocationDTO> filterLocations(String locationName, String address, String city) {
        List<ServiceLocation> list = serviceLocationRepository.filterLocations(locationName, address, city);
        return list.stream().map(this::mapToDTO).toList();
    }
}
