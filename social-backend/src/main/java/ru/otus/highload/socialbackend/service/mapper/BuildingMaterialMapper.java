package ru.otus.highload.socialbackend.service.mapper;

import ru.otus.highload.socialbackend.domain.BuildingMaterial;
import ru.otus.highload.socialbackend.service.dto.BuildingMaterialDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity BuildingMaterial and its DTO BuildingMaterialDTO.
 */
@Component
@Mapper(componentModel = "spring", uses = {BuildingObjectMapper.class, VehicleMapper.class, RouteMapper.class, FactoryMapper.class})
public interface BuildingMaterialMapper extends EntityMapper<BuildingMaterialDTO, BuildingMaterial> {

    @Mapping(source = "buildingObject.id", target = "buildingObjectId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "route.id", target = "routeId")
    @Mapping(source = "factory.id", target = "factoryId")
    BuildingMaterialDTO toDto(BuildingMaterial buildingMaterial);

    @Mapping(source = "buildingObjectId", target = "buildingObject")
    @Mapping(source = "vehicleId", target = "vehicle")
    @Mapping(source = "routeId", target = "route")
    @Mapping(source = "factoryId", target = "factory")
    BuildingMaterial toEntity(BuildingMaterialDTO buildingMaterialDTO);

    default BuildingMaterial fromId(Long id) {
        if (id == null) {
            return null;
        }
        BuildingMaterial buildingMaterial = new BuildingMaterial();
        buildingMaterial.setId(id);
        return buildingMaterial;
    }
}
