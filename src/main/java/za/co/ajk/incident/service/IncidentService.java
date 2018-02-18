package za.co.ajk.incident.service;

import java.util.List;

import za.co.ajk.incident.service.dto.CreateNewIncidentDTO;
import za.co.ajk.incident.service.dto.IncidentDTO;

/**
 * Service Interface for managing Incident.
 */
public interface IncidentService {
    
    /**
     * Create a new incident.
     * @param createNewIncidentDTO
     * @return
     */
    IncidentDTO createNewIncident(CreateNewIncidentDTO createNewIncidentDTO);
    
    /**
     * Save a incident.
     *
     * @param incidentDTO the entity to save
     * @return the persisted entity
     */
    IncidentDTO save(IncidentDTO incidentDTO);

    /**
     * Get all the incidents.
     *
     * @return the list of entities
     */
    List<IncidentDTO> findAll();

    /**
     * Get the "id" incident.
     *
     * @param id the id of the entity
     * @return the entity
     */
    IncidentDTO findOne(Long id);

    /**
     * Delete the "id" incident.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the incident corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<IncidentDTO> search(String query);
}
