package za.co.ajk.incident.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import za.co.ajk.incident.service.IncidentService;
import za.co.ajk.incident.service.TestService;
import za.co.ajk.incident.service.dto.CreateNewIncidentDTO;
import za.co.ajk.incident.service.dto.IncidentDTO;
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
    
    @Autowired
    private TestService testService;
    
    public IncidentResource(IncidentService incidentService) {
        this.incidentService = incidentService;
    }
    
    //    /**
    //     * POST  /incidents : Create a new incident.
    //     *
    //     * @param incidentDTO the incidentDTO to create
    //     * @return the ResponseEntity with status 201 (Created) and with body the new incidentDTO, or with status 400 (Bad Request) if the incident has already an ID
    //     * @throws URISyntaxException if the Location URI syntax is incorrect
    //     */
    //    @PostMapping("/incidents")
    //    @Timed
    //    public ResponseEntity<IncidentDTO> createIncident(@Valid @RequestBody IncidentDTO incidentDTO) throws URISyntaxException {
    //        log.debug("REST request to save Incident : {}", incidentDTO);
    //        if (incidentDTO.getId() != null) {
    //            throw new BadRequestAlertException("A new incident cannot already have an ID", ENTITY_NAME, "idexists");
    //        }
    //        IncidentDTO result = incidentService.save(incidentDTO);
    //        return ResponseEntity.created(new URI("/api/incidents/" + result.getId()))
    //            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
    //            .body(result);
    //    }
    
    @PostMapping("/incidents")
    public ResponseEntity<IncidentDTO> createIncident(@Valid @RequestBody CreateNewIncidentDTO createNewIncidentDTO) throws
                                                                                                                     URISyntaxException {
        
        testService.getUserIdForCurrentUSer();
        
        IncidentDTO result = incidentService.createNewIncident(createNewIncidentDTO);
        return ResponseEntity.created(new URI("/api/incidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * PUT  /incidents : Updates an existing incident.
     *
     * @param incidentDTO the incidentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incidentDTO,
     * or with status 400 (Bad Request) if the incidentDTO is not valid,
     * or with status 500 (Internal Server Error) if the incidentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/incidents")
    @Timed
    public ResponseEntity<IncidentDTO> updateIncident(@Valid @RequestBody IncidentDTO incidentDTO) throws
                                                                                                   URISyntaxException {
        log.debug("REST request to update Incident : {}", incidentDTO);
//        if (incidentDTO.getId() == null) {
//            return createIncident(incidentDTO);
//        }
        IncidentDTO result = incidentService.save(incidentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incidentDTO.getId().toString()))
            .body(result);
    }
    
    /**
     * GET  /incidents : get all the incidents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of incidents in body
     */
    @GetMapping("/incidents")
    @Timed
    public List<IncidentDTO> getAllIncidents() {
        log.debug("REST request to get all Incidents");
        return incidentService.findAll();
    }
    
    /**
     * GET  /incidents/:id : get the "id" incident.
     *
     * @param id the id of the incidentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incidentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/incidents/{id}")
    @Timed
    public ResponseEntity<IncidentDTO> getIncident(@PathVariable Long id) {
        log.debug("REST request to get Incident : {}", id);
        IncidentDTO incidentDTO = incidentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(incidentDTO));
    }
    
    /**
     * DELETE  /incidents/:id : delete the "id" incident.
     *
     * @param id the id of the incidentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/incidents/{id}")
    @Timed
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        log.debug("REST request to delete Incident : {}", id);
        incidentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * SEARCH  /_search/incidents?query=:query : search for the incident corresponding
     * to the query.
     *
     * @param query the query of the incident search
     * @return the result of the search
     */
    @GetMapping("/_search/incidents")
    @Timed
    public List<IncidentDTO> searchIncidents(@RequestParam String query) {
        log.debug("REST request to search Incidents for query {}", query);
        return incidentService.search(query);
    }
    
}
