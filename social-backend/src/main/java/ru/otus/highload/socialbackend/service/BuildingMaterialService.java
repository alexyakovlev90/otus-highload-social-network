package ru.otus.highload.socialbackend.service;

import ru.otus.highload.socialbackend.service.dto.BuildingMaterialDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BuildingMaterial.
 */
public interface BuildingMaterialService {

    /**
     * Save a buildingMaterial.
     *
     * @param buildingMaterialDTO the entity to save
     * @return the persisted entity
     */
    BuildingMaterialDTO save(BuildingMaterialDTO buildingMaterialDTO);

    /**
     * Get all the buildingMaterials.
     *
     * @return the list of entities
     */
    List<BuildingMaterialDTO> findAll();


    /**
     * Get the "id" buildingMaterial.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BuildingMaterialDTO> findOne(Long id);

    /**
     * Delete the "id" buildingMaterial.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
