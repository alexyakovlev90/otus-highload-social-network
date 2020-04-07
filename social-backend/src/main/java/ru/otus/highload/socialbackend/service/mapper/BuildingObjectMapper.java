package ru.otus.highload.socialbackend.service.mapper;

import ru.otus.highload.socialbackend.domain.BuildingObject;
import ru.otus.highload.socialbackend.service.dto.BuildingObjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity BuildingObject and its DTO BuildingObjectDTO.
 */
@Component
@Mapper(componentModel = "spring", uses = {RouteMapper.class})
public interface BuildingObjectMapper extends EntityMapper<BuildingObjectDTO, BuildingObject> {

    @Mapping(source = "route.id", target = "routeId")
    BuildingObjectDTO toDto(BuildingObject buildingObject);

    @Mapping(target = "buildingMaterials", ignore = true)
    @Mapping(source = "routeId", target = "route")
    BuildingObject toEntity(BuildingObjectDTO buildingObjectDTO);

    default BuildingObject fromId(Long id) {
        if (id == null) {
            return null;
        }
        BuildingObject buildingObject = new BuildingObject();
        buildingObject.setId(id);
        return buildingObject;
    }
}
