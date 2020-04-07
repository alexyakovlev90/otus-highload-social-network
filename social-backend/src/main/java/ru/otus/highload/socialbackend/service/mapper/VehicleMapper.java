package ru.otus.highload.socialbackend.service.mapper;

import ru.otus.highload.socialbackend.domain.Vehicle;
import ru.otus.highload.socialbackend.service.dto.VehicleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity Vehicle and its DTO VehicleDTO.
 */
@Component
@Mapper(componentModel = "spring", uses = {})
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {


    @Mapping(target = "buildingMaterials", ignore = true)
    @Mapping(target = "routes", ignore = true)
    Vehicle toEntity(VehicleDTO vehicleDTO);

    default Vehicle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }
}
