package za.co.ajk.incident.service.impl;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.ajk.incident.domain.Company;
import za.co.ajk.incident.domain.Equipment;
import za.co.ajk.incident.repository.CompanyRepository;
import za.co.ajk.incident.repository.EquipmentRepository;
import za.co.ajk.incident.repository.search.EquipmentSearchRepository;
import za.co.ajk.incident.security.SecurityUtils;
import za.co.ajk.incident.service.EquipmentService;
import za.co.ajk.incident.service.dto.EquipmentDTO;
import za.co.ajk.incident.service.mapper.EquipmentMapper;
import za.co.ajk.incident.service.restio.RestTemplateService;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;


/**
 * Service Implementation for managing Equipment.
 */
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    
    private final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);
    
    private final EquipmentRepository equipmentRepository;
    
    private final CompanyRepository companyRepository;
    
    private final EquipmentMapper equipmentMapper;
    
    private final EquipmentSearchRepository equipmentSearchRepository;
    
    @Autowired
    private RestTemplateService restTemplateService;
    
    public EquipmentServiceImpl(EquipmentRepository equipmentRepository,
                                EquipmentMapper equipmentMapper,
                                EquipmentSearchRepository equipmentSearchRepository,
                                CompanyRepository companyRepository) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentMapper = equipmentMapper;
        this.equipmentSearchRepository = equipmentSearchRepository;
        this.companyRepository = companyRepository;
    }
    
    /**
     * Save a equipment.
     *
     * @param equipmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EquipmentDTO save(EquipmentDTO equipmentDTO) {
        log.debug("Request to save Equipment : {}", equipmentDTO);
        Equipment equipment = equipmentMapper.toEntity(equipmentDTO);
        equipment = equipmentRepository.save(equipment);
        EquipmentDTO result = equipmentMapper.toDto(equipment);
        equipmentSearchRepository.save(equipment);
        return result;
    }
    
    /**
     * Get all the equipment.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EquipmentDTO> findAll() {
        log.debug("Request to get all Equipment");
        return equipmentRepository.findAll().stream()
            .map(equipmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     * Get one equipment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EquipmentDTO findOne(Long id) {
        log.debug("Request to get Equipment : {}", id);
        Equipment equipment = equipmentRepository.findOne(id);
        return equipmentMapper.toDto(equipment);
    }
    
    /**
     * Delete the equipment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipment : {}", id);
        equipmentRepository.delete(id);
        equipmentSearchRepository.delete(id);
    }
    
    /**
     * Search for the equipment corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EquipmentDTO> search(String query) {
        log.debug("Request to search Equipment for query {}", query);
        return StreamSupport
            .stream(equipmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(equipmentMapper::toDto)
            .collect(Collectors.toList());
    }
    
    /**
     * This method will first try and retrieve the equipment from the local database. This indicates that the equipment was
     * involved in some incident before. If not found locally, it will call the Inventory MicroService which is the owner of all
     * equipment and load it into the local DB for future use.
     * Data is not replicated by default to all other services when loaded into the Inventory db.
     *
     * @param company
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Equipment getEquipmentByCompanyAndEquipmentId(Company company, Long equipmentId) {
        Equipment equipment = equipmentRepository.getEquipmentByCompanyAndEquipmentId(company, equipmentId.intValue());
        
        if (equipment == null) {
            JsonNode dto = restTemplateService.getEquipmentFromInventory(equipmentId);
            
            String operatorName = SecurityUtils.getCurrentUserLogin().isPresent() == true ?
                SecurityUtils.getCurrentUserLogin().get() :
                "Anonymous";

            Equipment newEquipment = new Equipment();
            newEquipment.setAddedBy(operatorName);
            newEquipment.setDateAdded(Instant.now());
            newEquipment.setEquipmentId(dto.get("equipmentId").asInt());
            
            Company comp = companyRepository.findOne(dto.get("companyId").longValue());
            newEquipment.setCompany(comp);
            
            equipmentRepository.save(newEquipment);
            
            equipment = newEquipment;
        }
        
        return equipment;
    }
}
