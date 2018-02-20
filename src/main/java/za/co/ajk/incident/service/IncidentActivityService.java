package za.co.ajk.incident.service;

import java.util.List;

import za.co.ajk.incident.domain.Incident;
import za.co.ajk.incident.service.dto.IncidentActivityDTO;

/**
 * Service Interface for managing IncidentActivity.
 */
public interface IncidentActivityService {

    /**
     * Save a incidentActivity.
     *
     * @param incidentActivityDTO the entity to save
     * @return the persisted entity
     */
    IncidentActivityDTO save(IncidentActivityDTO incidentActivityDTO);

    /**
     * Get all the incidentActivities.
     *
     * @return the list of entities
     */
    List<IncidentActivityDTO> findAll();

    /**
     * Get the "id" incidentActivity.
     *
     * @param id the id of the entity
     * @return the entity
     */
    IncidentActivityDTO findOne(Long id);

    /**
     * Delete the "id" incidentActivity.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the incidentActivity corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<IncidentActivityDTO> search(String query);
    
    /**
     * Find incident activities for an incident.
     * @param incident
     * @return
     */
    List<IncidentActivityDTO> findIncidentActivitiesByIncident(Incident incident);
}
