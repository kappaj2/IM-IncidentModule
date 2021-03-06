package za.co.ajk.incident.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import za.co.ajk.incident.service.EquipmentActivityService;
import za.co.ajk.incident.service.dto.EquipmentActivityDTO;
import za.co.ajk.incident.web.rest.errors.BadRequestAlertException;
import za.co.ajk.incident.web.rest.util.HeaderUtil;

/**
 * REST controller for managing EquipmentActivity.
 */
@RestController
@RequestMapping("/api")
public class EquipmentActivityResource {

    private final Logger log = LoggerFactory.getLogger(EquipmentActivityResource.class);

    private static final String ENTITY_NAME = "equipmentActivity";

    private final EquipmentActivityService equipmentActivityService;

    public EquipmentActivityResource(EquipmentActivityService equipmentActivityService) {
        this.equipmentActivityService = equipmentActivityService;
    }

    /**
     * POST  /v1/equipment-activities : Create a new equipmentActivity.
     *
     * @param equipmentActivityDTO the equipmentActivityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new equipmentActivityDTO, or with status 400 (Bad Request) if the equipmentActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/v1/equipment-activities")
    @Timed
    public ResponseEntity<EquipmentActivityDTO> createEquipmentActivity(@Valid @RequestBody EquipmentActivityDTO equipmentActivityDTO) throws URISyntaxException {
        log.debug("REST request to save EquipmentActivity : {}", equipmentActivityDTO);
        if (equipmentActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipmentActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipmentActivityDTO result = equipmentActivityService.save(equipmentActivityDTO);
        return ResponseEntity.created(new URI("/api/equipment-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /v1/equipment-activities : Updates an existing equipmentActivity.
     *
     * @param equipmentActivityDTO the equipmentActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated equipmentActivityDTO,
     * or with status 400 (Bad Request) if the equipmentActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the equipmentActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/v1/equipment-activities")
    @Timed
    public ResponseEntity<EquipmentActivityDTO> updateEquipmentActivity(@Valid @RequestBody EquipmentActivityDTO equipmentActivityDTO) throws URISyntaxException {
        log.debug("REST request to update EquipmentActivity : {}", equipmentActivityDTO);
        if (equipmentActivityDTO.getId() == null) {
            return createEquipmentActivity(equipmentActivityDTO);
        }
        EquipmentActivityDTO result = equipmentActivityService.save(equipmentActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, equipmentActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /v1/equipment-activities : get all the equipmentActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of equipmentActivities in body
     */
    @GetMapping("/v1/equipment-activities")
    @Timed
    public List<EquipmentActivityDTO> getAllEquipmentActivities() {
        log.debug("REST request to get all EquipmentActivities");
        return equipmentActivityService.findAll();
        }

    /**
     * GET  /v1/equipment-activities/:id : get the "id" equipmentActivity.
     *
     * @param id the id of the equipmentActivityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the equipmentActivityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/v1/equipment-activities/{id}")
    @Timed
    public ResponseEntity<EquipmentActivityDTO> getEquipmentActivity(@PathVariable Long id) {
        log.debug("REST request to get EquipmentActivity : {}", id);
        EquipmentActivityDTO equipmentActivityDTO = equipmentActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(equipmentActivityDTO));
    }

    /**
     * DELETE  /v1/equipment-activities/:id : delete the "id" equipmentActivity.
     *
     * @param id the id of the equipmentActivityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/v1/equipment-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteEquipmentActivity(@PathVariable Long id) {
        log.debug("REST request to delete EquipmentActivity : {}", id);
        equipmentActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /v1/_search/equipment-activities?query=:query : search for the equipmentActivity corresponding
     * to the query.
     *
     * @param query the query of the equipmentActivity search
     * @return the result of the search
     */
    @GetMapping("/v1/_search/equipment-activities")
    @Timed
    public List<EquipmentActivityDTO> searchEquipmentActivities(@RequestParam String query) {
        log.debug("REST request to search EquipmentActivities for query {}", query);
        return equipmentActivityService.search(query);
    }

}
