package ru.otus.highload.socialbackend.service;

import ru.otus.highload.socialbackend.service.dto.RouteDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Route.
 */
public interface RouteService {

    /**
     * Save a route.
     *
     * @param routeDTO the entity to save
     * @return the persisted entity
     */
    RouteDTO save(RouteDTO routeDTO);

    /**
     * Get all the routes.
     *
     * @return the list of entities
     */
    List<RouteDTO> findAll();


    /**
     * Get the "id" route.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RouteDTO> findOne(Long id);

    /**
     * Delete the "id" route.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
