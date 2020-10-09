package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.SomalivisaserverApp;
import so.gov.mfa.visa.domain.ApplicantContactInfo;
import so.gov.mfa.visa.repository.ApplicantContactInfoRepository;
import so.gov.mfa.visa.repository.search.ApplicantContactInfoSearchRepository;
import so.gov.mfa.visa.service.ApplicantContactInfoService;
import so.gov.mfa.visa.service.dto.ApplicantContactInfoDTO;
import so.gov.mfa.visa.service.mapper.ApplicantContactInfoMapper;

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
 * Integration tests for the {@link ApplicantContactInfoResource} REST controller.
 */
@SpringBootTest(classes = SomalivisaserverApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplicantContactInfoResourceIT {

    private static final String DEFAULT_APPLICANTS_HOME_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_APPLICANTS_HOME_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYER = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYER = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYERS_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYERS_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private ApplicantContactInfoRepository applicantContactInfoRepository;

    @Autowired
    private ApplicantContactInfoMapper applicantContactInfoMapper;

    @Autowired
    private ApplicantContactInfoService applicantContactInfoService;

    /**
     * This repository is mocked in the so.gov.mfa.visa.repository.search test package.
     *
     * @see so.gov.mfa.visa.repository.search.ApplicantContactInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ApplicantContactInfoSearchRepository mockApplicantContactInfoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantContactInfoMockMvc;

    private ApplicantContactInfo applicantContactInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantContactInfo createEntity(EntityManager em) {
        ApplicantContactInfo applicantContactInfo = new ApplicantContactInfo()
            .applicantsHomeAddress(DEFAULT_APPLICANTS_HOME_ADDRESS)
            .telephoneNumber(DEFAULT_TELEPHONE_NUMBER)
            .email(DEFAULT_EMAIL)
            .employer(DEFAULT_EMPLOYER)
            .employersAddress(DEFAULT_EMPLOYERS_ADDRESS);
        return applicantContactInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantContactInfo createUpdatedEntity(EntityManager em) {
        ApplicantContactInfo applicantContactInfo = new ApplicantContactInfo()
            .applicantsHomeAddress(UPDATED_APPLICANTS_HOME_ADDRESS)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .employer(UPDATED_EMPLOYER)
            .employersAddress(UPDATED_EMPLOYERS_ADDRESS);
        return applicantContactInfo;
    }

    @BeforeEach
    public void initTest() {
        applicantContactInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicantContactInfo() throws Exception {
        int databaseSizeBeforeCreate = applicantContactInfoRepository.findAll().size();
        // Create the ApplicantContactInfo
        ApplicantContactInfoDTO applicantContactInfoDTO = applicantContactInfoMapper.toDto(applicantContactInfo);
        restApplicantContactInfoMockMvc.perform(post("/api/applicant-contact-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantContactInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicantContactInfo in the database
        List<ApplicantContactInfo> applicantContactInfoList = applicantContactInfoRepository.findAll();
        assertThat(applicantContactInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantContactInfo testApplicantContactInfo = applicantContactInfoList.get(applicantContactInfoList.size() - 1);
        assertThat(testApplicantContactInfo.getApplicantsHomeAddress()).isEqualTo(DEFAULT_APPLICANTS_HOME_ADDRESS);
        assertThat(testApplicantContactInfo.getTelephoneNumber()).isEqualTo(DEFAULT_TELEPHONE_NUMBER);
        assertThat(testApplicantContactInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testApplicantContactInfo.getEmployer()).isEqualTo(DEFAULT_EMPLOYER);
        assertThat(testApplicantContactInfo.getEmployersAddress()).isEqualTo(DEFAULT_EMPLOYERS_ADDRESS);

        // Validate the ApplicantContactInfo in Elasticsearch
        verify(mockApplicantContactInfoSearchRepository, times(1)).save(testApplicantContactInfo);
    }

    @Test
    @Transactional
    public void createApplicantContactInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantContactInfoRepository.findAll().size();

        // Create the ApplicantContactInfo with an existing ID
        applicantContactInfo.setId(1L);
        ApplicantContactInfoDTO applicantContactInfoDTO = applicantContactInfoMapper.toDto(applicantContactInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantContactInfoMockMvc.perform(post("/api/applicant-contact-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantContactInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantContactInfo in the database
        List<ApplicantContactInfo> applicantContactInfoList = applicantContactInfoRepository.findAll();
        assertThat(applicantContactInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ApplicantContactInfo in Elasticsearch
        verify(mockApplicantContactInfoSearchRepository, times(0)).save(applicantContactInfo);
    }


    @Test
    @Transactional
    public void checkApplicantsHomeAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantContactInfoRepository.findAll().size();
        // set the field null
        applicantContactInfo.setApplicantsHomeAddress(null);

        // Create the ApplicantContactInfo, which fails.
        ApplicantContactInfoDTO applicantContactInfoDTO = applicantContactInfoMapper.toDto(applicantContactInfo);


        restApplicantContactInfoMockMvc.perform(post("/api/applicant-contact-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantContactInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicantContactInfo> applicantContactInfoList = applicantContactInfoRepository.findAll();
        assertThat(applicantContactInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantContactInfoRepository.findAll().size();
        // set the field null
        applicantContactInfo.setTelephoneNumber(null);

        // Create the ApplicantContactInfo, which fails.
        ApplicantContactInfoDTO applicantContactInfoDTO = applicantContactInfoMapper.toDto(applicantContactInfo);


        restApplicantContactInfoMockMvc.perform(post("/api/applicant-contact-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantContactInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicantContactInfo> applicantContactInfoList = applicantContactInfoRepository.findAll();
        assertThat(applicantContactInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantContactInfoRepository.findAll().size();
        // set the field null
        applicantContactInfo.setEmail(null);

        // Create the ApplicantContactInfo, which fails.
        ApplicantContactInfoDTO applicantContactInfoDTO = applicantContactInfoMapper.toDto(applicantContactInfo);


        restApplicantContactInfoMockMvc.perform(post("/api/applicant-contact-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantContactInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicantContactInfo> applicantContactInfoList = applicantContactInfoRepository.findAll();
        assertThat(applicantContactInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicantContactInfos() throws Exception {
        // Initialize the database
        applicantContactInfoRepository.saveAndFlush(applicantContactInfo);

        // Get all the applicantContactInfoList
        restApplicantContactInfoMockMvc.perform(get("/api/applicant-contact-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantContactInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicantsHomeAddress").value(hasItem(DEFAULT_APPLICANTS_HOME_ADDRESS)))
            .andExpect(jsonPath("$.[*].telephoneNumber").value(hasItem(DEFAULT_TELEPHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].employer").value(hasItem(DEFAULT_EMPLOYER)))
            .andExpect(jsonPath("$.[*].employersAddress").value(hasItem(DEFAULT_EMPLOYERS_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getApplicantContactInfo() throws Exception {
        // Initialize the database
        applicantContactInfoRepository.saveAndFlush(applicantContactInfo);

        // Get the applicantContactInfo
        restApplicantContactInfoMockMvc.perform(get("/api/applicant-contact-infos/{id}", applicantContactInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicantContactInfo.getId().intValue()))
            .andExpect(jsonPath("$.applicantsHomeAddress").value(DEFAULT_APPLICANTS_HOME_ADDRESS))
            .andExpect(jsonPath("$.telephoneNumber").value(DEFAULT_TELEPHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.employer").value(DEFAULT_EMPLOYER))
            .andExpect(jsonPath("$.employersAddress").value(DEFAULT_EMPLOYERS_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingApplicantContactInfo() throws Exception {
        // Get the applicantContactInfo
        restApplicantContactInfoMockMvc.perform(get("/api/applicant-contact-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicantContactInfo() throws Exception {
        // Initialize the database
        applicantContactInfoRepository.saveAndFlush(applicantContactInfo);

        int databaseSizeBeforeUpdate = applicantContactInfoRepository.findAll().size();

        // Update the applicantContactInfo
        ApplicantContactInfo updatedApplicantContactInfo = applicantContactInfoRepository.findById(applicantContactInfo.getId()).get();
        // Disconnect from session so that the updates on updatedApplicantContactInfo are not directly saved in db
        em.detach(updatedApplicantContactInfo);
        updatedApplicantContactInfo
            .applicantsHomeAddress(UPDATED_APPLICANTS_HOME_ADDRESS)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .employer(UPDATED_EMPLOYER)
            .employersAddress(UPDATED_EMPLOYERS_ADDRESS);
        ApplicantContactInfoDTO applicantContactInfoDTO = applicantContactInfoMapper.toDto(updatedApplicantContactInfo);

        restApplicantContactInfoMockMvc.perform(put("/api/applicant-contact-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantContactInfoDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicantContactInfo in the database
        List<ApplicantContactInfo> applicantContactInfoList = applicantContactInfoRepository.findAll();
        assertThat(applicantContactInfoList).hasSize(databaseSizeBeforeUpdate);
        ApplicantContactInfo testApplicantContactInfo = applicantContactInfoList.get(applicantContactInfoList.size() - 1);
        assertThat(testApplicantContactInfo.getApplicantsHomeAddress()).isEqualTo(UPDATED_APPLICANTS_HOME_ADDRESS);
        assertThat(testApplicantContactInfo.getTelephoneNumber()).isEqualTo(UPDATED_TELEPHONE_NUMBER);
        assertThat(testApplicantContactInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApplicantContactInfo.getEmployer()).isEqualTo(UPDATED_EMPLOYER);
        assertThat(testApplicantContactInfo.getEmployersAddress()).isEqualTo(UPDATED_EMPLOYERS_ADDRESS);

        // Validate the ApplicantContactInfo in Elasticsearch
        verify(mockApplicantContactInfoSearchRepository, times(1)).save(testApplicantContactInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicantContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = applicantContactInfoRepository.findAll().size();

        // Create the ApplicantContactInfo
        ApplicantContactInfoDTO applicantContactInfoDTO = applicantContactInfoMapper.toDto(applicantContactInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantContactInfoMockMvc.perform(put("/api/applicant-contact-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantContactInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantContactInfo in the database
        List<ApplicantContactInfo> applicantContactInfoList = applicantContactInfoRepository.findAll();
        assertThat(applicantContactInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ApplicantContactInfo in Elasticsearch
        verify(mockApplicantContactInfoSearchRepository, times(0)).save(applicantContactInfo);
    }

    @Test
    @Transactional
    public void deleteApplicantContactInfo() throws Exception {
        // Initialize the database
        applicantContactInfoRepository.saveAndFlush(applicantContactInfo);

        int databaseSizeBeforeDelete = applicantContactInfoRepository.findAll().size();

        // Delete the applicantContactInfo
        restApplicantContactInfoMockMvc.perform(delete("/api/applicant-contact-infos/{id}", applicantContactInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicantContactInfo> applicantContactInfoList = applicantContactInfoRepository.findAll();
        assertThat(applicantContactInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ApplicantContactInfo in Elasticsearch
        verify(mockApplicantContactInfoSearchRepository, times(1)).deleteById(applicantContactInfo.getId());
    }

    @Test
    @Transactional
    public void searchApplicantContactInfo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        applicantContactInfoRepository.saveAndFlush(applicantContactInfo);
        when(mockApplicantContactInfoSearchRepository.search(queryStringQuery("id:" + applicantContactInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(applicantContactInfo), PageRequest.of(0, 1), 1));

        // Search the applicantContactInfo
        restApplicantContactInfoMockMvc.perform(get("/api/_search/applicant-contact-infos?query=id:" + applicantContactInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantContactInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicantsHomeAddress").value(hasItem(DEFAULT_APPLICANTS_HOME_ADDRESS)))
            .andExpect(jsonPath("$.[*].telephoneNumber").value(hasItem(DEFAULT_TELEPHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].employer").value(hasItem(DEFAULT_EMPLOYER)))
            .andExpect(jsonPath("$.[*].employersAddress").value(hasItem(DEFAULT_EMPLOYERS_ADDRESS)));
    }
}
