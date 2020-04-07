package ru.otus.highload.socialbackend.service.impl;

import ru.otus.highload.socialbackend.domain.BuildingMaterial;
import ru.otus.highload.socialbackend.repository.BuildingMaterialRepository;
import ru.otus.highload.socialbackend.service.BuildingMaterialService;
import ru.otus.highload.socialbackend.service.dto.BuildingMaterialDTO;
import ru.otus.highload.socialbackend.service.mapper.BuildingMaterialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BuildingMaterial.
 */
@Service
@Transactional
public class BuildingMaterialServiceImpl implements BuildingMaterialService {

    private final Logger log = LoggerFactory.getLogger(BuildingMaterialServiceImpl.class);

    private final BuildingMaterialRepository buildingMaterialRepository;

    private final BuildingMaterialMapper buildingMaterialMapper;

    public BuildingMaterialServiceImpl(BuildingMaterialRepository buildingMaterialRepository, BuildingMaterialMapper buildingMaterialMapper) {
        this.buildingMaterialRepository = buildingMaterialRepository;
        this.buildingMaterialMapper = buildingMaterialMapper;
    }

    /**
     * Save a buildingMaterial.
     *
     * @param buildingMaterialDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BuildingMaterialDTO save(BuildingMaterialDTO buildingMaterialDTO) {
        log.debug("Request to save BuildingMaterial : {}", buildingMaterialDTO);
        BuildingMaterial buildingMaterial = buildingMaterialMapper.toEntity(buildingMaterialDTO);
        buildingMaterial = buildingMaterialRepository.save(buildingMaterial);
        return buildingMaterialMapper.toDto(buildingMaterial);
    }

    /**
     * Get all the buildingMaterials.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuildingMaterialDTO> findAll() {
        log.debug("Request to get all BuildingMaterials");
        return buildingMaterialRepository.findAll().stream()
            .map(buildingMaterialMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one buildingMaterial by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BuildingMaterialDTO> findOne(Long id) {
        log.debug("Request to get BuildingMaterial : {}", id);
        return buildingMaterialRepository.findById(id)
            .map(buildingMaterialMapper::toDto);
    }

    /**
     * Delete the buildingMaterial by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuildingMaterial : {}", id);
        buildingMaterialRepository.deleteById(id);
    }
}
