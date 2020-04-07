package ru.otus.highload.socialbackend.rest;

import ru.otus.highload.socialbackend.service.FactoryService;
import ru.otus.highload.socialbackend.service.dto.FactoryDTO;
import ru.otus.highload.socialbackend.rest.errors.BadRequestAlertException;
import ru.otus.highload.socialbackend.rest.util.HeaderUtil;
import ru.otus.highload.socialbackend.rest.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Factory.
 */
@RestController
@RequestMapping("/api")
public class FactoryResource {

    private final Logger log = LoggerFactory.getLogger(FactoryResource.class);

    private static final String ENTITY_NAME = "factory";

    private final FactoryService factoryService;

    public FactoryResource(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    /**
     * POST  /factories : Create a new factory.
     *
     * @param factoryDTO the factoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new factoryDTO, or with status 400 (Bad Request) if the factory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/factories")
    public ResponseEntity<FactoryDTO> createFactory(@RequestBody FactoryDTO factoryDTO) throws URISyntaxException {
        log.debug("REST request to save Factory : {}", factoryDTO);
        if (factoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new factory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FactoryDTO result = factoryService.save(factoryDTO);
        return ResponseEntity.created(new URI("/api/factories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /factories : Updates an existing factory.
     *
     * @param factoryDTO the factoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated factoryDTO,
     * or with status 400 (Bad Request) if the factoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the factoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/factories")
    public ResponseEntity<FactoryDTO> updateFactory(@RequestBody FactoryDTO factoryDTO) throws URISyntaxException {
        log.debug("REST request to update Factory : {}", factoryDTO);
        if (factoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FactoryDTO result = factoryService.save(factoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, factoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /factories : get all the factories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of factories in body
     */
    @GetMapping("/factories")
    public List<FactoryDTO> getAllFactories() {
        log.debug("REST request to get all Factories");
        return factoryService.findAll();
    }

    /**
     * GET  /factories/:id : get the "id" factory.
     *
     * @param id the id of the factoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the factoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/factories/{id}")
    public ResponseEntity<FactoryDTO> getFactory(@PathVariable Long id) {
        log.debug("REST request to get Factory : {}", id);
        Optional<FactoryDTO> factoryDTO = factoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factoryDTO);
    }

    /**
     * DELETE  /factories/:id : delete the "id" factory.
     *
     * @param id the id of the factoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/factories/{id}")
    public ResponseEntity<Void> deleteFactory(@PathVariable Long id) {
        log.debug("REST request to delete Factory : {}", id);
        factoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
