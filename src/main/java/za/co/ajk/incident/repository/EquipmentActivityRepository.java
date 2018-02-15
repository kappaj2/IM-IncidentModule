package za.co.ajk.incident.repository;

import za.co.ajk.incident.domain.EquipmentActivity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EquipmentActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentActivityRepository extends JpaRepository<EquipmentActivity, Long> {

}
