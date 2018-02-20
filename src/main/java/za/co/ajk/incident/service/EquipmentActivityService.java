package za.co.ajk.incident.service;

import java.util.List;

import za.co.ajk.incident.domain.Equipment;
import za.co.ajk.incident.domain.EquipmentActivity;
import za.co.ajk.incident.domain.IncidentActivity;
import za.co.ajk.incident.service.dto.EquipmentActivityDTO;

/**
 * Service Interface for managing EquipmentActivity.
 */
public interface EquipmentActivityService {

    /**
     * Save a equipmentActivity.
     *
     * @param equipmentActivityDTO the entity to save
     * @return the persisted entity
     */
    EquipmentActivityDTO save(EquipmentActivityDTO equipmentActivityDTO);

    /**
     * Get all the equipmentActivities.
     *
     * @return the list of entities
     */
    List<EquipmentActivityDTO> findAll();

    /**
     * Get the "id" equipmentActivity.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EquipmentActivityDTO findOne(Long id);

    /**
     * Delete the "id" equipmentActivity.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the equipmentActivity corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<EquipmentActivityDTO> search(String query);
    
    /**
     * Search for EquipmentActivity using the IncidentActivity and equipment.
     * @param equipment
     * @param incidentActivity
     * @return
     */
    List<EquipmentActivity> findEquipmentActivitiesByEquipmentAndIncidentActivity(Equipment equipment, IncidentActivity incidentActivity);
    
    /**
     * Find all the equipment involved in the incident activity.
     * @param incidentActivity
     * @return
     */
    List<EquipmentActivity> findEquipmentActivitiesByIncidentActivity(IncidentActivity incidentActivity);
}
