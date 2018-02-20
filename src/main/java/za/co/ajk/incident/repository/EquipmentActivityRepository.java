package za.co.ajk.incident.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.ajk.incident.domain.Equipment;
import za.co.ajk.incident.domain.EquipmentActivity;
import za.co.ajk.incident.domain.IncidentActivity;


/**
 * Spring Data JPA repository for the EquipmentActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentActivityRepository extends JpaRepository<EquipmentActivity, Long> {
    
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
