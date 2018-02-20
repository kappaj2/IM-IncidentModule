package za.co.ajk.incident.service.impl;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import za.co.ajk.incident.domain.Company;
import za.co.ajk.incident.domain.Equipment;
import za.co.ajk.incident.domain.EquipmentActivity;
import za.co.ajk.incident.domain.Incident;
import za.co.ajk.incident.domain.IncidentActivity;
import za.co.ajk.incident.enums.EventType;
import za.co.ajk.incident.enums.IncidentStatusType;
import za.co.ajk.incident.repository.CompanyRepository;
import za.co.ajk.incident.repository.EquipmentActivityRepository;
import za.co.ajk.incident.repository.IncidentActivityRepository;
import za.co.ajk.incident.repository.IncidentRepository;
import za.co.ajk.incident.repository.search.IncidentSearchRepository;
import za.co.ajk.incident.service.EquipmentService;
import za.co.ajk.incident.service.IncidentService;
import za.co.ajk.incident.service.dto.CreateNewIncidentDTO;
import za.co.ajk.incident.service.dto.IncidentDTO;
import za.co.ajk.incident.service.mapper.EquipmentMapper;
import za.co.ajk.incident.service.mapper.IncidentMapper;
import za.co.ajk.incident.web.rest.errors.InvalidEquipmentIdReceivedException;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;


/**
 * Service Implementation for managing Incident.
 */
@Service
@Transactional
public class IncidentServiceImpl implements IncidentService {
    
    private final Logger log = LoggerFactory.getLogger(IncidentServiceImpl.class);
    
    private final IncidentRepository incidentRepository;
    
    private final IncidentMapper incidentMapper;
    
    private final IncidentSearchRepository incidentSearchRepository;
    
    @Autowired
    private IncidentActivityRepository incidentActivityRepository;
    
    @Autowired
    private EquipmentActivityRepository equipmentActivityRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private EquipmentService equipmentService;
    
    @Autowired
    private EquipmentMapper equipmentMapper;
    
    public IncidentServiceImpl(IncidentRepository incidentRepository,
                               IncidentMapper incidentMapper,
                               IncidentSearchRepository incidentSearchRepository) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
        this.incidentSearchRepository = incidentSearchRepository;
    }
    
    /**
     * Create a new incident for the specific company. Retrieve the last incident number for this company and increment. Each company has a sequence
     * of incidents.
     *
     * @param createNewIncidentDTO
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public IncidentDTO createNewIncident(CreateNewIncidentDTO createNewIncidentDTO) {
        
        // Find the company using the company id provided.
        Company company = companyRepository.getOne(createNewIncidentDTO.getCompanyId());
        Long companyId = company.getId();
        
        Integer lastIncidentNumber = incidentRepository.getMaxIncidentNumberForCompany(companyId);
        
        lastIncidentNumber = lastIncidentNumber == null ? 1 : ++lastIncidentNumber;
        
        Incident incident = new Incident();
        incident.setIncidentNumber(lastIncidentNumber);
        incident.setCompany(company);
        incident.setCreatedBy(createNewIncidentDTO.getOperator());
        incident.setDateCreated(Instant.now());
        incident.setIncidentDescription(createNewIncidentDTO.getIncidentDescription());
        incident.setIncidentHeader(createNewIncidentDTO.getIncidentHeader());
        incident.setIncidentPriorityCode(createNewIncidentDTO.getIncidentPriorityCode());
        incident.setIncidentStatusCode(createNewIncidentDTO.getIncidentStatusCode());
        incident.setIncidentTypeCode(createNewIncidentDTO.getIncidentTypeCode());
        incident.setDateUpdated(Instant.now());
        incident.setUpdatedBy(createNewIncidentDTO.getOperator());
        
        incidentRepository.save(incident);

        /*
            Add the incident event start activity
         */
        IncidentActivity incidentActivity = createIncidentEventActivity(incident, createNewIncidentDTO);
        
        /*
            Link all the equipment that is involved in the incident.
         */
        createIncidentEquipmentActivity(createNewIncidentDTO, incidentActivity, company);
        
        /*
            Return the incident DTO. This will not include the activity, equipment, etc.
            These need to be retrieve via their respective rest controllers.
         */
        IncidentDTO result = incidentMapper.toDto(incident);
        incidentSearchRepository.save(incident);
        return result;
    }
    
    /**
     * Create incident activity for the specific incident.
     * @param incident
     * @param createNewIncidentDTO
     * @Return IncidentActivity
     */
    private IncidentActivity createIncidentEventActivity(Incident incident,
                                             CreateNewIncidentDTO createNewIncidentDTO){

        Integer incidentActivityEventNumber = incidentActivityRepository.getMaxIncidentEventNumberForIncident(incident.getId());
    
        incidentActivityEventNumber = incidentActivityEventNumber == null ? 1 : ++incidentActivityEventNumber;
    
        IncidentActivity incidentActivity = new IncidentActivity();
        incidentActivity.setCreatedBy(createNewIncidentDTO.getOperator());
        incidentActivity.setDateCreated(Instant.now());
        incidentActivity.setEventTypeCode(EventType.OPEN_INCIDENT.getEventTypeCode());
        incidentActivity.setUpdatedStatusCode(IncidentStatusType.OPENED.getIncidentStatusCode());
        incidentActivity.setIncident(incident);
        incidentActivity.setIncidentComment("Incident created");
        incidentActivity.setDateUpdated(Instant.now());
        incidentActivity.setUpdatedBy(createNewIncidentDTO.getOperator());
        incidentActivity.setEventNumber(incidentActivityEventNumber);
        incidentActivity.setUpdatedPriorityCode(createNewIncidentDTO.getIncidentPriorityCode());
        incidentActivityRepository.save(incidentActivity);
        
        return incidentActivity;
    }
    
    /**
     * The incident activity will be the link for equipment. This will allow equipment to be put on loan, replaced, etc.
     * @param incidentActivity
     * @param createNewIncidentDTO
     */
    private void createIncidentEquipmentActivity(CreateNewIncidentDTO createNewIncidentDTO,
                                                 IncidentActivity incidentActivity,
                                                 Company company){
        
        List<CreateNewIncidentDTO.Equipment> equipList = createNewIncidentDTO.getEquipmentList();
    
        equipList.stream().forEach(equip ->{
    
            Equipment entity = equipmentService.getEquipmentByCompanyAndEquipmentId(company, equip.getEquipmentId());
            
            if(entity == null){
                log.info("Equipment not found in local DB");
                throw new InvalidEquipmentIdReceivedException();
            }
            
            EquipmentActivity equipmentActivity = new EquipmentActivity();
            equipmentActivity.setEquipment(entity);
            equipmentActivity.setIncidentActivity(incidentActivity);
            equipmentActivity.setActivityComment(equip.getEquipmentComment());
            equipmentActivity.setDateCreated(Instant.now());
            equipmentActivity.setOnLoan(equip.isOnLoan());
            equipmentActivity.setReplacement(equip.isReplacement());
            equipmentActivity.setCreatedBy(createNewIncidentDTO.getOperator());
            equipmentActivity.setEquipmentActionCode(equip.getEquipmentActionCode());
            
            equipmentActivityRepository.save(equipmentActivity);
        });
    }
    
    /**
     * Save a incident.
     *
     * @param incidentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IncidentDTO save(IncidentDTO incidentDTO) {
        log.debug("Request to save Incident : {}", incidentDTO);
        Incident incident = incidentMapper.toEntity(incidentDTO);
        incident = incidentRepository.save(incident);
        IncidentDTO result = incidentMapper.toDto(incident);
        incidentSearchRepository.save(incident);
        return result;
    }
    
    /**
     * Get all the incidents.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IncidentDTO> findAll() {
        log.debug("Request to get all Incidents");
        return incidentRepository.findAll().stream()
            .map(incidentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     * Get one incident by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IncidentDTO findOne(Long id) {
        log.debug("Request to get Incident : {}", id);
        Incident incident = incidentRepository.findOne(id);
        return incidentMapper.toDto(incident);
    }
    
    /**
     * Delete the incident by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Incident : {}", id);
        incidentRepository.delete(id);
        incidentSearchRepository.delete(id);
    }
    
    /**
     * Search for the incident corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IncidentDTO> search(String query) {
        log.debug("Request to search Incidents for query {}", query);
        return StreamSupport
            .stream(incidentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(incidentMapper::toDto)
            .collect(Collectors.toList());
    }
}
