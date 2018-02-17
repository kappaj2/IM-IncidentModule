package za.co.ajk.incident.service.impl;

import za.co.ajk.incident.service.IncidentActivityService;
import za.co.ajk.incident.domain.IncidentActivity;
import za.co.ajk.incident.repository.IncidentActivityRepository;
import za.co.ajk.incident.repository.search.IncidentActivitySearchRepository;
import za.co.ajk.incident.service.dto.IncidentActivityDTO;
import za.co.ajk.incident.service.mapper.IncidentActivityMapper;
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
 * Service Implementation for managing IncidentActivity.
 */
@Service
@Transactional
public class IncidentActivityServiceImpl implements IncidentActivityService {

    private final Logger log = LoggerFactory.getLogger(IncidentActivityServiceImpl.class);

    private final IncidentActivityRepository incidentActivityRepository;

    private final IncidentActivityMapper incidentActivityMapper;

    private final IncidentActivitySearchRepository incidentActivitySearchRepository;

    public IncidentActivityServiceImpl(IncidentActivityRepository incidentActivityRepository, IncidentActivityMapper incidentActivityMapper, IncidentActivitySearchRepository incidentActivitySearchRepository) {
        this.incidentActivityRepository = incidentActivityRepository;
        this.incidentActivityMapper = incidentActivityMapper;
        this.incidentActivitySearchRepository = incidentActivitySearchRepository;
    }

    /**
     * Save a incidentActivity.
     *
     * @param incidentActivityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IncidentActivityDTO save(IncidentActivityDTO incidentActivityDTO) {
        log.debug("Request to save IncidentActivity : {}", incidentActivityDTO);
        IncidentActivity incidentActivity = incidentActivityMapper.toEntity(incidentActivityDTO);
        incidentActivity = incidentActivityRepository.save(incidentActivity);
        IncidentActivityDTO result = incidentActivityMapper.toDto(incidentActivity);
        incidentActivitySearchRepository.save(incidentActivity);
        return result;
    }

    /**
     * Get all the incidentActivities.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IncidentActivityDTO> findAll() {
        log.debug("Request to get all IncidentActivities");
        return incidentActivityRepository.findAll().stream()
            .map(incidentActivityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one incidentActivity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IncidentActivityDTO findOne(Long id) {
        log.debug("Request to get IncidentActivity : {}", id);
        IncidentActivity incidentActivity = incidentActivityRepository.findOne(id);
        return incidentActivityMapper.toDto(incidentActivity);
    }

    /**
     * Delete the incidentActivity by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IncidentActivity : {}", id);
        incidentActivityRepository.delete(id);
        incidentActivitySearchRepository.delete(id);
    }

    /**
     * Search for the incidentActivity corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IncidentActivityDTO> search(String query) {
        log.debug("Request to search IncidentActivities for query {}", query);
        return StreamSupport
            .stream(incidentActivitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(incidentActivityMapper::toDto)
            .collect(Collectors.toList());
    }
}
