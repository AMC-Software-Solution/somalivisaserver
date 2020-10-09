package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.SomalivisaserverApp;
import so.gov.mfa.visa.domain.VisaApplication;
import so.gov.mfa.visa.repository.VisaApplicationRepository;
import so.gov.mfa.visa.repository.search.VisaApplicationSearchRepository;
import so.gov.mfa.visa.service.VisaApplicationService;
import so.gov.mfa.visa.service.dto.VisaApplicationDTO;
import so.gov.mfa.visa.service.mapper.VisaApplicationMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static so.gov.mfa.visa.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import so.gov.mfa.visa.domain.enumeration.ApplicationStatus;
import so.gov.mfa.visa.domain.enumeration.TravelPurpose;
import so.gov.mfa.visa.domain.enumeration.VisaType;
import so.gov.mfa.visa.domain.enumeration.TravelMode;
/**
 * Integration tests for the {@link VisaApplicationResource} REST controller.
 */
@SpringBootTest(classes = SomalivisaserverApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class VisaApplicationResourceIT {

    private static final String DEFAULT_APPLICATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_APPLICATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_APPLICATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ApplicationStatus DEFAULT_APPLICATION_STATUS = ApplicationStatus.RECEIVED;
    private static final ApplicationStatus UPDATED_APPLICATION_STATUS = ApplicationStatus.UNDER_PROCESS;

    private static final TravelPurpose DEFAULT_TRAVEL_PURPOSE = TravelPurpose.TOURISM;
    private static final TravelPurpose UPDATED_TRAVEL_PURPOSE = TravelPurpose.BUSINESS;

    private static final VisaType DEFAULT_VISA_TYPE = VisaType.SINGLE_ENTRY;
    private static final VisaType UPDATED_VISA_TYPE = VisaType.MULTIPLE_ENTRY;

    private static final TravelMode DEFAULT_TRAVEL_MODE = TravelMode.AIR;
    private static final TravelMode UPDATED_TRAVEL_MODE = TravelMode.SEA;

    private static final String DEFAULT_PORT_OF_ENTRY = "AAAAAAAAAA";
    private static final String UPDATED_PORT_OF_ENTRY = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER_OF_ENTRIES_REQUESTED = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_OF_ENTRIES_REQUESTED = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INTENDED_DATE_OF_ARRIVAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INTENDED_DATE_OF_ARRIVAL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_INTENDED_DATE_OF_DEPARTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INTENDED_DATE_OF_DEPARTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_UNTIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_UNTIL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TRAVEL_PURPOSE_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_TRAVEL_PURPOSE_OTHER = "BBBBBBBBBB";

    private static final String DEFAULT_REJECT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECT_REASON = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_APPROVED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_APPROVED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private VisaApplicationRepository visaApplicationRepository;

    @Autowired
    private VisaApplicationMapper visaApplicationMapper;

    @Autowired
    private VisaApplicationService visaApplicationService;

    /**
     * This repository is mocked in the so.gov.mfa.visa.repository.search test package.
     *
     * @see so.gov.mfa.visa.repository.search.VisaApplicationSearchRepositoryMockConfiguration
     */
    @Autowired
    private VisaApplicationSearchRepository mockVisaApplicationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVisaApplicationMockMvc;

    private VisaApplication visaApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VisaApplication createEntity(EntityManager em) {
        VisaApplication visaApplication = new VisaApplication()
            .applicationName(DEFAULT_APPLICATION_NAME)
            .applicationCode(DEFAULT_APPLICATION_CODE)
            .applicationDate(DEFAULT_APPLICATION_DATE)
            .applicationStatus(DEFAULT_APPLICATION_STATUS)
            .travelPurpose(DEFAULT_TRAVEL_PURPOSE)
            .visaType(DEFAULT_VISA_TYPE)
            .travelMode(DEFAULT_TRAVEL_MODE)
            .portOfEntry(DEFAULT_PORT_OF_ENTRY)
            .numberOfEntriesRequested(DEFAULT_NUMBER_OF_ENTRIES_REQUESTED)
            .intendedDateOfArrival(DEFAULT_INTENDED_DATE_OF_ARRIVAL)
            .intendedDateOfDeparture(DEFAULT_INTENDED_DATE_OF_DEPARTURE)
            .validUntil(DEFAULT_VALID_UNTIL)
            .travelPurposeOther(DEFAULT_TRAVEL_PURPOSE_OTHER)
            .rejectReason(DEFAULT_REJECT_REASON)
            .approvedDate(DEFAULT_APPROVED_DATE);
        return visaApplication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VisaApplication createUpdatedEntity(EntityManager em) {
        VisaApplication visaApplication = new VisaApplication()
            .applicationName(UPDATED_APPLICATION_NAME)
            .applicationCode(UPDATED_APPLICATION_CODE)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .applicationStatus(UPDATED_APPLICATION_STATUS)
            .travelPurpose(UPDATED_TRAVEL_PURPOSE)
            .visaType(UPDATED_VISA_TYPE)
            .travelMode(UPDATED_TRAVEL_MODE)
            .portOfEntry(UPDATED_PORT_OF_ENTRY)
            .numberOfEntriesRequested(UPDATED_NUMBER_OF_ENTRIES_REQUESTED)
            .intendedDateOfArrival(UPDATED_INTENDED_DATE_OF_ARRIVAL)
            .intendedDateOfDeparture(UPDATED_INTENDED_DATE_OF_DEPARTURE)
            .validUntil(UPDATED_VALID_UNTIL)
            .travelPurposeOther(UPDATED_TRAVEL_PURPOSE_OTHER)
            .rejectReason(UPDATED_REJECT_REASON)
            .approvedDate(UPDATED_APPROVED_DATE);
        return visaApplication;
    }

    @BeforeEach
    public void initTest() {
        visaApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisaApplication() throws Exception {
        int databaseSizeBeforeCreate = visaApplicationRepository.findAll().size();
        // Create the VisaApplication
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);
        restVisaApplicationMockMvc.perform(post("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isCreated());

        // Validate the VisaApplication in the database
        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        VisaApplication testVisaApplication = visaApplicationList.get(visaApplicationList.size() - 1);
        assertThat(testVisaApplication.getApplicationName()).isEqualTo(DEFAULT_APPLICATION_NAME);
        assertThat(testVisaApplication.getApplicationCode()).isEqualTo(DEFAULT_APPLICATION_CODE);
        assertThat(testVisaApplication.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
        assertThat(testVisaApplication.getApplicationStatus()).isEqualTo(DEFAULT_APPLICATION_STATUS);
        assertThat(testVisaApplication.getTravelPurpose()).isEqualTo(DEFAULT_TRAVEL_PURPOSE);
        assertThat(testVisaApplication.getVisaType()).isEqualTo(DEFAULT_VISA_TYPE);
        assertThat(testVisaApplication.getTravelMode()).isEqualTo(DEFAULT_TRAVEL_MODE);
        assertThat(testVisaApplication.getPortOfEntry()).isEqualTo(DEFAULT_PORT_OF_ENTRY);
        assertThat(testVisaApplication.getNumberOfEntriesRequested()).isEqualTo(DEFAULT_NUMBER_OF_ENTRIES_REQUESTED);
        assertThat(testVisaApplication.getIntendedDateOfArrival()).isEqualTo(DEFAULT_INTENDED_DATE_OF_ARRIVAL);
        assertThat(testVisaApplication.getIntendedDateOfDeparture()).isEqualTo(DEFAULT_INTENDED_DATE_OF_DEPARTURE);
        assertThat(testVisaApplication.getValidUntil()).isEqualTo(DEFAULT_VALID_UNTIL);
        assertThat(testVisaApplication.getTravelPurposeOther()).isEqualTo(DEFAULT_TRAVEL_PURPOSE_OTHER);
        assertThat(testVisaApplication.getRejectReason()).isEqualTo(DEFAULT_REJECT_REASON);
        assertThat(testVisaApplication.getApprovedDate()).isEqualTo(DEFAULT_APPROVED_DATE);

        // Validate the VisaApplication in Elasticsearch
        verify(mockVisaApplicationSearchRepository, times(1)).save(testVisaApplication);
    }

    @Test
    @Transactional
    public void createVisaApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visaApplicationRepository.findAll().size();

        // Create the VisaApplication with an existing ID
        visaApplication.setId(1L);
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisaApplicationMockMvc.perform(post("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VisaApplication in the database
        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeCreate);

        // Validate the VisaApplication in Elasticsearch
        verify(mockVisaApplicationSearchRepository, times(0)).save(visaApplication);
    }


    @Test
    @Transactional
    public void checkApplicationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = visaApplicationRepository.findAll().size();
        // set the field null
        visaApplication.setApplicationName(null);

        // Create the VisaApplication, which fails.
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);


        restVisaApplicationMockMvc.perform(post("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTravelPurposeIsRequired() throws Exception {
        int databaseSizeBeforeTest = visaApplicationRepository.findAll().size();
        // set the field null
        visaApplication.setTravelPurpose(null);

        // Create the VisaApplication, which fails.
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);


        restVisaApplicationMockMvc.perform(post("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = visaApplicationRepository.findAll().size();
        // set the field null
        visaApplication.setVisaType(null);

        // Create the VisaApplication, which fails.
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);


        restVisaApplicationMockMvc.perform(post("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTravelModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = visaApplicationRepository.findAll().size();
        // set the field null
        visaApplication.setTravelMode(null);

        // Create the VisaApplication, which fails.
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);


        restVisaApplicationMockMvc.perform(post("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortOfEntryIsRequired() throws Exception {
        int databaseSizeBeforeTest = visaApplicationRepository.findAll().size();
        // set the field null
        visaApplication.setPortOfEntry(null);

        // Create the VisaApplication, which fails.
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);


        restVisaApplicationMockMvc.perform(post("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfEntriesRequestedIsRequired() throws Exception {
        int databaseSizeBeforeTest = visaApplicationRepository.findAll().size();
        // set the field null
        visaApplication.setNumberOfEntriesRequested(null);

        // Create the VisaApplication, which fails.
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);


        restVisaApplicationMockMvc.perform(post("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntendedDateOfArrivalIsRequired() throws Exception {
        int databaseSizeBeforeTest = visaApplicationRepository.findAll().size();
        // set the field null
        visaApplication.setIntendedDateOfArrival(null);

        // Create the VisaApplication, which fails.
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);


        restVisaApplicationMockMvc.perform(post("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntendedDateOfDepartureIsRequired() throws Exception {
        int databaseSizeBeforeTest = visaApplicationRepository.findAll().size();
        // set the field null
        visaApplication.setIntendedDateOfDeparture(null);

        // Create the VisaApplication, which fails.
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);


        restVisaApplicationMockMvc.perform(post("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidUntilIsRequired() throws Exception {
        int databaseSizeBeforeTest = visaApplicationRepository.findAll().size();
        // set the field null
        visaApplication.setValidUntil(null);

        // Create the VisaApplication, which fails.
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);


        restVisaApplicationMockMvc.perform(post("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVisaApplications() throws Exception {
        // Initialize the database
        visaApplicationRepository.saveAndFlush(visaApplication);

        // Get all the visaApplicationList
        restVisaApplicationMockMvc.perform(get("/api/visa-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visaApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationName").value(hasItem(DEFAULT_APPLICATION_NAME)))
            .andExpect(jsonPath("$.[*].applicationCode").value(hasItem(DEFAULT_APPLICATION_CODE)))
            .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(sameInstant(DEFAULT_APPLICATION_DATE))))
            .andExpect(jsonPath("$.[*].applicationStatus").value(hasItem(DEFAULT_APPLICATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].travelPurpose").value(hasItem(DEFAULT_TRAVEL_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].visaType").value(hasItem(DEFAULT_VISA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].travelMode").value(hasItem(DEFAULT_TRAVEL_MODE.toString())))
            .andExpect(jsonPath("$.[*].portOfEntry").value(hasItem(DEFAULT_PORT_OF_ENTRY)))
            .andExpect(jsonPath("$.[*].numberOfEntriesRequested").value(hasItem(DEFAULT_NUMBER_OF_ENTRIES_REQUESTED)))
            .andExpect(jsonPath("$.[*].intendedDateOfArrival").value(hasItem(DEFAULT_INTENDED_DATE_OF_ARRIVAL.toString())))
            .andExpect(jsonPath("$.[*].intendedDateOfDeparture").value(hasItem(DEFAULT_INTENDED_DATE_OF_DEPARTURE.toString())))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].travelPurposeOther").value(hasItem(DEFAULT_TRAVEL_PURPOSE_OTHER)))
            .andExpect(jsonPath("$.[*].rejectReason").value(hasItem(DEFAULT_REJECT_REASON)))
            .andExpect(jsonPath("$.[*].approvedDate").value(hasItem(sameInstant(DEFAULT_APPROVED_DATE))));
    }
    
    @Test
    @Transactional
    public void getVisaApplication() throws Exception {
        // Initialize the database
        visaApplicationRepository.saveAndFlush(visaApplication);

        // Get the visaApplication
        restVisaApplicationMockMvc.perform(get("/api/visa-applications/{id}", visaApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visaApplication.getId().intValue()))
            .andExpect(jsonPath("$.applicationName").value(DEFAULT_APPLICATION_NAME))
            .andExpect(jsonPath("$.applicationCode").value(DEFAULT_APPLICATION_CODE))
            .andExpect(jsonPath("$.applicationDate").value(sameInstant(DEFAULT_APPLICATION_DATE)))
            .andExpect(jsonPath("$.applicationStatus").value(DEFAULT_APPLICATION_STATUS.toString()))
            .andExpect(jsonPath("$.travelPurpose").value(DEFAULT_TRAVEL_PURPOSE.toString()))
            .andExpect(jsonPath("$.visaType").value(DEFAULT_VISA_TYPE.toString()))
            .andExpect(jsonPath("$.travelMode").value(DEFAULT_TRAVEL_MODE.toString()))
            .andExpect(jsonPath("$.portOfEntry").value(DEFAULT_PORT_OF_ENTRY))
            .andExpect(jsonPath("$.numberOfEntriesRequested").value(DEFAULT_NUMBER_OF_ENTRIES_REQUESTED))
            .andExpect(jsonPath("$.intendedDateOfArrival").value(DEFAULT_INTENDED_DATE_OF_ARRIVAL.toString()))
            .andExpect(jsonPath("$.intendedDateOfDeparture").value(DEFAULT_INTENDED_DATE_OF_DEPARTURE.toString()))
            .andExpect(jsonPath("$.validUntil").value(DEFAULT_VALID_UNTIL.toString()))
            .andExpect(jsonPath("$.travelPurposeOther").value(DEFAULT_TRAVEL_PURPOSE_OTHER))
            .andExpect(jsonPath("$.rejectReason").value(DEFAULT_REJECT_REASON))
            .andExpect(jsonPath("$.approvedDate").value(sameInstant(DEFAULT_APPROVED_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingVisaApplication() throws Exception {
        // Get the visaApplication
        restVisaApplicationMockMvc.perform(get("/api/visa-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisaApplication() throws Exception {
        // Initialize the database
        visaApplicationRepository.saveAndFlush(visaApplication);

        int databaseSizeBeforeUpdate = visaApplicationRepository.findAll().size();

        // Update the visaApplication
        VisaApplication updatedVisaApplication = visaApplicationRepository.findById(visaApplication.getId()).get();
        // Disconnect from session so that the updates on updatedVisaApplication are not directly saved in db
        em.detach(updatedVisaApplication);
        updatedVisaApplication
            .applicationName(UPDATED_APPLICATION_NAME)
            .applicationCode(UPDATED_APPLICATION_CODE)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .applicationStatus(UPDATED_APPLICATION_STATUS)
            .travelPurpose(UPDATED_TRAVEL_PURPOSE)
            .visaType(UPDATED_VISA_TYPE)
            .travelMode(UPDATED_TRAVEL_MODE)
            .portOfEntry(UPDATED_PORT_OF_ENTRY)
            .numberOfEntriesRequested(UPDATED_NUMBER_OF_ENTRIES_REQUESTED)
            .intendedDateOfArrival(UPDATED_INTENDED_DATE_OF_ARRIVAL)
            .intendedDateOfDeparture(UPDATED_INTENDED_DATE_OF_DEPARTURE)
            .validUntil(UPDATED_VALID_UNTIL)
            .travelPurposeOther(UPDATED_TRAVEL_PURPOSE_OTHER)
            .rejectReason(UPDATED_REJECT_REASON)
            .approvedDate(UPDATED_APPROVED_DATE);
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(updatedVisaApplication);

        restVisaApplicationMockMvc.perform(put("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isOk());

        // Validate the VisaApplication in the database
        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeUpdate);
        VisaApplication testVisaApplication = visaApplicationList.get(visaApplicationList.size() - 1);
        assertThat(testVisaApplication.getApplicationName()).isEqualTo(UPDATED_APPLICATION_NAME);
        assertThat(testVisaApplication.getApplicationCode()).isEqualTo(UPDATED_APPLICATION_CODE);
        assertThat(testVisaApplication.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
        assertThat(testVisaApplication.getApplicationStatus()).isEqualTo(UPDATED_APPLICATION_STATUS);
        assertThat(testVisaApplication.getTravelPurpose()).isEqualTo(UPDATED_TRAVEL_PURPOSE);
        assertThat(testVisaApplication.getVisaType()).isEqualTo(UPDATED_VISA_TYPE);
        assertThat(testVisaApplication.getTravelMode()).isEqualTo(UPDATED_TRAVEL_MODE);
        assertThat(testVisaApplication.getPortOfEntry()).isEqualTo(UPDATED_PORT_OF_ENTRY);
        assertThat(testVisaApplication.getNumberOfEntriesRequested()).isEqualTo(UPDATED_NUMBER_OF_ENTRIES_REQUESTED);
        assertThat(testVisaApplication.getIntendedDateOfArrival()).isEqualTo(UPDATED_INTENDED_DATE_OF_ARRIVAL);
        assertThat(testVisaApplication.getIntendedDateOfDeparture()).isEqualTo(UPDATED_INTENDED_DATE_OF_DEPARTURE);
        assertThat(testVisaApplication.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testVisaApplication.getTravelPurposeOther()).isEqualTo(UPDATED_TRAVEL_PURPOSE_OTHER);
        assertThat(testVisaApplication.getRejectReason()).isEqualTo(UPDATED_REJECT_REASON);
        assertThat(testVisaApplication.getApprovedDate()).isEqualTo(UPDATED_APPROVED_DATE);

        // Validate the VisaApplication in Elasticsearch
        verify(mockVisaApplicationSearchRepository, times(1)).save(testVisaApplication);
    }

    @Test
    @Transactional
    public void updateNonExistingVisaApplication() throws Exception {
        int databaseSizeBeforeUpdate = visaApplicationRepository.findAll().size();

        // Create the VisaApplication
        VisaApplicationDTO visaApplicationDTO = visaApplicationMapper.toDto(visaApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisaApplicationMockMvc.perform(put("/api/visa-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VisaApplication in the database
        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the VisaApplication in Elasticsearch
        verify(mockVisaApplicationSearchRepository, times(0)).save(visaApplication);
    }

    @Test
    @Transactional
    public void deleteVisaApplication() throws Exception {
        // Initialize the database
        visaApplicationRepository.saveAndFlush(visaApplication);

        int databaseSizeBeforeDelete = visaApplicationRepository.findAll().size();

        // Delete the visaApplication
        restVisaApplicationMockMvc.perform(delete("/api/visa-applications/{id}", visaApplication.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VisaApplication> visaApplicationList = visaApplicationRepository.findAll();
        assertThat(visaApplicationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the VisaApplication in Elasticsearch
        verify(mockVisaApplicationSearchRepository, times(1)).deleteById(visaApplication.getId());
    }

    @Test
    @Transactional
    public void searchVisaApplication() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        visaApplicationRepository.saveAndFlush(visaApplication);
        when(mockVisaApplicationSearchRepository.search(queryStringQuery("id:" + visaApplication.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(visaApplication), PageRequest.of(0, 1), 1));

        // Search the visaApplication
        restVisaApplicationMockMvc.perform(get("/api/_search/visa-applications?query=id:" + visaApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visaApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationName").value(hasItem(DEFAULT_APPLICATION_NAME)))
            .andExpect(jsonPath("$.[*].applicationCode").value(hasItem(DEFAULT_APPLICATION_CODE)))
            .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(sameInstant(DEFAULT_APPLICATION_DATE))))
            .andExpect(jsonPath("$.[*].applicationStatus").value(hasItem(DEFAULT_APPLICATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].travelPurpose").value(hasItem(DEFAULT_TRAVEL_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].visaType").value(hasItem(DEFAULT_VISA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].travelMode").value(hasItem(DEFAULT_TRAVEL_MODE.toString())))
            .andExpect(jsonPath("$.[*].portOfEntry").value(hasItem(DEFAULT_PORT_OF_ENTRY)))
            .andExpect(jsonPath("$.[*].numberOfEntriesRequested").value(hasItem(DEFAULT_NUMBER_OF_ENTRIES_REQUESTED)))
            .andExpect(jsonPath("$.[*].intendedDateOfArrival").value(hasItem(DEFAULT_INTENDED_DATE_OF_ARRIVAL.toString())))
            .andExpect(jsonPath("$.[*].intendedDateOfDeparture").value(hasItem(DEFAULT_INTENDED_DATE_OF_DEPARTURE.toString())))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].travelPurposeOther").value(hasItem(DEFAULT_TRAVEL_PURPOSE_OTHER)))
            .andExpect(jsonPath("$.[*].rejectReason").value(hasItem(DEFAULT_REJECT_REASON)))
            .andExpect(jsonPath("$.[*].approvedDate").value(hasItem(sameInstant(DEFAULT_APPROVED_DATE))));
    }
}
