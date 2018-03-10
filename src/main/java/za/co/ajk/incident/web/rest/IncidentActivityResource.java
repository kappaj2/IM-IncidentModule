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
import za.co.ajk.incident.service.IncidentActivityService;
import za.co.ajk.incident.service.dto.IncidentActivityDTO;
import za.co.ajk.incident.web.rest.errors.BadRequestAlertException;
import za.co.ajk.incident.web.rest.util.HeaderUtil;

/**
 * REST controller for managing IncidentActivity.
 */
@RestController
@RequestMapping("/api")
public class IncidentActivityResource {

    private final Logger log = LoggerFactory.getLogger(IncidentActivityResource.class);

    private static final String ENTITY_NAME = "incidentActivity";

    private final IncidentActivityService incidentActivityService;

    public IncidentActivityResource(IncidentActivityService incidentActivityService) {
        this.incidentActivityService = incidentActivityService;
    }

    /**
     * POST  /v1/incident-activities : Create a new incidentActivity.
     *
     * @param incidentActivityDTO the incidentActivityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incidentActivityDTO, or with status 400 (Bad Request) if the incidentActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/v1/incident-activities")
    @Timed
    public ResponseEntity<IncidentActivityDTO> createIncidentActivity(@Valid @RequestBody IncidentActivityDTO incidentActivityDTO) throws URISyntaxException {
        log.debug("REST request to save IncidentActivity : {}", incidentActivityDTO);
        if (incidentActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new incidentActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncidentActivityDTO result = incidentActivityService.save(incidentActivityDTO);
        return ResponseEntity.created(new URI("/api/incident-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /v1/incident-activities : Updates an existing incidentActivity.
     *
     * @param incidentActivityDTO the incidentActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incidentActivityDTO,
     * or with status 400 (Bad Request) if the incidentActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the incidentActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/v1/incident-activities")
    @Timed
    public ResponseEntity<IncidentActivityDTO> updateIncidentActivity(@Valid @RequestBody IncidentActivityDTO incidentActivityDTO) throws URISyntaxException {
        log.debug("REST request to update IncidentActivity : {}", incidentActivityDTO);
        if (incidentActivityDTO.getId() == null) {
            return createIncidentActivity(incidentActivityDTO);
        }
        IncidentActivityDTO result = incidentActivityService.save(incidentActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incidentActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /v1/incident-activities : get all the incidentActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of incidentActivities in body
     */
    @GetMapping("/v1/incident-activities")
    @Timed
    public List<IncidentActivityDTO> getAllIncidentActivities() {
        log.debug("REST request to get all IncidentActivities");
        return incidentActivityService.findAll();
        }

    /**
     * GET  /v1/incident-activities/:id : get the "id" incidentActivity.
     *
     * @param id the id of the incidentActivityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incidentActivityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/v1/incident-activities/{id}")
    @Timed
    public ResponseEntity<IncidentActivityDTO> getIncidentActivity(@PathVariable Long id) {
        log.debug("REST request to get IncidentActivity : {}", id);
        IncidentActivityDTO incidentActivityDTO = incidentActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(incidentActivityDTO));
    }

    /**
     * DELETE  /v1/incident-activities/:id : delete the "id" incidentActivity.
     *
     * @param id the id of the incidentActivityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/v1/incident-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteIncidentActivity(@PathVariable Long id) {
        log.debug("REST request to delete IncidentActivity : {}", id);
        incidentActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /v1/_search/incident-activities?query=:query : search for the incidentActivity corresponding
     * to the query.
     *
     * @param query the query of the incidentActivity search
     * @return the result of the search
     */
    @GetMapping("/v1/_search/incident-activities")
    @Timed
    public List<IncidentActivityDTO> searchIncidentActivities(@RequestParam String query) {
        log.debug("REST request to search IncidentActivities for query {}", query);
        return incidentActivityService.search(query);
    }

}
