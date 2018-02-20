package za.co.ajk.incident.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.ajk.incident.domain.Company;
import za.co.ajk.incident.domain.Equipment;


/**
 * Spring Data JPA repository for the Equipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    
    
    //     *
    //     * @param incidentId
    //     * @return
    //     */
    //    @Query("SELECT max(i.eventNumber) FROM EquipmentActivity i WHERE i.id = ?1")
    //    Integer getMaxEquipmentEventNumberForIncident(Long incidentId);
    //
    //    /**
    //     * Find a list of incident activity entries for the incident
    //     * @param incident
    //     * @return
    //     */
    //    List<IncidentActivity> findIncidentActivitiesByIncident(Incident incident);
    
    Equipment getEquipmentByCompanyAndEquipmentId(Company company, Integer id);

}
