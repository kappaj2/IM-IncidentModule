package za.co.ajk.incident.web.rest;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import za.co.ajk.incident.IncidentModuleApp;
import za.co.ajk.incident.domain.Company;
import za.co.ajk.incident.domain.Country;
import za.co.ajk.incident.domain.Equipment;
import za.co.ajk.incident.domain.EquipmentActivity;
import za.co.ajk.incident.domain.Incident;
import za.co.ajk.incident.domain.IncidentActivity;
import za.co.ajk.incident.domain.Region;
import za.co.ajk.incident.enums.EventType;
import za.co.ajk.incident.enums.IncidentStatusType;
import za.co.ajk.incident.repository.CompanyRepository;
import za.co.ajk.incident.repository.CountryRepository;
import za.co.ajk.incident.repository.EquipmentActivityRepository;
import za.co.ajk.incident.repository.EquipmentRepository;
import za.co.ajk.incident.repository.IncidentActivityRepository;
import za.co.ajk.incident.repository.IncidentRepository;
import za.co.ajk.incident.repository.RegionRepository;
import za.co.ajk.incident.repository.search.IncidentSearchRepository;
import za.co.ajk.incident.service.IncidentActivityService;
import za.co.ajk.incident.service.IncidentService;
import za.co.ajk.incident.service.dto.CreateNewIncidentDTO;
import za.co.ajk.incident.service.dto.IncidentActivityDTO;
import za.co.ajk.incident.service.dto.IncidentDTO;
import za.co.ajk.incident.service.mapper.IncidentMapper;
import za.co.ajk.incident.web.rest.errors.ExceptionTranslator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static za.co.ajk.incident.web.rest.TestUtil.createFormattingConversionService;


/**
 * Test class for the IncidentResource REST controller.
 *
 * @see IncidentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IncidentModuleApp.class)
public class IncidentResourceIntTest {
    
    private static final Logger log = LoggerFactory.getLogger(IncidentResourceIntTest.class);
    
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
    
    private static final String DEFAULT_CREATED_BY = "AnonymousUser";
    private static final String UPDATED_CREATED_BY = "AnonymousUser";
    
    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    
    private static final String DEFAULT_UPDATED_BY = "AnonymousUser";
    private static final String UPDATED_UPDATED_BY = "AnonymousUser";
    
    private static final String DEFAULT_INCIDENT_RESOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_INCIDENT_RESOLUTION = "BBBBBBBBBB";
    
    private static final Instant DEFAULT_DATE_CLOSED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CLOSED = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    
    private static final String DEFAULT_CLOSED_BY = "AnonymousUser";
    private static final String UPDATED_CLOSED_BY = "AnonymousUser";
    
    private static Long DEFAULT_COMPANY = 1l;
    private static final String DEFAULT_OPERATOR = "AnonymousUser";
    
    private static final String DEFAULT_ACTIVITY_COMMENT = "Incident created";
    
    private static final String DEFAULT_UPDATED_STATUS = IncidentStatusType.OPENED.getIncidentStatusCode();
    private static final String DEFAULT_EVENT_TYPE_CODE = EventType.OPEN_INCIDENT.getEventTypeCode();
    
    @Autowired
    private IncidentRepository incidentRepository;
    
    @Autowired
    private IncidentMapper incidentMapper;
    
    @Autowired
    private IncidentService incidentService;
    
    @Autowired
    private IncidentActivityRepository incidentActivityRepository;
    
    @Autowired
    private IncidentActivityService incidentActivityService;
    
    @Autowired
    private IncidentSearchRepository incidentSearchRepository;
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private RegionRepository regionRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private EquipmentRepository equipmentRepository;
    
    @Autowired
    private EquipmentActivityRepository equipmentActivityRepository;
    
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
    
    private CreateNewIncidentDTO createNewIncidentDTO;
    
    private Country southAfrica = new Country().countryCode("ZA").countryName("South Africa").regions(null);
    private Region gauteng = new Region().regionCode("REG1").regionName("Gauteng").country(southAfrica);
    private Company ca1_1 = new Company().name("Company One").branchCode("BC1").region(gauteng);
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        
        /*
            Setup test data that is required as depencies
         */
        countryRepository.save(southAfrica);
        regionRepository.save(gauteng);
        companyRepository.save(ca1_1);
        
        equipmentRepository.save(new Equipment()
            .company(ca1_1)
            .equipmentId(Integer.valueOf(1))
            .dateAdded(Instant.now())
            .addedBy("SYSTEM"));
        equipmentRepository.save(new Equipment()
            .company(ca1_1)
            .equipmentId(Integer.valueOf(2))
            .dateAdded(Instant.now())
            .addedBy("SYSTEM"));
        
        final IncidentResource incidentResource = new IncidentResource(incidentService);
        this.restIncidentMockMvc = MockMvcBuilders.standaloneSetup(incidentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }
    
    /**
     * Create an entity for this test. This will help to test the entity-dto transalation as well as entity syntax.
     * Creating a new incident involves a lot more, so creating an incident with IncidentDTO will not work.
     * <p>
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
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .updatedBy(DEFAULT_UPDATED_BY)
            .incidentResolution(DEFAULT_INCIDENT_RESOLUTION)
            .dateClosed(DEFAULT_DATE_CLOSED)
            .closedBy(DEFAULT_CLOSED_BY);
        return incident;
    }
    
    public static CreateNewIncidentDTO createANewIncidentDTO() {
        CreateNewIncidentDTO createNewIncidentDTO = new CreateNewIncidentDTO();
        createNewIncidentDTO.setCompanyId(1l);
        createNewIncidentDTO.setIncidentDescription(DEFAULT_INCIDENT_DESCRIPTION);
        createNewIncidentDTO.setIncidentHeader(DEFAULT_INCIDENT_HEADER);
        createNewIncidentDTO.setIncidentPriorityCode(DEFAULT_INCIDENT_PRIORITY_CODE);
        createNewIncidentDTO.setIncidentTypeCode(DEFAULT_INCIDENT_TYPE_CODE);
        createNewIncidentDTO.setIncidentStatusCode(DEFAULT_INCIDENT_STATUS_CODE);
        createNewIncidentDTO.setOperator(DEFAULT_OPERATOR);
        
        CreateNewIncidentDTO.Equipment eq1 = new CreateNewIncidentDTO.Equipment();
        eq1.setEquipmentActionCode("A1");
        eq1.setEquipmentComment("Equipment 1 comment");
        eq1.setEquipmentId(1l);
        eq1.setOnLoan(true);
        eq1.setReplacement(false);
        
        CreateNewIncidentDTO.Equipment eq2 = new CreateNewIncidentDTO.Equipment();
        eq2.setEquipmentActionCode("A1");
        eq2.setEquipmentComment("Equipment 2 comment");
        eq2.setEquipmentId(2l);
        eq2.setOnLoan(true);
        eq2.setReplacement(false);
        
        List<CreateNewIncidentDTO.Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(eq1);
        equipmentList.add(eq2);
        
        createNewIncidentDTO.setEquipmentList(equipmentList);
        return createNewIncidentDTO;
    }
    
    @Before
    public void initTest() {
        incidentSearchRepository.deleteAll();
        incident = createEntity(em);
        createNewIncidentDTO = createANewIncidentDTO();
    }
    
    /**
     * Test the when an incident is created that the first incident activity is also created.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void createIncident() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        /*
            The list of companies should contain one only. Get the newly create compnayId so we can create a new incident
            using the createdNewIncidentDTO which requires the companyId;
         */
        List<Company> companyList = companyRepository.findAll();
        createNewIncidentDTO.setCompanyId(companyList.get(0).getId());
        
        log.info("New compnay ID is : "+companyList.get(0).getId());
        
        restIncidentMockMvc.perform(post("/api/v1/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(createNewIncidentDTO)))
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
    
        /*
            Incident date fields are auto populated during creation. Check time is not older than 5 mins.
         */
        
        Instant instantNow = Instant.now();
        
        Duration createTimeLapsed = Duration.between(testIncident.getDateCreated(), instantNow);
        long dateCreatedDuration = createTimeLapsed.toMillis();
        assertThat(dateCreatedDuration).isBetween(0l, 300000L);
        
        Duration updateTimeLapsed = Duration.between(testIncident.getDateUpdated(), instantNow);
        long dateUpdatedDuration = createTimeLapsed.toMillis();
        assertThat(dateUpdatedDuration).isBetween(0l, 300000L);
        
        assertThat(testIncident.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testIncident.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testIncident.getIncidentResolution()).isNull();
        assertThat(testIncident.getDateClosed()).isNull();
        assertThat(testIncident.getClosedBy()).isNull();
        
        // Validate the Incident in Elasticsearch
        Incident incidentEs = incidentSearchRepository.findOne(testIncident.getId());
        assertThat(incidentEs).isEqualToIgnoringGivenFields(testIncident);
        
        /*
            Incident should have been created by now - no field for dateClosed and closedBy.
            There should also be information for incident activity.
         */
        List<IncidentActivityDTO> incidentActivityDTOList = incidentActivityService
            .findIncidentActivitiesByIncident(testIncident);
        
        incidentActivityDTOList.forEach(incidentAct -> {
            
            Duration actCreateTimeLapsed = Duration.between(incidentAct.getDateCreated(), instantNow);
            long actDateCreatedDuration = actCreateTimeLapsed.toMillis();
            assertThat(actDateCreatedDuration).isBetween(0l, 300000L);
            
            Duration actUpdatedTimeLapsed = Duration.between(incidentAct.getDateUpdated(), instantNow);
            long actDateUpdatedDuration = actUpdatedTimeLapsed.toMillis();
            assertThat(actDateUpdatedDuration).isBetween(0l, 300000L);
            
            assertThat(incidentAct.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
            assertThat(incidentAct.getEventNumber()).isEqualTo(1);
            assertThat(incidentAct.getEventTypeCode()).isEqualTo(DEFAULT_EVENT_TYPE_CODE);
            assertThat(incidentAct.getId()).isNotNull();
            assertThat(incidentAct.getIncidentComment()).isEqualTo(DEFAULT_ACTIVITY_COMMENT);
            assertThat(incidentAct.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
            assertThat(incidentAct.getUpdatedStatusCode()).isEqualTo(DEFAULT_UPDATED_STATUS);
            assertThat(incidentAct.getUpdatedPriorityCode()).isEqualTo(DEFAULT_INCIDENT_PRIORITY_CODE);
            assertThat(incidentAct.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        });
        
        /*
            Part of the incident activity is also the creation of the entries for the equipment involved.
         */
        List<IncidentActivity> incidentActivityList = incidentActivityRepository
            .findIncidentActivitiesByIncident(testIncident);
        incidentActivityList.stream().forEach(incidentActivity -> {
            
            incidentActivity.getEventNumber();
            
            List<EquipmentActivity> equipmentActivityList =
                equipmentActivityRepository.findEquipmentActivitiesByIncidentActivity(incidentActivity);
            
            //  Should have two equipment entries for this activity.
            assertThat(equipmentActivityList.size() == 2);
            
            equipmentActivityList.stream().forEach(equipmentActivity -> {
                equipmentActivity.getCreatedBy();
                
                assertThat(equipmentActivity.getCreatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
                
                Duration createTimeLapse = Duration.between(equipmentActivity.getDateCreated(), instantNow);
                long dateCreatedDur = createTimeLapse.toMillis();
                assertThat(dateCreatedDur).isBetween(0l, 300000L);
                
                String targetComment1 = "Equipment 1 comment";
                String targetComment2 = "Equipment 2 comment";
                
                //  The entries are not ordered, just make sure it is one of the two possibilities.
                assertThat(
                    (equipmentActivity.getActivityComment().equalsIgnoreCase(targetComment1)
                        || (equipmentActivity.getActivityComment()).equalsIgnoreCase(targetComment2))
                );
                
                assertThat(equipmentActivity.getEquipmentActionCode()).isEqualTo("A1");
                assertThat(equipmentActivity.getId()).isNotNull();
                assertThat(equipmentActivity.isOnLoan()).isEqualTo(true);
                assertThat(equipmentActivity.isReplacement()).isEqualTo(false);
                
            });
        });
        
    }
    
    /**
     * Test that priority code is required. Expects a bad request response.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void checkIncidentPriorityCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        
        CreateNewIncidentDTO createNewIncidentDTO = createANewIncidentDTO();
        
        // set the field null
        createNewIncidentDTO.setIncidentPriorityCode(null);
        
        restIncidentMockMvc.perform(post("/api/v1/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(createNewIncidentDTO)))
            .andExpect(status().isBadRequest());
        
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }
    
    /**
     * Check that incidentTypeCode is required.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void checkIncidentTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        
        CreateNewIncidentDTO createNewIncidentDTO = createANewIncidentDTO();
        createNewIncidentDTO.setIncidentTypeCode(null);
        
        restIncidentMockMvc.perform(post("/api/v1/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(createNewIncidentDTO)))
            .andExpect(status().isBadRequest());
        
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }
    
    /**
     * Check that incident header is required.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void checkIncidentHeaderIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        
        CreateNewIncidentDTO createNewIncidentDTO = createANewIncidentDTO();
        createNewIncidentDTO.setIncidentHeader(null);
        
        restIncidentMockMvc.perform(post("/api/v1/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(createNewIncidentDTO)))
            .andExpect(status().isBadRequest());
        
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }
    
    /**
     * Check that incident description is required.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void checkIncidentDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        
        CreateNewIncidentDTO createNewIncidentDTO = createANewIncidentDTO();
        createNewIncidentDTO.setIncidentDescription(null);
        
        restIncidentMockMvc.perform(post("/api/v1/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(createNewIncidentDTO)))
            .andExpect(status().isBadRequest());
        
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }
    
    /**
     * Test that incident status code is required.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void checkIncidentStatusCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        
        CreateNewIncidentDTO createNewIncidentDTO = createANewIncidentDTO();
        createNewIncidentDTO.setIncidentStatusCode(null);
        
        restIncidentMockMvc.perform(post("/api/v1/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(createNewIncidentDTO)))
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
        restIncidentMockMvc.perform(get("/api/v1/incidents?sort=id,desc"))
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
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].incidentResolution").value(hasItem(DEFAULT_INCIDENT_RESOLUTION.toString())))
            .andExpect(jsonPath("$.[*].dateClosed").value(hasItem(DEFAULT_DATE_CLOSED.toString())))
            .andExpect(jsonPath("$.[*].closedBy").value(hasItem(DEFAULT_CLOSED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);
        
        // Get the incident
        restIncidentMockMvc.perform(get("/api/v1/incidents/{id}", incident.getId()))
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
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.incidentResolution").value(DEFAULT_INCIDENT_RESOLUTION.toString()))
            .andExpect(jsonPath("$.dateClosed").value(DEFAULT_DATE_CLOSED.toString()))
            .andExpect(jsonPath("$.closedBy").value(DEFAULT_CLOSED_BY.toString()));
    }
    
    @Test
    @Transactional
    public void getNonExistingIncident() throws Exception {
        // Get the incident
        restIncidentMockMvc.perform(get("/api/v1/incidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
    
    @Test
    @Transactional
    public void updateIncident() throws Exception {
        //        // Initialize the database
        //        incidentRepository.saveAndFlush(incident);
        //        incidentSearchRepository.save(incident);
        //        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();
        //
        //        // Update the incident
        //        Incident updatedIncident = incidentRepository.findOne(incident.getId());
        //        // Disconnect from session so that the updates on updatedIncident are not directly saved in db
        //        em.detach(updatedIncident);
        //        updatedIncident
        //            .incidentNumber(UPDATED_INCIDENT_NUMBER)
        //            .incidentPriorityCode(UPDATED_INCIDENT_PRIORITY_CODE)
        //            .incidentTypeCode(UPDATED_INCIDENT_TYPE_CODE)
        //            .incidentHeader(UPDATED_INCIDENT_HEADER)
        //            .incidentDescription(UPDATED_INCIDENT_DESCRIPTION)
        //            .incidentStatusCode(UPDATED_INCIDENT_STATUS_CODE)
        //            .dateCreated(UPDATED_DATE_CREATED)
        //            .createdBy(UPDATED_CREATED_BY)
        //            .dateUpdated(UPDATED_DATE_UPDATED)
        //            .updatedBy(UPDATED_UPDATED_BY)
        //            .incidentResolution(UPDATED_INCIDENT_RESOLUTION)
        //            .dateClosed(UPDATED_DATE_CLOSED)
        //            .closedBy(UPDATED_CLOSED_BY);
        //        IncidentDTO incidentDTO = incidentMapper.toDto(updatedIncident);
        //
        //        restIncidentMockMvc.perform(put("/api/incidents")
        //            .contentType(TestUtil.APPLICATION_JSON_UTF8)
        //            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
        //            .andExpect(status().isOk());
        //
        //        // Validate the Incident in the database
        //        List<Incident> incidentList = incidentRepository.findAll();
        //        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate);
        //        Incident testIncident = incidentList.get(incidentList.size() - 1);
        //        assertThat(testIncident.getIncidentNumber()).isEqualTo(UPDATED_INCIDENT_NUMBER);
        //        assertThat(testIncident.getIncidentPriorityCode()).isEqualTo(UPDATED_INCIDENT_PRIORITY_CODE);
        //        assertThat(testIncident.getIncidentTypeCode()).isEqualTo(UPDATED_INCIDENT_TYPE_CODE);
        //        assertThat(testIncident.getIncidentHeader()).isEqualTo(UPDATED_INCIDENT_HEADER);
        //        assertThat(testIncident.getIncidentDescription()).isEqualTo(UPDATED_INCIDENT_DESCRIPTION);
        //        assertThat(testIncident.getIncidentStatusCode()).isEqualTo(UPDATED_INCIDENT_STATUS_CODE);
        //        assertThat(testIncident.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        //        assertThat(testIncident.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        //        assertThat(testIncident.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        //        assertThat(testIncident.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        //        assertThat(testIncident.getIncidentResolution()).isEqualTo(UPDATED_INCIDENT_RESOLUTION);
        //        assertThat(testIncident.getDateClosed()).isEqualTo(UPDATED_DATE_CLOSED);
        //        assertThat(testIncident.getClosedBy()).isEqualTo(UPDATED_CLOSED_BY);
        //
        //        // Validate the Incident in Elasticsearch
        //        Incident incidentEs = incidentSearchRepository.findOne(testIncident.getId());
        //        assertThat(incidentEs).isEqualToIgnoringGivenFields(testIncident);
    }
    
    /**
     * This test expects that the incident will be created as it is not supposed to exists. So db size should be one more...
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void updateNonExistingIncident() throws Exception {
        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();
        
        //        // Create the Incident
        //        IncidentDTO incidentDTO = incidentMapper.toDto(incident);
        //
        //        // If the entity doesn't have an ID, it will be created instead of just being updated
        //        restIncidentMockMvc.perform(put("/api/incidents")
        //            .contentType(TestUtil.APPLICATION_JSON_UTF8)
        //            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
        //            .andExpect(status().isCreated());
        //
        //        // Validate the Incident in the database
        //        List<Incident> incidentList = incidentRepository.findAll();
        //        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate + 1);
    }
    
    @Test
    @Transactional
    public void deleteIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);
        incidentSearchRepository.save(incident);
        int databaseSizeBeforeDelete = incidentRepository.findAll().size();
        
        // Get the incident
        restIncidentMockMvc.perform(delete("/api/v1/incidents/{id}", incident.getId())
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
        //        // Initialize the database
        //        incidentRepository.saveAndFlush(incident);
        //        incidentSearchRepository.save(incident);
        //
        //        // Search the incident
        //        restIncidentMockMvc.perform(get("/api/_search/incidents?query=id:" + incident.getId()))
        //            .andExpect(status().isOk())
        //            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        //            .andExpect(jsonPath("$.[*].id").value(hasItem(incident.getId().intValue())))
        //            .andExpect(jsonPath("$.[*].incidentNumber").value(hasItem(DEFAULT_INCIDENT_NUMBER)))
        //            .andExpect(jsonPath("$.[*].incidentPriorityCode").value(hasItem(DEFAULT_INCIDENT_PRIORITY_CODE.toString())))
        //            .andExpect(jsonPath("$.[*].incidentTypeCode").value(hasItem(DEFAULT_INCIDENT_TYPE_CODE.toString())))
        //            .andExpect(jsonPath("$.[*].incidentHeader").value(hasItem(DEFAULT_INCIDENT_HEADER.toString())))
        //            .andExpect(jsonPath("$.[*].incidentDescription").value(hasItem(DEFAULT_INCIDENT_DESCRIPTION.toString())))
        //            .andExpect(jsonPath("$.[*].incidentStatusCode").value(hasItem(DEFAULT_INCIDENT_STATUS_CODE.toString())))
        //            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
        //            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
        //            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
        //            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
        //            .andExpect(jsonPath("$.[*].incidentResolution").value(hasItem(DEFAULT_INCIDENT_RESOLUTION.toString())))
        //            .andExpect(jsonPath("$.[*].dateClosed").value(hasItem(DEFAULT_DATE_CLOSED.toString())))
        //            .andExpect(jsonPath("$.[*].closedBy").value(hasItem(DEFAULT_CLOSED_BY.toString())));
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
