package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.SomalivisaserverApp;
import so.gov.mfa.visa.domain.ElectronicVisa;
import so.gov.mfa.visa.repository.ElectronicVisaRepository;
import so.gov.mfa.visa.repository.search.ElectronicVisaSearchRepository;
import so.gov.mfa.visa.service.ElectronicVisaService;
import so.gov.mfa.visa.service.dto.ElectronicVisaDTO;
import so.gov.mfa.visa.service.mapper.ElectronicVisaMapper;

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
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ElectronicVisaResource} REST controller.
 */
@SpringBootTest(classes = SomalivisaserverApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ElectronicVisaResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VISA_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VISA_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_TRAVEL_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_TRAVEL_DOCUMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TRAVEL_DOCUMENT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRAVEL_DOCUMENT_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TRAVEL_DOCUMENT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRAVEL_DOCUMENT_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TRAVEL_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_TRAVEL_PURPOSE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VISA_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VISA_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VISA_VALID_UNTIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VISA_VALID_UNTIL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VISA_VALIDITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VISA_VALIDITY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VISA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VISA_TYPE = "BBBBBBBBBB";

    @Autowired
    private ElectronicVisaRepository electronicVisaRepository;

    @Autowired
    private ElectronicVisaMapper electronicVisaMapper;

    @Autowired
    private ElectronicVisaService electronicVisaService;

    /**
     * This repository is mocked in the so.gov.mfa.visa.repository.search test package.
     *
     * @see so.gov.mfa.visa.repository.search.ElectronicVisaSearchRepositoryMockConfiguration
     */
    @Autowired
    private ElectronicVisaSearchRepository mockElectronicVisaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElectronicVisaMockMvc;

    private ElectronicVisa electronicVisa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElectronicVisa createEntity(EntityManager em) {
        ElectronicVisa electronicVisa = new ElectronicVisa()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .visaNumber(DEFAULT_VISA_NUMBER)
            .barcode(DEFAULT_BARCODE)
            .nationality(DEFAULT_NATIONALITY)
            .placeOfBirth(DEFAULT_PLACE_OF_BIRTH)
            .travelDocument(DEFAULT_TRAVEL_DOCUMENT)
            .travelDocumentIssueDate(DEFAULT_TRAVEL_DOCUMENT_ISSUE_DATE)
            .travelDocumentExpiryDate(DEFAULT_TRAVEL_DOCUMENT_EXPIRY_DATE)
            .travelPurpose(DEFAULT_TRAVEL_PURPOSE)
            .visaValidFrom(DEFAULT_VISA_VALID_FROM)
            .visaValidUntil(DEFAULT_VISA_VALID_UNTIL)
            .visaValidityType(DEFAULT_VISA_VALIDITY_TYPE)
            .visaType(DEFAULT_VISA_TYPE);
        return electronicVisa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElectronicVisa createUpdatedEntity(EntityManager em) {
        ElectronicVisa electronicVisa = new ElectronicVisa()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .visaNumber(UPDATED_VISA_NUMBER)
            .barcode(UPDATED_BARCODE)
            .nationality(UPDATED_NATIONALITY)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .travelDocument(UPDATED_TRAVEL_DOCUMENT)
            .travelDocumentIssueDate(UPDATED_TRAVEL_DOCUMENT_ISSUE_DATE)
            .travelDocumentExpiryDate(UPDATED_TRAVEL_DOCUMENT_EXPIRY_DATE)
            .travelPurpose(UPDATED_TRAVEL_PURPOSE)
            .visaValidFrom(UPDATED_VISA_VALID_FROM)
            .visaValidUntil(UPDATED_VISA_VALID_UNTIL)
            .visaValidityType(UPDATED_VISA_VALIDITY_TYPE)
            .visaType(UPDATED_VISA_TYPE);
        return electronicVisa;
    }

    @BeforeEach
    public void initTest() {
        electronicVisa = createEntity(em);
    }

    @Test
    @Transactional
    public void createElectronicVisa() throws Exception {
        int databaseSizeBeforeCreate = electronicVisaRepository.findAll().size();
        // Create the ElectronicVisa
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);
        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isCreated());

        // Validate the ElectronicVisa in the database
        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeCreate + 1);
        ElectronicVisa testElectronicVisa = electronicVisaList.get(electronicVisaList.size() - 1);
        assertThat(testElectronicVisa.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testElectronicVisa.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testElectronicVisa.getVisaNumber()).isEqualTo(DEFAULT_VISA_NUMBER);
        assertThat(testElectronicVisa.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testElectronicVisa.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testElectronicVisa.getPlaceOfBirth()).isEqualTo(DEFAULT_PLACE_OF_BIRTH);
        assertThat(testElectronicVisa.getTravelDocument()).isEqualTo(DEFAULT_TRAVEL_DOCUMENT);
        assertThat(testElectronicVisa.getTravelDocumentIssueDate()).isEqualTo(DEFAULT_TRAVEL_DOCUMENT_ISSUE_DATE);
        assertThat(testElectronicVisa.getTravelDocumentExpiryDate()).isEqualTo(DEFAULT_TRAVEL_DOCUMENT_EXPIRY_DATE);
        assertThat(testElectronicVisa.getTravelPurpose()).isEqualTo(DEFAULT_TRAVEL_PURPOSE);
        assertThat(testElectronicVisa.getVisaValidFrom()).isEqualTo(DEFAULT_VISA_VALID_FROM);
        assertThat(testElectronicVisa.getVisaValidUntil()).isEqualTo(DEFAULT_VISA_VALID_UNTIL);
        assertThat(testElectronicVisa.getVisaValidityType()).isEqualTo(DEFAULT_VISA_VALIDITY_TYPE);
        assertThat(testElectronicVisa.getVisaType()).isEqualTo(DEFAULT_VISA_TYPE);

        // Validate the ElectronicVisa in Elasticsearch
        verify(mockElectronicVisaSearchRepository, times(1)).save(testElectronicVisa);
    }

    @Test
    @Transactional
    public void createElectronicVisaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = electronicVisaRepository.findAll().size();

        // Create the ElectronicVisa with an existing ID
        electronicVisa.setId(1L);
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ElectronicVisa in the database
        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeCreate);

        // Validate the ElectronicVisa in Elasticsearch
        verify(mockElectronicVisaSearchRepository, times(0)).save(electronicVisa);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setFirstName(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setLastName(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisaNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setVisaNumber(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBarcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setBarcode(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setNationality(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlaceOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setPlaceOfBirth(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTravelDocumentIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setTravelDocument(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTravelDocumentIssueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setTravelDocumentIssueDate(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTravelDocumentExpiryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setTravelDocumentExpiryDate(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTravelPurposeIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setTravelPurpose(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisaValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setVisaValidFrom(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisaValidUntilIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setVisaValidUntil(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisaValidityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setVisaValidityType(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = electronicVisaRepository.findAll().size();
        // set the field null
        electronicVisa.setVisaType(null);

        // Create the ElectronicVisa, which fails.
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);


        restElectronicVisaMockMvc.perform(post("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllElectronicVisas() throws Exception {
        // Initialize the database
        electronicVisaRepository.saveAndFlush(electronicVisa);

        // Get all the electronicVisaList
        restElectronicVisaMockMvc.perform(get("/api/electronic-visas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(electronicVisa.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].visaNumber").value(hasItem(DEFAULT_VISA_NUMBER)))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].travelDocument").value(hasItem(DEFAULT_TRAVEL_DOCUMENT)))
            .andExpect(jsonPath("$.[*].travelDocumentIssueDate").value(hasItem(DEFAULT_TRAVEL_DOCUMENT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].travelDocumentExpiryDate").value(hasItem(DEFAULT_TRAVEL_DOCUMENT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].travelPurpose").value(hasItem(DEFAULT_TRAVEL_PURPOSE)))
            .andExpect(jsonPath("$.[*].visaValidFrom").value(hasItem(DEFAULT_VISA_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].visaValidUntil").value(hasItem(DEFAULT_VISA_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].visaValidityType").value(hasItem(DEFAULT_VISA_VALIDITY_TYPE)))
            .andExpect(jsonPath("$.[*].visaType").value(hasItem(DEFAULT_VISA_TYPE)));
    }
    
    @Test
    @Transactional
    public void getElectronicVisa() throws Exception {
        // Initialize the database
        electronicVisaRepository.saveAndFlush(electronicVisa);

        // Get the electronicVisa
        restElectronicVisaMockMvc.perform(get("/api/electronic-visas/{id}", electronicVisa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(electronicVisa.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.visaNumber").value(DEFAULT_VISA_NUMBER))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.placeOfBirth").value(DEFAULT_PLACE_OF_BIRTH))
            .andExpect(jsonPath("$.travelDocument").value(DEFAULT_TRAVEL_DOCUMENT))
            .andExpect(jsonPath("$.travelDocumentIssueDate").value(DEFAULT_TRAVEL_DOCUMENT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.travelDocumentExpiryDate").value(DEFAULT_TRAVEL_DOCUMENT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.travelPurpose").value(DEFAULT_TRAVEL_PURPOSE))
            .andExpect(jsonPath("$.visaValidFrom").value(DEFAULT_VISA_VALID_FROM.toString()))
            .andExpect(jsonPath("$.visaValidUntil").value(DEFAULT_VISA_VALID_UNTIL.toString()))
            .andExpect(jsonPath("$.visaValidityType").value(DEFAULT_VISA_VALIDITY_TYPE))
            .andExpect(jsonPath("$.visaType").value(DEFAULT_VISA_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingElectronicVisa() throws Exception {
        // Get the electronicVisa
        restElectronicVisaMockMvc.perform(get("/api/electronic-visas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElectronicVisa() throws Exception {
        // Initialize the database
        electronicVisaRepository.saveAndFlush(electronicVisa);

        int databaseSizeBeforeUpdate = electronicVisaRepository.findAll().size();

        // Update the electronicVisa
        ElectronicVisa updatedElectronicVisa = electronicVisaRepository.findById(electronicVisa.getId()).get();
        // Disconnect from session so that the updates on updatedElectronicVisa are not directly saved in db
        em.detach(updatedElectronicVisa);
        updatedElectronicVisa
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .visaNumber(UPDATED_VISA_NUMBER)
            .barcode(UPDATED_BARCODE)
            .nationality(UPDATED_NATIONALITY)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .travelDocument(UPDATED_TRAVEL_DOCUMENT)
            .travelDocumentIssueDate(UPDATED_TRAVEL_DOCUMENT_ISSUE_DATE)
            .travelDocumentExpiryDate(UPDATED_TRAVEL_DOCUMENT_EXPIRY_DATE)
            .travelPurpose(UPDATED_TRAVEL_PURPOSE)
            .visaValidFrom(UPDATED_VISA_VALID_FROM)
            .visaValidUntil(UPDATED_VISA_VALID_UNTIL)
            .visaValidityType(UPDATED_VISA_VALIDITY_TYPE)
            .visaType(UPDATED_VISA_TYPE);
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(updatedElectronicVisa);

        restElectronicVisaMockMvc.perform(put("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isOk());

        // Validate the ElectronicVisa in the database
        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeUpdate);
        ElectronicVisa testElectronicVisa = electronicVisaList.get(electronicVisaList.size() - 1);
        assertThat(testElectronicVisa.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testElectronicVisa.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testElectronicVisa.getVisaNumber()).isEqualTo(UPDATED_VISA_NUMBER);
        assertThat(testElectronicVisa.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testElectronicVisa.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testElectronicVisa.getPlaceOfBirth()).isEqualTo(UPDATED_PLACE_OF_BIRTH);
        assertThat(testElectronicVisa.getTravelDocument()).isEqualTo(UPDATED_TRAVEL_DOCUMENT);
        assertThat(testElectronicVisa.getTravelDocumentIssueDate()).isEqualTo(UPDATED_TRAVEL_DOCUMENT_ISSUE_DATE);
        assertThat(testElectronicVisa.getTravelDocumentExpiryDate()).isEqualTo(UPDATED_TRAVEL_DOCUMENT_EXPIRY_DATE);
        assertThat(testElectronicVisa.getTravelPurpose()).isEqualTo(UPDATED_TRAVEL_PURPOSE);
        assertThat(testElectronicVisa.getVisaValidFrom()).isEqualTo(UPDATED_VISA_VALID_FROM);
        assertThat(testElectronicVisa.getVisaValidUntil()).isEqualTo(UPDATED_VISA_VALID_UNTIL);
        assertThat(testElectronicVisa.getVisaValidityType()).isEqualTo(UPDATED_VISA_VALIDITY_TYPE);
        assertThat(testElectronicVisa.getVisaType()).isEqualTo(UPDATED_VISA_TYPE);

        // Validate the ElectronicVisa in Elasticsearch
        verify(mockElectronicVisaSearchRepository, times(1)).save(testElectronicVisa);
    }

    @Test
    @Transactional
    public void updateNonExistingElectronicVisa() throws Exception {
        int databaseSizeBeforeUpdate = electronicVisaRepository.findAll().size();

        // Create the ElectronicVisa
        ElectronicVisaDTO electronicVisaDTO = electronicVisaMapper.toDto(electronicVisa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElectronicVisaMockMvc.perform(put("/api/electronic-visas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electronicVisaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ElectronicVisa in the database
        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ElectronicVisa in Elasticsearch
        verify(mockElectronicVisaSearchRepository, times(0)).save(electronicVisa);
    }

    @Test
    @Transactional
    public void deleteElectronicVisa() throws Exception {
        // Initialize the database
        electronicVisaRepository.saveAndFlush(electronicVisa);

        int databaseSizeBeforeDelete = electronicVisaRepository.findAll().size();

        // Delete the electronicVisa
        restElectronicVisaMockMvc.perform(delete("/api/electronic-visas/{id}", electronicVisa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ElectronicVisa> electronicVisaList = electronicVisaRepository.findAll();
        assertThat(electronicVisaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ElectronicVisa in Elasticsearch
        verify(mockElectronicVisaSearchRepository, times(1)).deleteById(electronicVisa.getId());
    }

    @Test
    @Transactional
    public void searchElectronicVisa() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        electronicVisaRepository.saveAndFlush(electronicVisa);
        when(mockElectronicVisaSearchRepository.search(queryStringQuery("id:" + electronicVisa.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(electronicVisa), PageRequest.of(0, 1), 1));

        // Search the electronicVisa
        restElectronicVisaMockMvc.perform(get("/api/_search/electronic-visas?query=id:" + electronicVisa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(electronicVisa.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].visaNumber").value(hasItem(DEFAULT_VISA_NUMBER)))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].travelDocument").value(hasItem(DEFAULT_TRAVEL_DOCUMENT)))
            .andExpect(jsonPath("$.[*].travelDocumentIssueDate").value(hasItem(DEFAULT_TRAVEL_DOCUMENT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].travelDocumentExpiryDate").value(hasItem(DEFAULT_TRAVEL_DOCUMENT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].travelPurpose").value(hasItem(DEFAULT_TRAVEL_PURPOSE)))
            .andExpect(jsonPath("$.[*].visaValidFrom").value(hasItem(DEFAULT_VISA_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].visaValidUntil").value(hasItem(DEFAULT_VISA_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].visaValidityType").value(hasItem(DEFAULT_VISA_VALIDITY_TYPE)))
            .andExpect(jsonPath("$.[*].visaType").value(hasItem(DEFAULT_VISA_TYPE)));
    }
}
