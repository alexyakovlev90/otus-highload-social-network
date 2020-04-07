package ru.otus.highload.socialbackend.service.mapper;

import ru.otus.highload.socialbackend.domain.Factory;
import ru.otus.highload.socialbackend.service.dto.FactoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity Factory and its DTO FactoryDTO.
 */
@Component
@Mapper(componentModel = "spring", uses = {})
public interface FactoryMapper extends EntityMapper<FactoryDTO, Factory> {


    @Mapping(target = "buildingMaterials", ignore = true)
    Factory toEntity(FactoryDTO factoryDTO);

    default Factory fromId(Long id) {
        if (id == null) {
            return null;
        }
        Factory factory = new Factory();
        factory.setId(id);
        return factory;
    }
}
