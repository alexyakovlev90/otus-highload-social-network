package ru.otus.highload.socialbackend.rest;

import ru.otus.highload.socialbackend.service.BuildingObjectService;
import ru.otus.highload.socialbackend.service.dto.BuildingObjectDTO;
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
 * REST controller for managing BuildingObject.
 */
@RestController
@RequestMapping("/api")
public class BuildingObjectResource {

    private final Logger log = LoggerFactory.getLogger(BuildingObjectResource.class);

    private static final String ENTITY_NAME = "buildingObject";

    private final BuildingObjectService buildingObjectService;

    public BuildingObjectResource(BuildingObjectService buildingObjectService) {
        this.buildingObjectService = buildingObjectService;
    }

    /**
     * POST  /building-objects : Create a new buildingObject.
     *
     * @param buildingObjectDTO the buildingObjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingObjectDTO, or with status 400 (Bad Request) if the buildingObject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/building-objects")
    public ResponseEntity<BuildingObjectDTO> createBuildingObject(@RequestBody BuildingObjectDTO buildingObjectDTO) throws URISyntaxException {
        log.debug("REST request to save BuildingObject : {}", buildingObjectDTO);
        if (buildingObjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new buildingObject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildingObjectDTO result = buildingObjectService.save(buildingObjectDTO);
        return ResponseEntity.created(new URI("/api/building-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /building-objects : Updates an existing buildingObject.
     *
     * @param buildingObjectDTO the buildingObjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingObjectDTO,
     * or with status 400 (Bad Request) if the buildingObjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the buildingObjectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/building-objects")
    public ResponseEntity<BuildingObjectDTO> updateBuildingObject(@RequestBody BuildingObjectDTO buildingObjectDTO) throws URISyntaxException {
        log.debug("REST request to update BuildingObject : {}", buildingObjectDTO);
        if (buildingObjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BuildingObjectDTO result = buildingObjectService.save(buildingObjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, buildingObjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /building-objects : get all the buildingObjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buildingObjects in body
     */
    @GetMapping("/building-objects")
    public List<BuildingObjectDTO> getAllBuildingObjects() {
        log.debug("REST request to get all BuildingObjects");
        return buildingObjectService.findAll();
    }

    /**
     * GET  /building-objects/:id : get the "id" buildingObject.
     *
     * @param id the id of the buildingObjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingObjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/building-objects/{id}")
    public ResponseEntity<BuildingObjectDTO> getBuildingObject(@PathVariable Long id) {
        log.debug("REST request to get BuildingObject : {}", id);
        Optional<BuildingObjectDTO> buildingObjectDTO = buildingObjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buildingObjectDTO);
    }

    /**
     * DELETE  /building-objects/:id : delete the "id" buildingObject.
     *
     * @param id the id of the buildingObjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/building-objects/{id}")
    public ResponseEntity<Void> deleteBuildingObject(@PathVariable Long id) {
        log.debug("REST request to delete BuildingObject : {}", id);
        buildingObjectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
