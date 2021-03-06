package za.co.ajk.incident.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import za.co.ajk.incident.service.IncidentService;
import za.co.ajk.incident.service.dto.CreateNewIncidentDTO;
import za.co.ajk.incident.service.dto.IncidentDTO;
import za.co.ajk.incident.web.rest.errors.InvalidEquipmentIdReceivedException;
import za.co.ajk.incident.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Incident.
 */
@RestController
@RequestMapping("/api")
public class IncidentResource {

    private final Logger log = LoggerFactory.getLogger(IncidentResource.class);

    private static final String ENTITY_NAME = "incident";

    private final IncidentService incidentService;

    public IncidentResource(IncidentService incidentService) {
        this.incidentService = incidentService;
    }
    
    /**
     * Create a new Incident.
     * @param createNewIncidentDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/v1/incidents")
    @Timed
    public ResponseEntity<IncidentDTO> createNewIncident(@Valid @RequestBody CreateNewIncidentDTO createNewIncidentDTO) throws
                                                                                                 URISyntaxException {
        IncidentDTO result = incidentService.createNewIncident(createNewIncidentDTO);
        return ResponseEntity.created(new URI("/api/incidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /v1/incidents : Updates an existing incident.
     *
     * @param incidentDTO the incidentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incidentDTO,
     * or with status 400 (Bad Request) if the incidentDTO is not valid,
     * or with status 500 (Internal Server Error) if the incidentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/v1/incidents")
    @Timed
    public ResponseEntity<IncidentDTO> updateIncident(@Valid @RequestBody CreateNewIncidentDTO createNewIncidentDTO) throws URISyntaxException {
        log.debug("REST request to update Incident : {}", createNewIncidentDTO);
        
        IncidentDTO incidentDTO = new IncidentDTO();
        IncidentDTO result = incidentService.save(incidentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incidentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /v1/incidents : get all the incidents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of incidents in body
     */
    @GetMapping("/v1/incidents")
    @Timed
    public List<IncidentDTO> getAllIncidents() {
        log.debug("REST request to get all Incidents");
        return incidentService.findAll();
        }

    /**
     * GET  /v1/incidents/:id : get the "id" incident.
     *
     * @param id the id of the incidentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incidentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/v1/incidents/{id}")
    @Timed
    public ResponseEntity<IncidentDTO> getIncident(@PathVariable Long id) {
        log.debug("REST request to get Incident : {}", id);
        IncidentDTO incidentDTO = incidentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(incidentDTO));
    }

    /**
     * DELETE  /v1/incidents/:id : delete the "id" incident.
     *
     * @param id the id of the incidentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/v1/incidents/{id}")
    @Timed
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        log.debug("REST request to delete Incident : {}", id);
        incidentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /v1/_search/incidents?query=:query : search for the incident corresponding
     * to the query.
     *
     * @param query the query of the incident search
     * @return the result of the search
     */
    @GetMapping("/v1/_search/incidents")
    @Timed
    public List<IncidentDTO> searchIncidents(@RequestParam String query) {
        log.debug("REST request to search Incidents for query {}", query);
        return incidentService.search(query);
    }

    @ExceptionHandler({InvalidEquipmentIdReceivedException.class})
    void handleInvalidEquipmentRequest(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid equipment ID received for the company.");
    }
}
