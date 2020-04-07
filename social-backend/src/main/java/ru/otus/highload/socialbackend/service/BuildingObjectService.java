package ru.otus.highload.socialbackend.service;

import ru.otus.highload.socialbackend.service.dto.BuildingObjectDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BuildingObject.
 */
public interface BuildingObjectService {

    /**
     * Save a buildingObject.
     *
     * @param buildingObjectDTO the entity to save
     * @return the persisted entity
     */
    BuildingObjectDTO save(BuildingObjectDTO buildingObjectDTO);

    /**
     * Get all the buildingObjects.
     *
     * @return the list of entities
     */
    List<BuildingObjectDTO> findAll();


    /**
     * Get the "id" buildingObject.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BuildingObjectDTO> findOne(Long id);

    /**
     * Delete the "id" buildingObject.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
