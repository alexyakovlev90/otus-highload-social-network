package ru.otus.highload.socialbackend.rest;

import ru.otus.highload.socialbackend.service.BuildingMaterialService;
import ru.otus.highload.socialbackend.service.dto.BuildingMaterialDTO;
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
 * REST controller for managing BuildingMaterial.
 */
@RestController
@RequestMapping("/api")
public class BuildingMaterialResource {

    private final Logger log = LoggerFactory.getLogger(BuildingMaterialResource.class);

    private static final String ENTITY_NAME = "buildingMaterial";

    private final BuildingMaterialService buildingMaterialService;

    public BuildingMaterialResource(BuildingMaterialService buildingMaterialService) {
        this.buildingMaterialService = buildingMaterialService;
    }

    /**
     * POST  /building-materials : Create a new buildingMaterial.
     *
     * @param buildingMaterialDTO the buildingMaterialDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingMaterialDTO, or with status 400 (Bad Request) if the buildingMaterial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/building-materials")
    public ResponseEntity<BuildingMaterialDTO> createBuildingMaterial(@RequestBody BuildingMaterialDTO buildingMaterialDTO) throws URISyntaxException {
        log.debug("REST request to save BuildingMaterial : {}", buildingMaterialDTO);
        if (buildingMaterialDTO.getId() != null) {
            throw new BadRequestAlertException("A new buildingMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildingMaterialDTO result = buildingMaterialService.save(buildingMaterialDTO);
        return ResponseEntity.created(new URI("/api/building-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /building-materials : Updates an existing buildingMaterial.
     *
     * @param buildingMaterialDTO the buildingMaterialDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingMaterialDTO,
     * or with status 400 (Bad Request) if the buildingMaterialDTO is not valid,
     * or with status 500 (Internal Server Error) if the buildingMaterialDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/building-materials")
    public ResponseEntity<BuildingMaterialDTO> updateBuildingMaterial(@RequestBody BuildingMaterialDTO buildingMaterialDTO) throws URISyntaxException {
        log.debug("REST request to update BuildingMaterial : {}", buildingMaterialDTO);
        if (buildingMaterialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BuildingMaterialDTO result = buildingMaterialService.save(buildingMaterialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, buildingMaterialDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /building-materials : get all the buildingMaterials.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buildingMaterials in body
     */
    @GetMapping("/building-materials")
    public List<BuildingMaterialDTO> getAllBuildingMaterials() {
        log.debug("REST request to get all BuildingMaterials");
        return buildingMaterialService.findAll();
    }

    /**
     * GET  /building-materials/:id : get the "id" buildingMaterial.
     *
     * @param id the id of the buildingMaterialDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingMaterialDTO, or with status 404 (Not Found)
     */
    @GetMapping("/building-materials/{id}")
    public ResponseEntity<BuildingMaterialDTO> getBuildingMaterial(@PathVariable Long id) {
        log.debug("REST request to get BuildingMaterial : {}", id);
        Optional<BuildingMaterialDTO> buildingMaterialDTO = buildingMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buildingMaterialDTO);
    }

    /**
     * DELETE  /building-materials/:id : delete the "id" buildingMaterial.
     *
     * @param id the id of the buildingMaterialDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/building-materials/{id}")
    public ResponseEntity<Void> deleteBuildingMaterial(@PathVariable Long id) {
        log.debug("REST request to delete BuildingMaterial : {}", id);
        buildingMaterialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
