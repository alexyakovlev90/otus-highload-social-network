package ru.otus.highload.socialbackend.service.mapper;

import ru.otus.highload.socialbackend.domain.Route;
import ru.otus.highload.socialbackend.service.dto.RouteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity Route and its DTO RouteDTO.
 */
@Component
@Mapper(componentModel = "spring", uses = {VehicleMapper.class, FactoryMapper.class})
public interface RouteMapper extends EntityMapper<RouteDTO, Route> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "factory.id", target = "factoryId")
    RouteDTO toDto(Route route);

    @Mapping(source = "vehicleId", target = "vehicle")
    @Mapping(target = "buildingObjects", ignore = true)
    @Mapping(target = "buildingMaterials", ignore = true)
    @Mapping(source = "factoryId", target = "factory")
    Route toEntity(RouteDTO routeDTO);

    default Route fromId(Long id) {
        if (id == null) {
            return null;
        }
        Route route = new Route();
        route.setId(id);
        return route;
    }
}
