package za.co.ajk.incident.web.rest;

import za.co.ajk.incident.IncidentModuleApp;

import za.co.ajk.incident.domain.IncidentActivity;
import za.co.ajk.incident.repository.IncidentActivityRepository;
import za.co.ajk.incident.service.IncidentActivityService;
import za.co.ajk.incident.repository.search.IncidentActivitySearchRepository;
import za.co.ajk.incident.service.dto.IncidentActivityDTO;
import za.co.ajk.incident.service.mapper.IncidentActivityMapper;
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
 * Test class for the IncidentActivityResource REST controller.
 *
 * @see IncidentActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IncidentModuleApp.class)
public class IncidentActivityResourceIntTest {

    private static final String DEFAULT_EVENT_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPE_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EVENT_NUMBER = 1;
    private static final Integer UPDATED_EVENT_NUMBER = 2;

    private static final String DEFAULT_UPDATED_PRIORITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_PRIORITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_STATUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_STATUS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_INCIDENT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_INCIDENT_COMMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Instant DEFAULT_DATE_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private IncidentActivityRepository incidentActivityRepository;

    @Autowired
    private IncidentActivityMapper incidentActivityMapper;

    @Autowired
    private IncidentActivityService incidentActivityService;

    @Autowired
    private IncidentActivitySearchRepository incidentActivitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIncidentActivityMockMvc;

    private IncidentActivity incidentActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncidentActivityResource incidentActivityResource = new IncidentActivityResource(incidentActivityService);
        this.restIncidentActivityMockMvc = MockMvcBuilders.standaloneSetup(incidentActivityResource)
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
    public static IncidentActivity createEntity(EntityManager em) {
        IncidentActivity incidentActivity = new IncidentActivity()
            .eventTypeCode(DEFAULT_EVENT_TYPE_CODE)
            .eventNumber(DEFAULT_EVENT_NUMBER)
            .updatedPriorityCode(DEFAULT_UPDATED_PRIORITY_CODE)
            .updatedStatusCode(DEFAULT_UPDATED_STATUS_CODE)
            .incidentComment(DEFAULT_INCIDENT_COMMENT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .createdBy(DEFAULT_CREATED_BY)
            .dateLastUpdated(DEFAULT_DATE_LAST_UPDATED)
            .updatedBy(DEFAULT_UPDATED_BY);
        return incidentActivity;
    }

    @Before
    public void initTest() {
        incidentActivitySearchRepository.deleteAll();
        incidentActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncidentActivity() throws Exception {
        int databaseSizeBeforeCreate = incidentActivityRepository.findAll().size();

        // Create the IncidentActivity
        IncidentActivityDTO incidentActivityDTO = incidentActivityMapper.toDto(incidentActivity);
        restIncidentActivityMockMvc.perform(post("/api/incident-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the IncidentActivity in the database
        List<IncidentActivity> incidentActivityList = incidentActivityRepository.findAll();
        assertThat(incidentActivityList).hasSize(databaseSizeBeforeCreate + 1);
        IncidentActivity testIncidentActivity = incidentActivityList.get(incidentActivityList.size() - 1);
        assertThat(testIncidentActivity.getEventTypeCode()).isEqualTo(DEFAULT_EVENT_TYPE_CODE);
        assertThat(testIncidentActivity.getEventNumber()).isEqualTo(DEFAULT_EVENT_NUMBER);
        assertThat(testIncidentActivity.getUpdatedPriorityCode()).isEqualTo(DEFAULT_UPDATED_PRIORITY_CODE);
        assertThat(testIncidentActivity.getUpdatedStatusCode()).isEqualTo(DEFAULT_UPDATED_STATUS_CODE);
        assertThat(testIncidentActivity.getIncidentComment()).isEqualTo(DEFAULT_INCIDENT_COMMENT);
        assertThat(testIncidentActivity.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testIncidentActivity.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testIncidentActivity.getDateLastUpdated()).isEqualTo(DEFAULT_DATE_LAST_UPDATED);
        assertThat(testIncidentActivity.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the IncidentActivity in Elasticsearch
        IncidentActivity incidentActivityEs = incidentActivitySearchRepository.findOne(testIncidentActivity.getId());
        assertThat(incidentActivityEs).isEqualToIgnoringGivenFields(testIncidentActivity);
    }

    @Test
    @Transactional
    public void createIncidentActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidentActivityRepository.findAll().size();

        // Create the IncidentActivity with an existing ID
        incidentActivity.setId(1L);
        IncidentActivityDTO incidentActivityDTO = incidentActivityMapper.toDto(incidentActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentActivityMockMvc.perform(post("/api/incident-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncidentActivity in the database
        List<IncidentActivity> incidentActivityList = incidentActivityRepository.findAll();
        assertThat(incidentActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEventTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentActivityRepository.findAll().size();
        // set the field null
        incidentActivity.setEventTypeCode(null);

        // Create the IncidentActivity, which fails.
        IncidentActivityDTO incidentActivityDTO = incidentActivityMapper.toDto(incidentActivity);

        restIncidentActivityMockMvc.perform(post("/api/incident-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentActivityDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentActivity> incidentActivityList = incidentActivityRepository.findAll();
        assertThat(incidentActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEventNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentActivityRepository.findAll().size();
        // set the field null
        incidentActivity.setEventNumber(null);

        // Create the IncidentActivity, which fails.
        IncidentActivityDTO incidentActivityDTO = incidentActivityMapper.toDto(incidentActivity);

        restIncidentActivityMockMvc.perform(post("/api/incident-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentActivityDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentActivity> incidentActivityList = incidentActivityRepository.findAll();
        assertThat(incidentActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedPriorityCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentActivityRepository.findAll().size();
        // set the field null
        incidentActivity.setUpdatedPriorityCode(null);

        // Create the IncidentActivity, which fails.
        IncidentActivityDTO incidentActivityDTO = incidentActivityMapper.toDto(incidentActivity);

        restIncidentActivityMockMvc.perform(post("/api/incident-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentActivityDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentActivity> incidentActivityList = incidentActivityRepository.findAll();
        assertThat(incidentActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedStatusCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentActivityRepository.findAll().size();
        // set the field null
        incidentActivity.setUpdatedStatusCode(null);

        // Create the IncidentActivity, which fails.
        IncidentActivityDTO incidentActivityDTO = incidentActivityMapper.toDto(incidentActivity);

        restIncidentActivityMockMvc.perform(post("/api/incident-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentActivityDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentActivity> incidentActivityList = incidentActivityRepository.findAll();
        assertThat(incidentActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncidentCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentActivityRepository.findAll().size();
        // set the field null
        incidentActivity.setIncidentComment(null);

        // Create the IncidentActivity, which fails.
        IncidentActivityDTO incidentActivityDTO = incidentActivityMapper.toDto(incidentActivity);

        restIncidentActivityMockMvc.perform(post("/api/incident-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentActivityDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentActivity> incidentActivityList = incidentActivityRepository.findAll();
        assertThat(incidentActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncidentActivities() throws Exception {
        // Initialize the database
        incidentActivityRepository.saveAndFlush(incidentActivity);

        // Get all the incidentActivityList
        restIncidentActivityMockMvc.perform(get("/api/incident-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidentActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventTypeCode").value(hasItem(DEFAULT_EVENT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].eventNumber").value(hasItem(DEFAULT_EVENT_NUMBER)))
            .andExpect(jsonPath("$.[*].updatedPriorityCode").value(hasItem(DEFAULT_UPDATED_PRIORITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].updatedStatusCode").value(hasItem(DEFAULT_UPDATED_STATUS_CODE.toString())))
            .andExpect(jsonPath("$.[*].incidentComment").value(hasItem(DEFAULT_INCIDENT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].dateLastUpdated").value(hasItem(DEFAULT_DATE_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getIncidentActivity() throws Exception {
        // Initialize the database
        incidentActivityRepository.saveAndFlush(incidentActivity);

        // Get the incidentActivity
        restIncidentActivityMockMvc.perform(get("/api/incident-activities/{id}", incidentActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incidentActivity.getId().intValue()))
            .andExpect(jsonPath("$.eventTypeCode").value(DEFAULT_EVENT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.eventNumber").value(DEFAULT_EVENT_NUMBER))
            .andExpect(jsonPath("$.updatedPriorityCode").value(DEFAULT_UPDATED_PRIORITY_CODE.toString()))
            .andExpect(jsonPath("$.updatedStatusCode").value(DEFAULT_UPDATED_STATUS_CODE.toString()))
            .andExpect(jsonPath("$.incidentComment").value(DEFAULT_INCIDENT_COMMENT.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.dateLastUpdated").value(DEFAULT_DATE_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIncidentActivity() throws Exception {
        // Get the incidentActivity
        restIncidentActivityMockMvc.perform(get("/api/incident-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncidentActivity() throws Exception {
        // Initialize the database
        incidentActivityRepository.saveAndFlush(incidentActivity);
        incidentActivitySearchRepository.save(incidentActivity);
        int databaseSizeBeforeUpdate = incidentActivityRepository.findAll().size();

        // Update the incidentActivity
        IncidentActivity updatedIncidentActivity = incidentActivityRepository.findOne(incidentActivity.getId());
        // Disconnect from session so that the updates on updatedIncidentActivity are not directly saved in db
        em.detach(updatedIncidentActivity);
        updatedIncidentActivity
            .eventTypeCode(UPDATED_EVENT_TYPE_CODE)
            .eventNumber(UPDATED_EVENT_NUMBER)
            .updatedPriorityCode(UPDATED_UPDATED_PRIORITY_CODE)
            .updatedStatusCode(UPDATED_UPDATED_STATUS_CODE)
            .incidentComment(UPDATED_INCIDENT_COMMENT)
            .dateCreated(UPDATED_DATE_CREATED)
            .createdBy(UPDATED_CREATED_BY)
            .dateLastUpdated(UPDATED_DATE_LAST_UPDATED)
            .updatedBy(UPDATED_UPDATED_BY);
        IncidentActivityDTO incidentActivityDTO = incidentActivityMapper.toDto(updatedIncidentActivity);

        restIncidentActivityMockMvc.perform(put("/api/incident-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentActivityDTO)))
            .andExpect(status().isOk());

        // Validate the IncidentActivity in the database
        List<IncidentActivity> incidentActivityList = incidentActivityRepository.findAll();
        assertThat(incidentActivityList).hasSize(databaseSizeBeforeUpdate);
        IncidentActivity testIncidentActivity = incidentActivityList.get(incidentActivityList.size() - 1);
        assertThat(testIncidentActivity.getEventTypeCode()).isEqualTo(UPDATED_EVENT_TYPE_CODE);
        assertThat(testIncidentActivity.getEventNumber()).isEqualTo(UPDATED_EVENT_NUMBER);
        assertThat(testIncidentActivity.getUpdatedPriorityCode()).isEqualTo(UPDATED_UPDATED_PRIORITY_CODE);
        assertThat(testIncidentActivity.getUpdatedStatusCode()).isEqualTo(UPDATED_UPDATED_STATUS_CODE);
        assertThat(testIncidentActivity.getIncidentComment()).isEqualTo(UPDATED_INCIDENT_COMMENT);
        assertThat(testIncidentActivity.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testIncidentActivity.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testIncidentActivity.getDateLastUpdated()).isEqualTo(UPDATED_DATE_LAST_UPDATED);
        assertThat(testIncidentActivity.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the IncidentActivity in Elasticsearch
        IncidentActivity incidentActivityEs = incidentActivitySearchRepository.findOne(testIncidentActivity.getId());
        assertThat(incidentActivityEs).isEqualToIgnoringGivenFields(testIncidentActivity);
    }

    @Test
    @Transactional
    public void updateNonExistingIncidentActivity() throws Exception {
        int databaseSizeBeforeUpdate = incidentActivityRepository.findAll().size();

        // Create the IncidentActivity
        IncidentActivityDTO incidentActivityDTO = incidentActivityMapper.toDto(incidentActivity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIncidentActivityMockMvc.perform(put("/api/incident-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the IncidentActivity in the database
        List<IncidentActivity> incidentActivityList = incidentActivityRepository.findAll();
        assertThat(incidentActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIncidentActivity() throws Exception {
        // Initialize the database
        incidentActivityRepository.saveAndFlush(incidentActivity);
        incidentActivitySearchRepository.save(incidentActivity);
        int databaseSizeBeforeDelete = incidentActivityRepository.findAll().size();

        // Get the incidentActivity
        restIncidentActivityMockMvc.perform(delete("/api/incident-activities/{id}", incidentActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean incidentActivityExistsInEs = incidentActivitySearchRepository.exists(incidentActivity.getId());
        assertThat(incidentActivityExistsInEs).isFalse();

        // Validate the database is empty
        List<IncidentActivity> incidentActivityList = incidentActivityRepository.findAll();
        assertThat(incidentActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIncidentActivity() throws Exception {
        // Initialize the database
        incidentActivityRepository.saveAndFlush(incidentActivity);
        incidentActivitySearchRepository.save(incidentActivity);

        // Search the incidentActivity
        restIncidentActivityMockMvc.perform(get("/api/_search/incident-activities?query=id:" + incidentActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidentActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventTypeCode").value(hasItem(DEFAULT_EVENT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].eventNumber").value(hasItem(DEFAULT_EVENT_NUMBER)))
            .andExpect(jsonPath("$.[*].updatedPriorityCode").value(hasItem(DEFAULT_UPDATED_PRIORITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].updatedStatusCode").value(hasItem(DEFAULT_UPDATED_STATUS_CODE.toString())))
            .andExpect(jsonPath("$.[*].incidentComment").value(hasItem(DEFAULT_INCIDENT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].dateLastUpdated").value(hasItem(DEFAULT_DATE_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentActivity.class);
        IncidentActivity incidentActivity1 = new IncidentActivity();
        incidentActivity1.setId(1L);
        IncidentActivity incidentActivity2 = new IncidentActivity();
        incidentActivity2.setId(incidentActivity1.getId());
        assertThat(incidentActivity1).isEqualTo(incidentActivity2);
        incidentActivity2.setId(2L);
        assertThat(incidentActivity1).isNotEqualTo(incidentActivity2);
        incidentActivity1.setId(null);
        assertThat(incidentActivity1).isNotEqualTo(incidentActivity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentActivityDTO.class);
        IncidentActivityDTO incidentActivityDTO1 = new IncidentActivityDTO();
        incidentActivityDTO1.setId(1L);
        IncidentActivityDTO incidentActivityDTO2 = new IncidentActivityDTO();
        assertThat(incidentActivityDTO1).isNotEqualTo(incidentActivityDTO2);
        incidentActivityDTO2.setId(incidentActivityDTO1.getId());
        assertThat(incidentActivityDTO1).isEqualTo(incidentActivityDTO2);
        incidentActivityDTO2.setId(2L);
        assertThat(incidentActivityDTO1).isNotEqualTo(incidentActivityDTO2);
        incidentActivityDTO1.setId(null);
        assertThat(incidentActivityDTO1).isNotEqualTo(incidentActivityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(incidentActivityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(incidentActivityMapper.fromId(null)).isNull();
    }
}
