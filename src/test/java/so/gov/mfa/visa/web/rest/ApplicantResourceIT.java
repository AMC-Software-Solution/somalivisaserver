package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.SomalivisaserverApp;
import so.gov.mfa.visa.domain.Applicant;
import so.gov.mfa.visa.repository.ApplicantRepository;
import so.gov.mfa.visa.repository.search.ApplicantSearchRepository;
import so.gov.mfa.visa.service.ApplicantService;
import so.gov.mfa.visa.service.dto.ApplicantDTO;
import so.gov.mfa.visa.service.mapper.ApplicantMapper;

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
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import so.gov.mfa.visa.domain.enumeration.Gender;
import so.gov.mfa.visa.domain.enumeration.MaritalStatus;
/**
 * Integration tests for the {@link ApplicantResource} REST controller.
 */
@SpringBootTest(classes = SomalivisaserverApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplicantResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAMES = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAMES = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLACE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_BIRTH = "BBBBBBBBBB";

    private static final Gender DEFAULT_SEX = Gender.MALE;
    private static final Gender UPDATED_SEX = Gender.FEMALE;

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.SINGLE;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.MARRIED;

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ApplicantMapper applicantMapper;

    @Autowired
    private ApplicantService applicantService;

    /**
     * This repository is mocked in the so.gov.mfa.visa.repository.search test package.
     *
     * @see so.gov.mfa.visa.repository.search.ApplicantSearchRepositoryMockConfiguration
     */
    @Autowired
    private ApplicantSearchRepository mockApplicantSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantMockMvc;

    private Applicant applicant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicant createEntity(EntityManager em) {
        Applicant applicant = new Applicant()
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .middleNames(DEFAULT_MIDDLE_NAMES)
            .lastName(DEFAULT_LAST_NAME)
            .fullName(DEFAULT_FULL_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .placeOfBirth(DEFAULT_PLACE_OF_BIRTH)
            .sex(DEFAULT_SEX)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .occupation(DEFAULT_OCCUPATION)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return applicant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicant createUpdatedEntity(EntityManager em) {
        Applicant applicant = new Applicant()
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleNames(UPDATED_MIDDLE_NAMES)
            .lastName(UPDATED_LAST_NAME)
            .fullName(UPDATED_FULL_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .sex(UPDATED_SEX)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .occupation(UPDATED_OCCUPATION)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        return applicant;
    }

    @BeforeEach
    public void initTest() {
        applicant = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicant() throws Exception {
        int databaseSizeBeforeCreate = applicantRepository.findAll().size();
        // Create the Applicant
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);
        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isCreated());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeCreate + 1);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testApplicant.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testApplicant.getMiddleNames()).isEqualTo(DEFAULT_MIDDLE_NAMES);
        assertThat(testApplicant.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testApplicant.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testApplicant.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testApplicant.getPlaceOfBirth()).isEqualTo(DEFAULT_PLACE_OF_BIRTH);
        assertThat(testApplicant.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testApplicant.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testApplicant.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testApplicant.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testApplicant.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);

        // Validate the Applicant in Elasticsearch
        verify(mockApplicantSearchRepository, times(1)).save(testApplicant);
    }

    @Test
    @Transactional
    public void createApplicantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantRepository.findAll().size();

        // Create the Applicant with an existing ID
        applicant.setId(1L);
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeCreate);

        // Validate the Applicant in Elasticsearch
        verify(mockApplicantSearchRepository, times(0)).save(applicant);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setTitle(null);

        // Create the Applicant, which fails.
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);


        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setFirstName(null);

        // Create the Applicant, which fails.
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);


        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMiddleNamesIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setMiddleNames(null);

        // Create the Applicant, which fails.
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);


        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setLastName(null);

        // Create the Applicant, which fails.
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);


        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setDateOfBirth(null);

        // Create the Applicant, which fails.
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);


        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlaceOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setPlaceOfBirth(null);

        // Create the Applicant, which fails.
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);


        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setSex(null);

        // Create the Applicant, which fails.
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);


        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaritalStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setMaritalStatus(null);

        // Create the Applicant, which fails.
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);


        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOccupationIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setOccupation(null);

        // Create the Applicant, which fails.
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);


        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicants() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList
        restApplicantMockMvc.perform(get("/api/applicants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicant.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleNames").value(hasItem(DEFAULT_MIDDLE_NAMES)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }
    
    @Test
    @Transactional
    public void getApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get the applicant
        restApplicantMockMvc.perform(get("/api/applicants/{id}", applicant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicant.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleNames").value(DEFAULT_MIDDLE_NAMES))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.placeOfBirth").value(DEFAULT_PLACE_OF_BIRTH))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }
    @Test
    @Transactional
    public void getNonExistingApplicant() throws Exception {
        // Get the applicant
        restApplicantMockMvc.perform(get("/api/applicants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // Update the applicant
        Applicant updatedApplicant = applicantRepository.findById(applicant.getId()).get();
        // Disconnect from session so that the updates on updatedApplicant are not directly saved in db
        em.detach(updatedApplicant);
        updatedApplicant
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleNames(UPDATED_MIDDLE_NAMES)
            .lastName(UPDATED_LAST_NAME)
            .fullName(UPDATED_FULL_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .sex(UPDATED_SEX)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .occupation(UPDATED_OCCUPATION)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        ApplicantDTO applicantDTO = applicantMapper.toDto(updatedApplicant);

        restApplicantMockMvc.perform(put("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isOk());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testApplicant.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testApplicant.getMiddleNames()).isEqualTo(UPDATED_MIDDLE_NAMES);
        assertThat(testApplicant.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplicant.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testApplicant.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testApplicant.getPlaceOfBirth()).isEqualTo(UPDATED_PLACE_OF_BIRTH);
        assertThat(testApplicant.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testApplicant.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testApplicant.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testApplicant.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testApplicant.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);

        // Validate the Applicant in Elasticsearch
        verify(mockApplicantSearchRepository, times(1)).save(testApplicant);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicant() throws Exception {
        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // Create the Applicant
        ApplicantDTO applicantDTO = applicantMapper.toDto(applicant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantMockMvc.perform(put("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Applicant in Elasticsearch
        verify(mockApplicantSearchRepository, times(0)).save(applicant);
    }

    @Test
    @Transactional
    public void deleteApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        int databaseSizeBeforeDelete = applicantRepository.findAll().size();

        // Delete the applicant
        restApplicantMockMvc.perform(delete("/api/applicants/{id}", applicant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Applicant in Elasticsearch
        verify(mockApplicantSearchRepository, times(1)).deleteById(applicant.getId());
    }

    @Test
    @Transactional
    public void searchApplicant() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        when(mockApplicantSearchRepository.search(queryStringQuery("id:" + applicant.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(applicant), PageRequest.of(0, 1), 1));

        // Search the applicant
        restApplicantMockMvc.perform(get("/api/_search/applicants?query=id:" + applicant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicant.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleNames").value(hasItem(DEFAULT_MIDDLE_NAMES)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }
}
