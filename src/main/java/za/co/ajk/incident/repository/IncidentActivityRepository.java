package za.co.ajk.incident.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import za.co.ajk.incident.domain.IncidentActivity;


/**
 * Spring Data JPA repository for the IncidentActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidentActivityRepository extends JpaRepository<IncidentActivity, Long> {
    
    
    /**
     * Return the last incident activity number for the incident.
     * @param incidentId
     * @return
     */
    @Query("SELECT max(i.eventNumber) FROM IncidentActivity i WHERE i.id = ?1")
    Integer getMaxIncidentEventNumberForIncident(Long incidentId);
}
