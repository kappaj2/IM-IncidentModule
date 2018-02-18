package za.co.ajk.incident.service.impl;

import za.co.ajk.incident.service.IncidentService;
import za.co.ajk.incident.domain.Incident;
import za.co.ajk.incident.repository.IncidentRepository;
import za.co.ajk.incident.repository.search.IncidentSearchRepository;
import za.co.ajk.incident.service.dto.IncidentDTO;
import za.co.ajk.incident.service.mapper.IncidentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

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

    public IncidentServiceImpl(IncidentRepository incidentRepository, IncidentMapper incidentMapper, IncidentSearchRepository incidentSearchRepository) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
        this.incidentSearchRepository = incidentSearchRepository;
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
