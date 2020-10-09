package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.SomalivisaserverApp;
import so.gov.mfa.visa.domain.VisaApplicationStay;
import so.gov.mfa.visa.repository.VisaApplicationStayRepository;
import so.gov.mfa.visa.repository.search.VisaApplicationStaySearchRepository;
import so.gov.mfa.visa.service.VisaApplicationStayService;
import so.gov.mfa.visa.service.dto.VisaApplicationStayDTO;
import so.gov.mfa.visa.service.mapper.VisaApplicationStayMapper;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VisaApplicationStayResource} REST controller.
 */
@SpringBootTest(classes = SomalivisaserverApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class VisaApplicationStayResourceIT {

    private static final Integer DEFAULT_DURATION_OF_PROPOSED_STAY_IN_DAYS = 1;
    private static final Integer UPDATED_DURATION_OF_PROPOSED_STAY_IN_DAYS = 2;

    private static final String DEFAULT_NAME_OF_HOSTING_PERSON_ORCOMPANY = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_HOSTING_PERSON_ORCOMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_STAYING_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STAYING_LOCATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STAY_LOCATION_FULL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STAY_LOCATION_FULL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_STAY_LOCATION_TELEPHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STAY_LOCATION_TELEPHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STAY_LOCATION_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_STAY_LOCATION_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WHO_COVERS_COST_OF_APPLICANTS_STAY = "AAAAAAAAAA";
    private static final String UPDATED_WHO_COVERS_COST_OF_APPLICANTS_STAY = "BBBBBBBBBB";

    @Autowired
    private VisaApplicationStayRepository visaApplicationStayRepository;

    @Autowired
    private VisaApplicationStayMapper visaApplicationStayMapper;

    @Autowired
    private VisaApplicationStayService visaApplicationStayService;

    /**
     * This repository is mocked in the so.gov.mfa.visa.repository.search test package.
     *
     * @see so.gov.mfa.visa.repository.search.VisaApplicationStaySearchRepositoryMockConfiguration
     */
    @Autowired
    private VisaApplicationStaySearchRepository mockVisaApplicationStaySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVisaApplicationStayMockMvc;

    private VisaApplicationStay visaApplicationStay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VisaApplicationStay createEntity(EntityManager em) {
        VisaApplicationStay visaApplicationStay = new VisaApplicationStay()
            .durationOfProposedStayInDays(DEFAULT_DURATION_OF_PROPOSED_STAY_IN_DAYS)
            .nameOfHostingPersonOrcompany(DEFAULT_NAME_OF_HOSTING_PERSON_ORCOMPANY)
            .stayingLocationName(DEFAULT_STAYING_LOCATION_NAME)
            .stayLocationFullAddress(DEFAULT_STAY_LOCATION_FULL_ADDRESS)
            .stayLocationTelephoneNumber(DEFAULT_STAY_LOCATION_TELEPHONE_NUMBER)
            .stayLocationEmail(DEFAULT_STAY_LOCATION_EMAIL)
            .whoCoversCostOfApplicantsStay(DEFAULT_WHO_COVERS_COST_OF_APPLICANTS_STAY);
        return visaApplicationStay;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VisaApplicationStay createUpdatedEntity(EntityManager em) {
        VisaApplicationStay visaApplicationStay = new VisaApplicationStay()
            .durationOfProposedStayInDays(UPDATED_DURATION_OF_PROPOSED_STAY_IN_DAYS)
            .nameOfHostingPersonOrcompany(UPDATED_NAME_OF_HOSTING_PERSON_ORCOMPANY)
            .stayingLocationName(UPDATED_STAYING_LOCATION_NAME)
            .stayLocationFullAddress(UPDATED_STAY_LOCATION_FULL_ADDRESS)
            .stayLocationTelephoneNumber(UPDATED_STAY_LOCATION_TELEPHONE_NUMBER)
            .stayLocationEmail(UPDATED_STAY_LOCATION_EMAIL)
            .whoCoversCostOfApplicantsStay(UPDATED_WHO_COVERS_COST_OF_APPLICANTS_STAY);
        return visaApplicationStay;
    }

    @BeforeEach
    public void initTest() {
        visaApplicationStay = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisaApplicationStay() throws Exception {
        int databaseSizeBeforeCreate = visaApplicationStayRepository.findAll().size();
        // Create the VisaApplicationStay
        VisaApplicationStayDTO visaApplicationStayDTO = visaApplicationStayMapper.toDto(visaApplicationStay);
        restVisaApplicationStayMockMvc.perform(post("/api/visa-application-stays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationStayDTO)))
            .andExpect(status().isCreated());

        // Validate the VisaApplicationStay in the database
        List<VisaApplicationStay> visaApplicationStayList = visaApplicationStayRepository.findAll();
        assertThat(visaApplicationStayList).hasSize(databaseSizeBeforeCreate + 1);
        VisaApplicationStay testVisaApplicationStay = visaApplicationStayList.get(visaApplicationStayList.size() - 1);
        assertThat(testVisaApplicationStay.getDurationOfProposedStayInDays()).isEqualTo(DEFAULT_DURATION_OF_PROPOSED_STAY_IN_DAYS);
        assertThat(testVisaApplicationStay.getNameOfHostingPersonOrcompany()).isEqualTo(DEFAULT_NAME_OF_HOSTING_PERSON_ORCOMPANY);
        assertThat(testVisaApplicationStay.getStayingLocationName()).isEqualTo(DEFAULT_STAYING_LOCATION_NAME);
        assertThat(testVisaApplicationStay.getStayLocationFullAddress()).isEqualTo(DEFAULT_STAY_LOCATION_FULL_ADDRESS);
        assertThat(testVisaApplicationStay.getStayLocationTelephoneNumber()).isEqualTo(DEFAULT_STAY_LOCATION_TELEPHONE_NUMBER);
        assertThat(testVisaApplicationStay.getStayLocationEmail()).isEqualTo(DEFAULT_STAY_LOCATION_EMAIL);
        assertThat(testVisaApplicationStay.getWhoCoversCostOfApplicantsStay()).isEqualTo(DEFAULT_WHO_COVERS_COST_OF_APPLICANTS_STAY);

        // Validate the VisaApplicationStay in Elasticsearch
        verify(mockVisaApplicationStaySearchRepository, times(1)).save(testVisaApplicationStay);
    }

    @Test
    @Transactional
    public void createVisaApplicationStayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visaApplicationStayRepository.findAll().size();

        // Create the VisaApplicationStay with an existing ID
        visaApplicationStay.setId(1L);
        VisaApplicationStayDTO visaApplicationStayDTO = visaApplicationStayMapper.toDto(visaApplicationStay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisaApplicationStayMockMvc.perform(post("/api/visa-application-stays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationStayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VisaApplicationStay in the database
        List<VisaApplicationStay> visaApplicationStayList = visaApplicationStayRepository.findAll();
        assertThat(visaApplicationStayList).hasSize(databaseSizeBeforeCreate);

        // Validate the VisaApplicationStay in Elasticsearch
        verify(mockVisaApplicationStaySearchRepository, times(0)).save(visaApplicationStay);
    }


    @Test
    @Transactional
    public void checkDurationOfProposedStayInDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = visaApplicationStayRepository.findAll().size();
        // set the field null
        visaApplicationStay.setDurationOfProposedStayInDays(null);

        // Create the VisaApplicationStay, which fails.
        VisaApplicationStayDTO visaApplicationStayDTO = visaApplicationStayMapper.toDto(visaApplicationStay);


        restVisaApplicationStayMockMvc.perform(post("/api/visa-application-stays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationStayDTO)))
            .andExpect(status().isBadRequest());

        List<VisaApplicationStay> visaApplicationStayList = visaApplicationStayRepository.findAll();
        assertThat(visaApplicationStayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVisaApplicationStays() throws Exception {
        // Initialize the database
        visaApplicationStayRepository.saveAndFlush(visaApplicationStay);

        // Get all the visaApplicationStayList
        restVisaApplicationStayMockMvc.perform(get("/api/visa-application-stays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visaApplicationStay.getId().intValue())))
            .andExpect(jsonPath("$.[*].durationOfProposedStayInDays").value(hasItem(DEFAULT_DURATION_OF_PROPOSED_STAY_IN_DAYS)))
            .andExpect(jsonPath("$.[*].nameOfHostingPersonOrcompany").value(hasItem(DEFAULT_NAME_OF_HOSTING_PERSON_ORCOMPANY)))
            .andExpect(jsonPath("$.[*].stayingLocationName").value(hasItem(DEFAULT_STAYING_LOCATION_NAME)))
            .andExpect(jsonPath("$.[*].stayLocationFullAddress").value(hasItem(DEFAULT_STAY_LOCATION_FULL_ADDRESS)))
            .andExpect(jsonPath("$.[*].stayLocationTelephoneNumber").value(hasItem(DEFAULT_STAY_LOCATION_TELEPHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].stayLocationEmail").value(hasItem(DEFAULT_STAY_LOCATION_EMAIL)))
            .andExpect(jsonPath("$.[*].whoCoversCostOfApplicantsStay").value(hasItem(DEFAULT_WHO_COVERS_COST_OF_APPLICANTS_STAY)));
    }
    
    @Test
    @Transactional
    public void getVisaApplicationStay() throws Exception {
        // Initialize the database
        visaApplicationStayRepository.saveAndFlush(visaApplicationStay);

        // Get the visaApplicationStay
        restVisaApplicationStayMockMvc.perform(get("/api/visa-application-stays/{id}", visaApplicationStay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visaApplicationStay.getId().intValue()))
            .andExpect(jsonPath("$.durationOfProposedStayInDays").value(DEFAULT_DURATION_OF_PROPOSED_STAY_IN_DAYS))
            .andExpect(jsonPath("$.nameOfHostingPersonOrcompany").value(DEFAULT_NAME_OF_HOSTING_PERSON_ORCOMPANY))
            .andExpect(jsonPath("$.stayingLocationName").value(DEFAULT_STAYING_LOCATION_NAME))
            .andExpect(jsonPath("$.stayLocationFullAddress").value(DEFAULT_STAY_LOCATION_FULL_ADDRESS))
            .andExpect(jsonPath("$.stayLocationTelephoneNumber").value(DEFAULT_STAY_LOCATION_TELEPHONE_NUMBER))
            .andExpect(jsonPath("$.stayLocationEmail").value(DEFAULT_STAY_LOCATION_EMAIL))
            .andExpect(jsonPath("$.whoCoversCostOfApplicantsStay").value(DEFAULT_WHO_COVERS_COST_OF_APPLICANTS_STAY));
    }
    @Test
    @Transactional
    public void getNonExistingVisaApplicationStay() throws Exception {
        // Get the visaApplicationStay
        restVisaApplicationStayMockMvc.perform(get("/api/visa-application-stays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisaApplicationStay() throws Exception {
        // Initialize the database
        visaApplicationStayRepository.saveAndFlush(visaApplicationStay);

        int databaseSizeBeforeUpdate = visaApplicationStayRepository.findAll().size();

        // Update the visaApplicationStay
        VisaApplicationStay updatedVisaApplicationStay = visaApplicationStayRepository.findById(visaApplicationStay.getId()).get();
        // Disconnect from session so that the updates on updatedVisaApplicationStay are not directly saved in db
        em.detach(updatedVisaApplicationStay);
        updatedVisaApplicationStay
            .durationOfProposedStayInDays(UPDATED_DURATION_OF_PROPOSED_STAY_IN_DAYS)
            .nameOfHostingPersonOrcompany(UPDATED_NAME_OF_HOSTING_PERSON_ORCOMPANY)
            .stayingLocationName(UPDATED_STAYING_LOCATION_NAME)
            .stayLocationFullAddress(UPDATED_STAY_LOCATION_FULL_ADDRESS)
            .stayLocationTelephoneNumber(UPDATED_STAY_LOCATION_TELEPHONE_NUMBER)
            .stayLocationEmail(UPDATED_STAY_LOCATION_EMAIL)
            .whoCoversCostOfApplicantsStay(UPDATED_WHO_COVERS_COST_OF_APPLICANTS_STAY);
        VisaApplicationStayDTO visaApplicationStayDTO = visaApplicationStayMapper.toDto(updatedVisaApplicationStay);

        restVisaApplicationStayMockMvc.perform(put("/api/visa-application-stays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationStayDTO)))
            .andExpect(status().isOk());

        // Validate the VisaApplicationStay in the database
        List<VisaApplicationStay> visaApplicationStayList = visaApplicationStayRepository.findAll();
        assertThat(visaApplicationStayList).hasSize(databaseSizeBeforeUpdate);
        VisaApplicationStay testVisaApplicationStay = visaApplicationStayList.get(visaApplicationStayList.size() - 1);
        assertThat(testVisaApplicationStay.getDurationOfProposedStayInDays()).isEqualTo(UPDATED_DURATION_OF_PROPOSED_STAY_IN_DAYS);
        assertThat(testVisaApplicationStay.getNameOfHostingPersonOrcompany()).isEqualTo(UPDATED_NAME_OF_HOSTING_PERSON_ORCOMPANY);
        assertThat(testVisaApplicationStay.getStayingLocationName()).isEqualTo(UPDATED_STAYING_LOCATION_NAME);
        assertThat(testVisaApplicationStay.getStayLocationFullAddress()).isEqualTo(UPDATED_STAY_LOCATION_FULL_ADDRESS);
        assertThat(testVisaApplicationStay.getStayLocationTelephoneNumber()).isEqualTo(UPDATED_STAY_LOCATION_TELEPHONE_NUMBER);
        assertThat(testVisaApplicationStay.getStayLocationEmail()).isEqualTo(UPDATED_STAY_LOCATION_EMAIL);
        assertThat(testVisaApplicationStay.getWhoCoversCostOfApplicantsStay()).isEqualTo(UPDATED_WHO_COVERS_COST_OF_APPLICANTS_STAY);

        // Validate the VisaApplicationStay in Elasticsearch
        verify(mockVisaApplicationStaySearchRepository, times(1)).save(testVisaApplicationStay);
    }

    @Test
    @Transactional
    public void updateNonExistingVisaApplicationStay() throws Exception {
        int databaseSizeBeforeUpdate = visaApplicationStayRepository.findAll().size();

        // Create the VisaApplicationStay
        VisaApplicationStayDTO visaApplicationStayDTO = visaApplicationStayMapper.toDto(visaApplicationStay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisaApplicationStayMockMvc.perform(put("/api/visa-application-stays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visaApplicationStayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VisaApplicationStay in the database
        List<VisaApplicationStay> visaApplicationStayList = visaApplicationStayRepository.findAll();
        assertThat(visaApplicationStayList).hasSize(databaseSizeBeforeUpdate);

        // Validate the VisaApplicationStay in Elasticsearch
        verify(mockVisaApplicationStaySearchRepository, times(0)).save(visaApplicationStay);
    }

    @Test
    @Transactional
    public void deleteVisaApplicationStay() throws Exception {
        // Initialize the database
        visaApplicationStayRepository.saveAndFlush(visaApplicationStay);

        int databaseSizeBeforeDelete = visaApplicationStayRepository.findAll().size();

        // Delete the visaApplicationStay
        restVisaApplicationStayMockMvc.perform(delete("/api/visa-application-stays/{id}", visaApplicationStay.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VisaApplicationStay> visaApplicationStayList = visaApplicationStayRepository.findAll();
        assertThat(visaApplicationStayList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the VisaApplicationStay in Elasticsearch
        verify(mockVisaApplicationStaySearchRepository, times(1)).deleteById(visaApplicationStay.getId());
    }

    @Test
    @Transactional
    public void searchVisaApplicationStay() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        visaApplicationStayRepository.saveAndFlush(visaApplicationStay);
        when(mockVisaApplicationStaySearchRepository.search(queryStringQuery("id:" + visaApplicationStay.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(visaApplicationStay), PageRequest.of(0, 1), 1));

        // Search the visaApplicationStay
        restVisaApplicationStayMockMvc.perform(get("/api/_search/visa-application-stays?query=id:" + visaApplicationStay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visaApplicationStay.getId().intValue())))
            .andExpect(jsonPath("$.[*].durationOfProposedStayInDays").value(hasItem(DEFAULT_DURATION_OF_PROPOSED_STAY_IN_DAYS)))
            .andExpect(jsonPath("$.[*].nameOfHostingPersonOrcompany").value(hasItem(DEFAULT_NAME_OF_HOSTING_PERSON_ORCOMPANY)))
            .andExpect(jsonPath("$.[*].stayingLocationName").value(hasItem(DEFAULT_STAYING_LOCATION_NAME)))
            .andExpect(jsonPath("$.[*].stayLocationFullAddress").value(hasItem(DEFAULT_STAY_LOCATION_FULL_ADDRESS)))
            .andExpect(jsonPath("$.[*].stayLocationTelephoneNumber").value(hasItem(DEFAULT_STAY_LOCATION_TELEPHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].stayLocationEmail").value(hasItem(DEFAULT_STAY_LOCATION_EMAIL)))
            .andExpect(jsonPath("$.[*].whoCoversCostOfApplicantsStay").value(hasItem(DEFAULT_WHO_COVERS_COST_OF_APPLICANTS_STAY)));
    }
}
