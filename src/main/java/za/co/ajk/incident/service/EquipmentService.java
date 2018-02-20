package za.co.ajk.incident.service;

import java.util.List;

import za.co.ajk.incident.domain.Company;
import za.co.ajk.incident.domain.Equipment;
import za.co.ajk.incident.service.dto.EquipmentDTO;

/**
 * Service Interface for managing Equipment.
 */
public interface EquipmentService {

    /**
     * Save a equipment.
     *
     * @param equipmentDTO the entity to save
     * @return the persisted entity
     */
    EquipmentDTO save(EquipmentDTO equipmentDTO);

    /**
     * Get all the equipment.
     *
     * @return the list of entities
     */
    List<EquipmentDTO> findAll();

    /**
     * Get the "id" equipment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EquipmentDTO findOne(Long id);

    /**
     * Delete the "id" equipment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the equipment corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<EquipmentDTO> search(String query);
    
    /**
     * Search for a piece of equipment using the provided company and equipment id.
     * @param company
     * @param id
     * @return
     */
    Equipment getEquipmentByCompanyAndEquipmentId(Company company, Long id);
}
