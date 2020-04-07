package ru.otus.highload.socialbackend.service.impl;

import ru.otus.highload.socialbackend.domain.BuildingObject;
import ru.otus.highload.socialbackend.repository.BuildingObjectRepository;
import ru.otus.highload.socialbackend.service.BuildingObjectService;
import ru.otus.highload.socialbackend.service.dto.BuildingObjectDTO;
import ru.otus.highload.socialbackend.service.mapper.BuildingObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BuildingObject.
 */
@Service
@Transactional
public class BuildingObjectServiceImpl implements BuildingObjectService {

    private final Logger log = LoggerFactory.getLogger(BuildingObjectServiceImpl.class);

    private final BuildingObjectRepository buildingObjectRepository;

    private final BuildingObjectMapper buildingObjectMapper;

    public BuildingObjectServiceImpl(BuildingObjectRepository buildingObjectRepository, BuildingObjectMapper buildingObjectMapper) {
        this.buildingObjectRepository = buildingObjectRepository;
        this.buildingObjectMapper = buildingObjectMapper;
    }

    /**
     * Save a buildingObject.
     *
     * @param buildingObjectDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BuildingObjectDTO save(BuildingObjectDTO buildingObjectDTO) {
        log.debug("Request to save BuildingObject : {}", buildingObjectDTO);
        BuildingObject buildingObject = buildingObjectMapper.toEntity(buildingObjectDTO);
        buildingObject = buildingObjectRepository.save(buildingObject);
        return buildingObjectMapper.toDto(buildingObject);
    }

    /**
     * Get all the buildingObjects.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuildingObjectDTO> findAll() {
        log.debug("Request to get all BuildingObjects");
        return buildingObjectRepository.findAll().stream()
            .map(buildingObjectMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one buildingObject by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BuildingObjectDTO> findOne(Long id) {
        log.debug("Request to get BuildingObject : {}", id);
        return buildingObjectRepository.findById(id)
            .map(buildingObjectMapper::toDto);
    }

    /**
     * Delete the buildingObject by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuildingObject : {}", id);
        buildingObjectRepository.deleteById(id);
    }
}
