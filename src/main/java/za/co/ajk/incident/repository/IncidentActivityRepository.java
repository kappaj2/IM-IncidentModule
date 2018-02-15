package za.co.ajk.incident.repository;

import za.co.ajk.incident.domain.IncidentActivity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IncidentActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidentActivityRepository extends JpaRepository<IncidentActivity, Long> {

}
