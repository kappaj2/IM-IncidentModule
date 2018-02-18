package za.co.ajk.incident.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import za.co.ajk.incident.domain.Incident;


/**
 * Spring Data JPA repository for the Incident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    
    /**
     * Return the last incident number for the company.
     * @param companyId
     * @return
     */
    @Query("SELECT max(i.incidentNumber) FROM Incident i INNER JOIN i.company c WHERE c.id = ?1")
    Integer getMaxIncidentNumberForCompany(Long companyId);
    
}
