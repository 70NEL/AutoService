package com.autoservice.backend.repository;

import com.autoservice.backend.model.ServiceLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceLocationRepository extends JpaRepository<ServiceLocation, Long> {

}
