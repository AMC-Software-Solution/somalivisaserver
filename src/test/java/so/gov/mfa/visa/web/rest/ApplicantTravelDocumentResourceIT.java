package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.SomalivisaserverApp;
import so.gov.mfa.visa.domain.ApplicantTravelDocument;
import so.gov.mfa.visa.repository.ApplicantTravelDocumentRepository;
import so.gov.mfa.visa.repository.search.ApplicantTravelDocumentSearchRepository;
import so.gov.mfa.visa.service.ApplicantTravelDocumentService;
import so.gov.mfa.visa.service.dto.ApplicantTravelDocumentDTO;
import so.gov.mfa.visa.service.mapper.ApplicantTravelDocumentMapper;

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

import so.gov.mfa.visa.domain.enumeration.TypeOfTravelDocument;
/**
 * Integration tests for the {@link ApplicantTravelDocumentResource} REST controller.
 */
@SpringBootTest(classes = SomalivisaserverApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplicantTravelDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_ISSUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_ISSUE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ISSUING_AUTHORITY = "AAAAAAAAAA";
    private static final String UPDATED_ISSUING_AUTHORITY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_PHOTO_CONTENT_TYPE = "image/png";

    private static final TypeOfTravelDocument DEFAULT_TYPE_OF_DOCUMENT = TypeOfTravelDocument.ORDINARY_PASSPORT;
    private static final TypeOfTravelDocument UPDATED_TYPE_OF_DOCUMENT = TypeOfTravelDocument.SERVICE_PASSPORT;

    @Autowired
    private ApplicantTravelDocumentRepository applicantTravelDocumentRepository;

    @Autowired
    private ApplicantTravelDocumentMapper applicantTravelDocumentMapper;

    @Autowired
    private ApplicantTravelDocumentService applicantTravelDocumentService;

    /**
     * This repository is mocked in the so.gov.mfa.visa.repository.search test package.
     *
     * @see so.gov.mfa.visa.repository.search.ApplicantTravelDocumentSearchRepositoryMockConfiguration
     */
    @Autowired
    private ApplicantTravelDocumentSearchRepository mockApplicantTravelDocumentSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantTravelDocumentMockMvc;

    private ApplicantTravelDocument applicantTravelDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantTravelDocument createEntity(EntityManager em) {
        ApplicantTravelDocument applicantTravelDocument = new ApplicantTravelDocument()
            .documentNumber(DEFAULT_DOCUMENT_NUMBER)
            .dateOfIssue(DEFAULT_DATE_OF_ISSUE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .issuingAuthority(DEFAULT_ISSUING_AUTHORITY)
            .documentPhoto(DEFAULT_DOCUMENT_PHOTO)
            .documentPhotoContentType(DEFAULT_DOCUMENT_PHOTO_CONTENT_TYPE)
            .typeOfDocument(DEFAULT_TYPE_OF_DOCUMENT);
        return applicantTravelDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantTravelDocument createUpdatedEntity(EntityManager em) {
        ApplicantTravelDocument applicantTravelDocument = new ApplicantTravelDocument()
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .dateOfIssue(UPDATED_DATE_OF_ISSUE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .issuingAuthority(UPDATED_ISSUING_AUTHORITY)
            .documentPhoto(UPDATED_DOCUMENT_PHOTO)
            .documentPhotoContentType(UPDATED_DOCUMENT_PHOTO_CONTENT_TYPE)
            .typeOfDocument(UPDATED_TYPE_OF_DOCUMENT);
        return applicantTravelDocument;
    }

    @BeforeEach
    public void initTest() {
        applicantTravelDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicantTravelDocument() throws Exception {
        int databaseSizeBeforeCreate = applicantTravelDocumentRepository.findAll().size();
        // Create the ApplicantTravelDocument
        ApplicantTravelDocumentDTO applicantTravelDocumentDTO = applicantTravelDocumentMapper.toDto(applicantTravelDocument);
        restApplicantTravelDocumentMockMvc.perform(post("/api/applicant-travel-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantTravelDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicantTravelDocument in the database
        List<ApplicantTravelDocument> applicantTravelDocumentList = applicantTravelDocumentRepository.findAll();
        assertThat(applicantTravelDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantTravelDocument testApplicantTravelDocument = applicantTravelDocumentList.get(applicantTravelDocumentList.size() - 1);
        assertThat(testApplicantTravelDocument.getDocumentNumber()).isEqualTo(DEFAULT_DOCUMENT_NUMBER);
        assertThat(testApplicantTravelDocument.getDateOfIssue()).isEqualTo(DEFAULT_DATE_OF_ISSUE);
        assertThat(testApplicantTravelDocument.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testApplicantTravelDocument.getIssuingAuthority()).isEqualTo(DEFAULT_ISSUING_AUTHORITY);
        assertThat(testApplicantTravelDocument.getDocumentPhoto()).isEqualTo(DEFAULT_DOCUMENT_PHOTO);
        assertThat(testApplicantTravelDocument.getDocumentPhotoContentType()).isEqualTo(DEFAULT_DOCUMENT_PHOTO_CONTENT_TYPE);
        assertThat(testApplicantTravelDocument.getTypeOfDocument()).isEqualTo(DEFAULT_TYPE_OF_DOCUMENT);

        // Validate the ApplicantTravelDocument in Elasticsearch
        verify(mockApplicantTravelDocumentSearchRepository, times(1)).save(testApplicantTravelDocument);
    }

    @Test
    @Transactional
    public void createApplicantTravelDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantTravelDocumentRepository.findAll().size();

        // Create the ApplicantTravelDocument with an existing ID
        applicantTravelDocument.setId(1L);
        ApplicantTravelDocumentDTO applicantTravelDocumentDTO = applicantTravelDocumentMapper.toDto(applicantTravelDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantTravelDocumentMockMvc.perform(post("/api/applicant-travel-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantTravelDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantTravelDocument in the database
        List<ApplicantTravelDocument> applicantTravelDocumentList = applicantTravelDocumentRepository.findAll();
        assertThat(applicantTravelDocumentList).hasSize(databaseSizeBeforeCreate);

        // Validate the ApplicantTravelDocument in Elasticsearch
        verify(mockApplicantTravelDocumentSearchRepository, times(0)).save(applicantTravelDocument);
    }


    @Test
    @Transactional
    public void checkDocumentNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantTravelDocumentRepository.findAll().size();
        // set the field null
        applicantTravelDocument.setDocumentNumber(null);

        // Create the ApplicantTravelDocument, which fails.
        ApplicantTravelDocumentDTO applicantTravelDocumentDTO = applicantTravelDocumentMapper.toDto(applicantTravelDocument);


        restApplicantTravelDocumentMockMvc.perform(post("/api/applicant-travel-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantTravelDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicantTravelDocument> applicantTravelDocumentList = applicantTravelDocumentRepository.findAll();
        assertThat(applicantTravelDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfIssueIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantTravelDocumentRepository.findAll().size();
        // set the field null
        applicantTravelDocument.setDateOfIssue(null);

        // Create the ApplicantTravelDocument, which fails.
        ApplicantTravelDocumentDTO applicantTravelDocumentDTO = applicantTravelDocumentMapper.toDto(applicantTravelDocument);


        restApplicantTravelDocumentMockMvc.perform(post("/api/applicant-travel-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantTravelDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicantTravelDocument> applicantTravelDocumentList = applicantTravelDocumentRepository.findAll();
        assertThat(applicantTravelDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantTravelDocumentRepository.findAll().size();
        // set the field null
        applicantTravelDocument.setExpiryDate(null);

        // Create the ApplicantTravelDocument, which fails.
        ApplicantTravelDocumentDTO applicantTravelDocumentDTO = applicantTravelDocumentMapper.toDto(applicantTravelDocument);


        restApplicantTravelDocumentMockMvc.perform(post("/api/applicant-travel-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantTravelDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicantTravelDocument> applicantTravelDocumentList = applicantTravelDocumentRepository.findAll();
        assertThat(applicantTravelDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssuingAuthorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantTravelDocumentRepository.findAll().size();
        // set the field null
        applicantTravelDocument.setIssuingAuthority(null);

        // Create the ApplicantTravelDocument, which fails.
        ApplicantTravelDocumentDTO applicantTravelDocumentDTO = applicantTravelDocumentMapper.toDto(applicantTravelDocument);


        restApplicantTravelDocumentMockMvc.perform(post("/api/applicant-travel-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantTravelDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicantTravelDocument> applicantTravelDocumentList = applicantTravelDocumentRepository.findAll();
        assertThat(applicantTravelDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeOfDocumentIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantTravelDocumentRepository.findAll().size();
        // set the field null
        applicantTravelDocument.setTypeOfDocument(null);

        // Create the ApplicantTravelDocument, which fails.
        ApplicantTravelDocumentDTO applicantTravelDocumentDTO = applicantTravelDocumentMapper.toDto(applicantTravelDocument);


        restApplicantTravelDocumentMockMvc.perform(post("/api/applicant-travel-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantTravelDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicantTravelDocument> applicantTravelDocumentList = applicantTravelDocumentRepository.findAll();
        assertThat(applicantTravelDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicantTravelDocuments() throws Exception {
        // Initialize the database
        applicantTravelDocumentRepository.saveAndFlush(applicantTravelDocument);

        // Get all the applicantTravelDocumentList
        restApplicantTravelDocumentMockMvc.perform(get("/api/applicant-travel-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantTravelDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfIssue").value(hasItem(DEFAULT_DATE_OF_ISSUE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].issuingAuthority").value(hasItem(DEFAULT_ISSUING_AUTHORITY)))
            .andExpect(jsonPath("$.[*].documentPhotoContentType").value(hasItem(DEFAULT_DOCUMENT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_PHOTO))))
            .andExpect(jsonPath("$.[*].typeOfDocument").value(hasItem(DEFAULT_TYPE_OF_DOCUMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicantTravelDocument() throws Exception {
        // Initialize the database
        applicantTravelDocumentRepository.saveAndFlush(applicantTravelDocument);

        // Get the applicantTravelDocument
        restApplicantTravelDocumentMockMvc.perform(get("/api/applicant-travel-documents/{id}", applicantTravelDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicantTravelDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentNumber").value(DEFAULT_DOCUMENT_NUMBER))
            .andExpect(jsonPath("$.dateOfIssue").value(DEFAULT_DATE_OF_ISSUE.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.issuingAuthority").value(DEFAULT_ISSUING_AUTHORITY))
            .andExpect(jsonPath("$.documentPhotoContentType").value(DEFAULT_DOCUMENT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentPhoto").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT_PHOTO)))
            .andExpect(jsonPath("$.typeOfDocument").value(DEFAULT_TYPE_OF_DOCUMENT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingApplicantTravelDocument() throws Exception {
        // Get the applicantTravelDocument
        restApplicantTravelDocumentMockMvc.perform(get("/api/applicant-travel-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicantTravelDocument() throws Exception {
        // Initialize the database
        applicantTravelDocumentRepository.saveAndFlush(applicantTravelDocument);

        int databaseSizeBeforeUpdate = applicantTravelDocumentRepository.findAll().size();

        // Update the applicantTravelDocument
        ApplicantTravelDocument updatedApplicantTravelDocument = applicantTravelDocumentRepository.findById(applicantTravelDocument.getId()).get();
        // Disconnect from session so that the updates on updatedApplicantTravelDocument are not directly saved in db
        em.detach(updatedApplicantTravelDocument);
        updatedApplicantTravelDocument
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .dateOfIssue(UPDATED_DATE_OF_ISSUE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .issuingAuthority(UPDATED_ISSUING_AUTHORITY)
            .documentPhoto(UPDATED_DOCUMENT_PHOTO)
            .documentPhotoContentType(UPDATED_DOCUMENT_PHOTO_CONTENT_TYPE)
            .typeOfDocument(UPDATED_TYPE_OF_DOCUMENT);
        ApplicantTravelDocumentDTO applicantTravelDocumentDTO = applicantTravelDocumentMapper.toDto(updatedApplicantTravelDocument);

        restApplicantTravelDocumentMockMvc.perform(put("/api/applicant-travel-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantTravelDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicantTravelDocument in the database
        List<ApplicantTravelDocument> applicantTravelDocumentList = applicantTravelDocumentRepository.findAll();
        assertThat(applicantTravelDocumentList).hasSize(databaseSizeBeforeUpdate);
        ApplicantTravelDocument testApplicantTravelDocument = applicantTravelDocumentList.get(applicantTravelDocumentList.size() - 1);
        assertThat(testApplicantTravelDocument.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testApplicantTravelDocument.getDateOfIssue()).isEqualTo(UPDATED_DATE_OF_ISSUE);
        assertThat(testApplicantTravelDocument.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testApplicantTravelDocument.getIssuingAuthority()).isEqualTo(UPDATED_ISSUING_AUTHORITY);
        assertThat(testApplicantTravelDocument.getDocumentPhoto()).isEqualTo(UPDATED_DOCUMENT_PHOTO);
        assertThat(testApplicantTravelDocument.getDocumentPhotoContentType()).isEqualTo(UPDATED_DOCUMENT_PHOTO_CONTENT_TYPE);
        assertThat(testApplicantTravelDocument.getTypeOfDocument()).isEqualTo(UPDATED_TYPE_OF_DOCUMENT);

        // Validate the ApplicantTravelDocument in Elasticsearch
        verify(mockApplicantTravelDocumentSearchRepository, times(1)).save(testApplicantTravelDocument);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicantTravelDocument() throws Exception {
        int databaseSizeBeforeUpdate = applicantTravelDocumentRepository.findAll().size();

        // Create the ApplicantTravelDocument
        ApplicantTravelDocumentDTO applicantTravelDocumentDTO = applicantTravelDocumentMapper.toDto(applicantTravelDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantTravelDocumentMockMvc.perform(put("/api/applicant-travel-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantTravelDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantTravelDocument in the database
        List<ApplicantTravelDocument> applicantTravelDocumentList = applicantTravelDocumentRepository.findAll();
        assertThat(applicantTravelDocumentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ApplicantTravelDocument in Elasticsearch
        verify(mockApplicantTravelDocumentSearchRepository, times(0)).save(applicantTravelDocument);
    }

    @Test
    @Transactional
    public void deleteApplicantTravelDocument() throws Exception {
        // Initialize the database
        applicantTravelDocumentRepository.saveAndFlush(applicantTravelDocument);

        int databaseSizeBeforeDelete = applicantTravelDocumentRepository.findAll().size();

        // Delete the applicantTravelDocument
        restApplicantTravelDocumentMockMvc.perform(delete("/api/applicant-travel-documents/{id}", applicantTravelDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicantTravelDocument> applicantTravelDocumentList = applicantTravelDocumentRepository.findAll();
        assertThat(applicantTravelDocumentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ApplicantTravelDocument in Elasticsearch
        verify(mockApplicantTravelDocumentSearchRepository, times(1)).deleteById(applicantTravelDocument.getId());
    }

    @Test
    @Transactional
    public void searchApplicantTravelDocument() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        applicantTravelDocumentRepository.saveAndFlush(applicantTravelDocument);
        when(mockApplicantTravelDocumentSearchRepository.search(queryStringQuery("id:" + applicantTravelDocument.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(applicantTravelDocument), PageRequest.of(0, 1), 1));

        // Search the applicantTravelDocument
        restApplicantTravelDocumentMockMvc.perform(get("/api/_search/applicant-travel-documents?query=id:" + applicantTravelDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantTravelDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfIssue").value(hasItem(DEFAULT_DATE_OF_ISSUE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].issuingAuthority").value(hasItem(DEFAULT_ISSUING_AUTHORITY)))
            .andExpect(jsonPath("$.[*].documentPhotoContentType").value(hasItem(DEFAULT_DOCUMENT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_PHOTO))))
            .andExpect(jsonPath("$.[*].typeOfDocument").value(hasItem(DEFAULT_TYPE_OF_DOCUMENT.toString())));
    }
}
