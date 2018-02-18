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
import org.springframework.transaction.annotation.Transactional;

import za.co.ajk.incident.domain.Company;
import za.co.ajk.incident.domain.Incident;
import za.co.ajk.incident.domain.IncidentActivity;
import za.co.ajk.incident.enums.EventType;
import za.co.ajk.incident.enums.IncidentStatusType;
import za.co.ajk.incident.repository.CompanyRepository;
import za.co.ajk.incident.repository.CountryRepository;
import za.co.ajk.incident.repository.IncidentActivityRepository;
import za.co.ajk.incident.repository.IncidentRepository;
import za.co.ajk.incident.repository.RegionRepository;
import za.co.ajk.incident.repository.search.IncidentSearchRepository;
import za.co.ajk.incident.security.SecurityUtils;
import za.co.ajk.incident.service.CountryService;
import za.co.ajk.incident.service.IncidentService;
import za.co.ajk.incident.service.dto.CreateNewIncidentDTO;
import za.co.ajk.incident.service.dto.IncidentDTO;
import za.co.ajk.incident.service.mapper.IncidentMapper;

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
    private CountryService countryService;
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private RegionRepository regionRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
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
    public IncidentDTO createNewIncident(CreateNewIncidentDTO createNewIncidentDTO) {
        
        // Find the company using the company id provided.
        Company company = companyRepository.getOne(createNewIncidentDTO.getCompanyId());
        Long companyId = company.getId();
        
        Integer lastIncidentNumber = incidentRepository.getMaxIncidentNumberForCompany(companyId);
        
        lastIncidentNumber = lastIncidentNumber == null ? 1 : ++lastIncidentNumber;
        
        Incident incident = new Incident();
        incident.setIncidentNumber(lastIncidentNumber);
        incident.setCompany(company);
        incident.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
        incident.setDateCreated(Instant.now());
        incident.setIncidentDescription(createNewIncidentDTO.getIncidentDescription());
        incident.setIncidentHeader(createNewIncidentDTO.getIncidentHeader());
        incident.setIncidentPriorityCode(createNewIncidentDTO.getIncidentPriorityCode());
        incident.setIncidentStatusCode(createNewIncidentDTO.getIncidentStatusCode());
        incident.setIncidentTypeCode(createNewIncidentDTO.getIncidentTypeCode());
        incident.setDateUpdated(Instant.now());
        incident.setUpdatedBy(SecurityUtils.getCurrentUserLogin().get());
        
        /*
            Create the incident activity entry.
         */
        Integer incidentActivityEventNumber = incidentActivityRepository.getMaxIncidentEventNumberForIncident(incident.getId());
    
        incidentActivityEventNumber = incidentActivityEventNumber == null ? 1 : ++incidentActivityEventNumber;
        
        IncidentActivity incidentActivity = new IncidentActivity();
        incidentActivity.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
        incidentActivity.setDateCreated(Instant.now());
        incidentActivity.setEventTypeCode(EventType.OPEN_INCIDENT.getEventTypeCode());
        incidentActivity.setUpdatedStatusCode(IncidentStatusType.OPENED.getIncidentStatusCode());
        incidentActivity.setIncident(incident);
        incidentActivity.setIncidentComment("Incident created");
        incidentActivity.setDateUpdated(Instant.now());
        incidentActivity.setUpdatedBy(SecurityUtils.getCurrentUserLogin().get());
        incidentActivity.setEventNumber(incidentActivityEventNumber);
        incidentActivity.setUpdatedPriorityCode(createNewIncidentDTO.getIncidentPriorityCode());
        
        incidentRepository.save(incident);
        incidentActivityRepository.save(incidentActivity);
        
        IncidentDTO result = incidentMapper.toDto(incident);
        incidentSearchRepository.save(incident);
        return result;
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
