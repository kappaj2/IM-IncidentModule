package za.co.ajk.incident.web.rest;

import za.co.ajk.incident.IncidentModuleApp;

import za.co.ajk.incident.domain.Incident;
import za.co.ajk.incident.repository.IncidentRepository;
import za.co.ajk.incident.service.IncidentService;
import za.co.ajk.incident.repository.search.IncidentSearchRepository;
import za.co.ajk.incident.service.dto.IncidentDTO;
import za.co.ajk.incident.service.mapper.IncidentMapper;
import za.co.ajk.incident.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static za.co.ajk.incident.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the IncidentResource REST controller.
 *
 * @see IncidentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IncidentModuleApp.class)
public class IncidentResourceIntTest {

    private static final Integer DEFAULT_INCIDENT_NUMBER = 1;
    private static final Integer UPDATED_INCIDENT_NUMBER = 2;

    private static final String DEFAULT_INCIDENT_PRIORITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_INCIDENT_PRIORITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_INCIDENT_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_INCIDENT_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_INCIDENT_HEADER = "AAAAAAAAAA";
    private static final String UPDATED_INCIDENT_HEADER = "BBBBBBBBBB";

    private static final String DEFAULT_INCIDENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_INCIDENT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_INCIDENT_STATUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_INCIDENT_STATUS_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Instant DEFAULT_DATE_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    private static final String DEFAULT_INDICENT_RESOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_INDICENT_RESOLUTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CLOSED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CLOSED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CLOSED_BY = 1;
    private static final Integer UPDATED_CLOSED_BY = 2;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private IncidentMapper incidentMapper;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private IncidentSearchRepository incidentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIncidentMockMvc;

    private Incident incident;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncidentResource incidentResource = new IncidentResource(incidentService);
        this.restIncidentMockMvc = MockMvcBuilders.standaloneSetup(incidentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incident createEntity(EntityManager em) {
        Incident incident = new Incident()
            .incidentNumber(DEFAULT_INCIDENT_NUMBER)
            .incidentPriorityCode(DEFAULT_INCIDENT_PRIORITY_CODE)
            .incidentTypeCode(DEFAULT_INCIDENT_TYPE_CODE)
            .incidentHeader(DEFAULT_INCIDENT_HEADER)
            .incidentDescription(DEFAULT_INCIDENT_DESCRIPTION)
            .incidentStatusCode(DEFAULT_INCIDENT_STATUS_CODE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .createdBy(DEFAULT_CREATED_BY)
            .dateLastUpdated(DEFAULT_DATE_LAST_UPDATED)
            .updatedBy(DEFAULT_UPDATED_BY)
            .indicentResolution(DEFAULT_INDICENT_RESOLUTION)
            .dateClosed(DEFAULT_DATE_CLOSED)
            .closedBy(DEFAULT_CLOSED_BY);
        return incident;
    }

    @Before
    public void initTest() {
        incidentSearchRepository.deleteAll();
        incident = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncident() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isCreated());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate + 1);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getIncidentNumber()).isEqualTo(DEFAULT_INCIDENT_NUMBER);
        assertThat(testIncident.getIncidentPriorityCode()).isEqualTo(DEFAULT_INCIDENT_PRIORITY_CODE);
        assertThat(testIncident.getIncidentTypeCode()).isEqualTo(DEFAULT_INCIDENT_TYPE_CODE);
        assertThat(testIncident.getIncidentHeader()).isEqualTo(DEFAULT_INCIDENT_HEADER);
        assertThat(testIncident.getIncidentDescription()).isEqualTo(DEFAULT_INCIDENT_DESCRIPTION);
        assertThat(testIncident.getIncidentStatusCode()).isEqualTo(DEFAULT_INCIDENT_STATUS_CODE);
        assertThat(testIncident.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testIncident.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testIncident.getDateLastUpdated()).isEqualTo(DEFAULT_DATE_LAST_UPDATED);
        assertThat(testIncident.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testIncident.getIndicentResolution()).isEqualTo(DEFAULT_INDICENT_RESOLUTION);
        assertThat(testIncident.getDateClosed()).isEqualTo(DEFAULT_DATE_CLOSED);
        assertThat(testIncident.getClosedBy()).isEqualTo(DEFAULT_CLOSED_BY);

        // Validate the Incident in Elasticsearch
        Incident incidentEs = incidentSearchRepository.findOne(testIncident.getId());
        assertThat(incidentEs).isEqualToIgnoringGivenFields(testIncident);
    }

    @Test
    @Transactional
    public void createIncidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident with an existing ID
        incident.setId(1L);
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIncidentNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setIncidentNumber(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncidentPriorityCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setIncidentPriorityCode(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncidentTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setIncidentTypeCode(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncidentHeaderIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setIncidentHeader(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncidentDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setIncidentDescription(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncidentStatusCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setIncidentStatusCode(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setDateCreated(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setCreatedBy(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateLastUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setDateLastUpdated(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setUpdatedBy(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncidents() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList
        restIncidentMockMvc.perform(get("/api/incidents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incident.getId().intValue())))
            .andExpect(jsonPath("$.[*].incidentNumber").value(hasItem(DEFAULT_INCIDENT_NUMBER)))
            .andExpect(jsonPath("$.[*].incidentPriorityCode").value(hasItem(DEFAULT_INCIDENT_PRIORITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].incidentTypeCode").value(hasItem(DEFAULT_INCIDENT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].incidentHeader").value(hasItem(DEFAULT_INCIDENT_HEADER.toString())))
            .andExpect(jsonPath("$.[*].incidentDescription").value(hasItem(DEFAULT_INCIDENT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].incidentStatusCode").value(hasItem(DEFAULT_INCIDENT_STATUS_CODE.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].dateLastUpdated").value(hasItem(DEFAULT_DATE_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].indicentResolution").value(hasItem(DEFAULT_INDICENT_RESOLUTION.toString())))
            .andExpect(jsonPath("$.[*].dateClosed").value(hasItem(DEFAULT_DATE_CLOSED.toString())))
            .andExpect(jsonPath("$.[*].closedBy").value(hasItem(DEFAULT_CLOSED_BY)));
    }

    @Test
    @Transactional
    public void getIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", incident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incident.getId().intValue()))
            .andExpect(jsonPath("$.incidentNumber").value(DEFAULT_INCIDENT_NUMBER))
            .andExpect(jsonPath("$.incidentPriorityCode").value(DEFAULT_INCIDENT_PRIORITY_CODE.toString()))
            .andExpect(jsonPath("$.incidentTypeCode").value(DEFAULT_INCIDENT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.incidentHeader").value(DEFAULT_INCIDENT_HEADER.toString()))
            .andExpect(jsonPath("$.incidentDescription").value(DEFAULT_INCIDENT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.incidentStatusCode").value(DEFAULT_INCIDENT_STATUS_CODE.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.dateLastUpdated").value(DEFAULT_DATE_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.indicentResolution").value(DEFAULT_INDICENT_RESOLUTION.toString()))
            .andExpect(jsonPath("$.dateClosed").value(DEFAULT_DATE_CLOSED.toString()))
            .andExpect(jsonPath("$.closedBy").value(DEFAULT_CLOSED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingIncident() throws Exception {
        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);
        incidentSearchRepository.save(incident);
        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Update the incident
        Incident updatedIncident = incidentRepository.findOne(incident.getId());
        // Disconnect from session so that the updates on updatedIncident are not directly saved in db
        em.detach(updatedIncident);
        updatedIncident
            .incidentNumber(UPDATED_INCIDENT_NUMBER)
            .incidentPriorityCode(UPDATED_INCIDENT_PRIORITY_CODE)
            .incidentTypeCode(UPDATED_INCIDENT_TYPE_CODE)
            .incidentHeader(UPDATED_INCIDENT_HEADER)
            .incidentDescription(UPDATED_INCIDENT_DESCRIPTION)
            .incidentStatusCode(UPDATED_INCIDENT_STATUS_CODE)
            .dateCreated(UPDATED_DATE_CREATED)
            .createdBy(UPDATED_CREATED_BY)
            .dateLastUpdated(UPDATED_DATE_LAST_UPDATED)
            .updatedBy(UPDATED_UPDATED_BY)
            .indicentResolution(UPDATED_INDICENT_RESOLUTION)
            .dateClosed(UPDATED_DATE_CLOSED)
            .closedBy(UPDATED_CLOSED_BY);
        IncidentDTO incidentDTO = incidentMapper.toDto(updatedIncident);

        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isOk());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getIncidentNumber()).isEqualTo(UPDATED_INCIDENT_NUMBER);
        assertThat(testIncident.getIncidentPriorityCode()).isEqualTo(UPDATED_INCIDENT_PRIORITY_CODE);
        assertThat(testIncident.getIncidentTypeCode()).isEqualTo(UPDATED_INCIDENT_TYPE_CODE);
        assertThat(testIncident.getIncidentHeader()).isEqualTo(UPDATED_INCIDENT_HEADER);
        assertThat(testIncident.getIncidentDescription()).isEqualTo(UPDATED_INCIDENT_DESCRIPTION);
        assertThat(testIncident.getIncidentStatusCode()).isEqualTo(UPDATED_INCIDENT_STATUS_CODE);
        assertThat(testIncident.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testIncident.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testIncident.getDateLastUpdated()).isEqualTo(UPDATED_DATE_LAST_UPDATED);
        assertThat(testIncident.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testIncident.getIndicentResolution()).isEqualTo(UPDATED_INDICENT_RESOLUTION);
        assertThat(testIncident.getDateClosed()).isEqualTo(UPDATED_DATE_CLOSED);
        assertThat(testIncident.getClosedBy()).isEqualTo(UPDATED_CLOSED_BY);

        // Validate the Incident in Elasticsearch
        Incident incidentEs = incidentSearchRepository.findOne(testIncident.getId());
        assertThat(incidentEs).isEqualToIgnoringGivenFields(testIncident);
    }

    @Test
    @Transactional
    public void updateNonExistingIncident() throws Exception {
        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Create the Incident
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isCreated());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);
        incidentSearchRepository.save(incident);
        int databaseSizeBeforeDelete = incidentRepository.findAll().size();

        // Get the incident
        restIncidentMockMvc.perform(delete("/api/incidents/{id}", incident.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean incidentExistsInEs = incidentSearchRepository.exists(incident.getId());
        assertThat(incidentExistsInEs).isFalse();

        // Validate the database is empty
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);
        incidentSearchRepository.save(incident);

        // Search the incident
        restIncidentMockMvc.perform(get("/api/_search/incidents?query=id:" + incident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incident.getId().intValue())))
            .andExpect(jsonPath("$.[*].incidentNumber").value(hasItem(DEFAULT_INCIDENT_NUMBER)))
            .andExpect(jsonPath("$.[*].incidentPriorityCode").value(hasItem(DEFAULT_INCIDENT_PRIORITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].incidentTypeCode").value(hasItem(DEFAULT_INCIDENT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].incidentHeader").value(hasItem(DEFAULT_INCIDENT_HEADER.toString())))
            .andExpect(jsonPath("$.[*].incidentDescription").value(hasItem(DEFAULT_INCIDENT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].incidentStatusCode").value(hasItem(DEFAULT_INCIDENT_STATUS_CODE.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].dateLastUpdated").value(hasItem(DEFAULT_DATE_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].indicentResolution").value(hasItem(DEFAULT_INDICENT_RESOLUTION.toString())))
            .andExpect(jsonPath("$.[*].dateClosed").value(hasItem(DEFAULT_DATE_CLOSED.toString())))
            .andExpect(jsonPath("$.[*].closedBy").value(hasItem(DEFAULT_CLOSED_BY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incident.class);
        Incident incident1 = new Incident();
        incident1.setId(1L);
        Incident incident2 = new Incident();
        incident2.setId(incident1.getId());
        assertThat(incident1).isEqualTo(incident2);
        incident2.setId(2L);
        assertThat(incident1).isNotEqualTo(incident2);
        incident1.setId(null);
        assertThat(incident1).isNotEqualTo(incident2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentDTO.class);
        IncidentDTO incidentDTO1 = new IncidentDTO();
        incidentDTO1.setId(1L);
        IncidentDTO incidentDTO2 = new IncidentDTO();
        assertThat(incidentDTO1).isNotEqualTo(incidentDTO2);
        incidentDTO2.setId(incidentDTO1.getId());
        assertThat(incidentDTO1).isEqualTo(incidentDTO2);
        incidentDTO2.setId(2L);
        assertThat(incidentDTO1).isNotEqualTo(incidentDTO2);
        incidentDTO1.setId(null);
        assertThat(incidentDTO1).isNotEqualTo(incidentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(incidentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(incidentMapper.fromId(null)).isNull();
    }
}
