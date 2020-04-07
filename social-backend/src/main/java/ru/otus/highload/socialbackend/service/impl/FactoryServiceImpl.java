package ru.otus.highload.socialbackend.service.impl;

import ru.otus.highload.socialbackend.domain.Factory;
import ru.otus.highload.socialbackend.repository.FactoryRepository;
import ru.otus.highload.socialbackend.service.FactoryService;
import ru.otus.highload.socialbackend.service.dto.FactoryDTO;
import ru.otus.highload.socialbackend.service.mapper.FactoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Factory.
 */
@Service
@Transactional
public class FactoryServiceImpl implements FactoryService {

    private final Logger log = LoggerFactory.getLogger(FactoryServiceImpl.class);

    private final FactoryRepository factoryRepository;

    private final FactoryMapper factoryMapper;

    public FactoryServiceImpl(FactoryRepository factoryRepository, FactoryMapper factoryMapper) {
        this.factoryRepository = factoryRepository;
        this.factoryMapper = factoryMapper;
    }

    /**
     * Save a factory.
     *
     * @param factoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FactoryDTO save(FactoryDTO factoryDTO) {
        log.debug("Request to save Factory : {}", factoryDTO);
        Factory factory = factoryMapper.toEntity(factoryDTO);
        factory = factoryRepository.save(factory);
        return factoryMapper.toDto(factory);
    }

    /**
     * Get all the factories.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FactoryDTO> findAll() {
        log.debug("Request to get all Factories");
        return factoryRepository.findAll().stream()
            .map(factoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one factory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FactoryDTO> findOne(Long id) {
        log.debug("Request to get Factory : {}", id);
        return factoryRepository.findById(id)
            .map(factoryMapper::toDto);
    }

    /**
     * Delete the factory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Factory : {}", id);
        factoryRepository.deleteById(id);
    }
}
