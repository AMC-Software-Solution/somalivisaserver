package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.SomalivisaserverApp;
import so.gov.mfa.visa.domain.ApplicationFee;
import so.gov.mfa.visa.repository.ApplicationFeeRepository;
import so.gov.mfa.visa.repository.search.ApplicationFeeSearchRepository;
import so.gov.mfa.visa.service.ApplicationFeeService;
import so.gov.mfa.visa.service.dto.ApplicationFeeDTO;
import so.gov.mfa.visa.service.mapper.ApplicationFeeMapper;

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
 * Integration tests for the {@link ApplicationFeeResource} REST controller.
 */
@SpringBootTest(classes = SomalivisaserverApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplicationFeeResourceIT {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_ISO_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_ISO_CODE = "BBBBBBBBBB";

    @Autowired
    private ApplicationFeeRepository applicationFeeRepository;

    @Autowired
    private ApplicationFeeMapper applicationFeeMapper;

    @Autowired
    private ApplicationFeeService applicationFeeService;

    /**
     * This repository is mocked in the so.gov.mfa.visa.repository.search test package.
     *
     * @see so.gov.mfa.visa.repository.search.ApplicationFeeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ApplicationFeeSearchRepository mockApplicationFeeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationFeeMockMvc;

    private ApplicationFee applicationFee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationFee createEntity(EntityManager em) {
        ApplicationFee applicationFee = new ApplicationFee()
            .amount(DEFAULT_AMOUNT)
            .description(DEFAULT_DESCRIPTION)
            .currency(DEFAULT_CURRENCY)
            .currentIsoCode(DEFAULT_CURRENT_ISO_CODE);
        return applicationFee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationFee createUpdatedEntity(EntityManager em) {
        ApplicationFee applicationFee = new ApplicationFee()
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .currency(UPDATED_CURRENCY)
            .currentIsoCode(UPDATED_CURRENT_ISO_CODE);
        return applicationFee;
    }

    @BeforeEach
    public void initTest() {
        applicationFee = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationFee() throws Exception {
        int databaseSizeBeforeCreate = applicationFeeRepository.findAll().size();
        // Create the ApplicationFee
        ApplicationFeeDTO applicationFeeDTO = applicationFeeMapper.toDto(applicationFee);
        restApplicationFeeMockMvc.perform(post("/api/application-fees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationFeeDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationFee in the database
        List<ApplicationFee> applicationFeeList = applicationFeeRepository.findAll();
        assertThat(applicationFeeList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationFee testApplicationFee = applicationFeeList.get(applicationFeeList.size() - 1);
        assertThat(testApplicationFee.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testApplicationFee.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApplicationFee.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testApplicationFee.getCurrentIsoCode()).isEqualTo(DEFAULT_CURRENT_ISO_CODE);

        // Validate the ApplicationFee in Elasticsearch
        verify(mockApplicationFeeSearchRepository, times(1)).save(testApplicationFee);
    }

    @Test
    @Transactional
    public void createApplicationFeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationFeeRepository.findAll().size();

        // Create the ApplicationFee with an existing ID
        applicationFee.setId(1L);
        ApplicationFeeDTO applicationFeeDTO = applicationFeeMapper.toDto(applicationFee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationFeeMockMvc.perform(post("/api/application-fees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationFeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationFee in the database
        List<ApplicationFee> applicationFeeList = applicationFeeRepository.findAll();
        assertThat(applicationFeeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ApplicationFee in Elasticsearch
        verify(mockApplicationFeeSearchRepository, times(0)).save(applicationFee);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationFeeRepository.findAll().size();
        // set the field null
        applicationFee.setAmount(null);

        // Create the ApplicationFee, which fails.
        ApplicationFeeDTO applicationFeeDTO = applicationFeeMapper.toDto(applicationFee);


        restApplicationFeeMockMvc.perform(post("/api/application-fees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationFeeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationFee> applicationFeeList = applicationFeeRepository.findAll();
        assertThat(applicationFeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationFeeRepository.findAll().size();
        // set the field null
        applicationFee.setDescription(null);

        // Create the ApplicationFee, which fails.
        ApplicationFeeDTO applicationFeeDTO = applicationFeeMapper.toDto(applicationFee);


        restApplicationFeeMockMvc.perform(post("/api/application-fees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationFeeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationFee> applicationFeeList = applicationFeeRepository.findAll();
        assertThat(applicationFeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationFeeRepository.findAll().size();
        // set the field null
        applicationFee.setCurrency(null);

        // Create the ApplicationFee, which fails.
        ApplicationFeeDTO applicationFeeDTO = applicationFeeMapper.toDto(applicationFee);


        restApplicationFeeMockMvc.perform(post("/api/application-fees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationFeeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationFee> applicationFeeList = applicationFeeRepository.findAll();
        assertThat(applicationFeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrentIsoCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationFeeRepository.findAll().size();
        // set the field null
        applicationFee.setCurrentIsoCode(null);

        // Create the ApplicationFee, which fails.
        ApplicationFeeDTO applicationFeeDTO = applicationFeeMapper.toDto(applicationFee);


        restApplicationFeeMockMvc.perform(post("/api/application-fees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationFeeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationFee> applicationFeeList = applicationFeeRepository.findAll();
        assertThat(applicationFeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationFees() throws Exception {
        // Initialize the database
        applicationFeeRepository.saveAndFlush(applicationFee);

        // Get all the applicationFeeList
        restApplicationFeeMockMvc.perform(get("/api/application-fees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationFee.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].currentIsoCode").value(hasItem(DEFAULT_CURRENT_ISO_CODE)));
    }
    
    @Test
    @Transactional
    public void getApplicationFee() throws Exception {
        // Initialize the database
        applicationFeeRepository.saveAndFlush(applicationFee);

        // Get the applicationFee
        restApplicationFeeMockMvc.perform(get("/api/application-fees/{id}", applicationFee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationFee.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.currentIsoCode").value(DEFAULT_CURRENT_ISO_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingApplicationFee() throws Exception {
        // Get the applicationFee
        restApplicationFeeMockMvc.perform(get("/api/application-fees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationFee() throws Exception {
        // Initialize the database
        applicationFeeRepository.saveAndFlush(applicationFee);

        int databaseSizeBeforeUpdate = applicationFeeRepository.findAll().size();

        // Update the applicationFee
        ApplicationFee updatedApplicationFee = applicationFeeRepository.findById(applicationFee.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationFee are not directly saved in db
        em.detach(updatedApplicationFee);
        updatedApplicationFee
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .currency(UPDATED_CURRENCY)
            .currentIsoCode(UPDATED_CURRENT_ISO_CODE);
        ApplicationFeeDTO applicationFeeDTO = applicationFeeMapper.toDto(updatedApplicationFee);

        restApplicationFeeMockMvc.perform(put("/api/application-fees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationFeeDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationFee in the database
        List<ApplicationFee> applicationFeeList = applicationFeeRepository.findAll();
        assertThat(applicationFeeList).hasSize(databaseSizeBeforeUpdate);
        ApplicationFee testApplicationFee = applicationFeeList.get(applicationFeeList.size() - 1);
        assertThat(testApplicationFee.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testApplicationFee.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicationFee.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testApplicationFee.getCurrentIsoCode()).isEqualTo(UPDATED_CURRENT_ISO_CODE);

        // Validate the ApplicationFee in Elasticsearch
        verify(mockApplicationFeeSearchRepository, times(1)).save(testApplicationFee);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationFee() throws Exception {
        int databaseSizeBeforeUpdate = applicationFeeRepository.findAll().size();

        // Create the ApplicationFee
        ApplicationFeeDTO applicationFeeDTO = applicationFeeMapper.toDto(applicationFee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationFeeMockMvc.perform(put("/api/application-fees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationFeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationFee in the database
        List<ApplicationFee> applicationFeeList = applicationFeeRepository.findAll();
        assertThat(applicationFeeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ApplicationFee in Elasticsearch
        verify(mockApplicationFeeSearchRepository, times(0)).save(applicationFee);
    }

    @Test
    @Transactional
    public void deleteApplicationFee() throws Exception {
        // Initialize the database
        applicationFeeRepository.saveAndFlush(applicationFee);

        int databaseSizeBeforeDelete = applicationFeeRepository.findAll().size();

        // Delete the applicationFee
        restApplicationFeeMockMvc.perform(delete("/api/application-fees/{id}", applicationFee.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationFee> applicationFeeList = applicationFeeRepository.findAll();
        assertThat(applicationFeeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ApplicationFee in Elasticsearch
        verify(mockApplicationFeeSearchRepository, times(1)).deleteById(applicationFee.getId());
    }

    @Test
    @Transactional
    public void searchApplicationFee() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        applicationFeeRepository.saveAndFlush(applicationFee);
        when(mockApplicationFeeSearchRepository.search(queryStringQuery("id:" + applicationFee.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(applicationFee), PageRequest.of(0, 1), 1));

        // Search the applicationFee
        restApplicationFeeMockMvc.perform(get("/api/_search/application-fees?query=id:" + applicationFee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationFee.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].currentIsoCode").value(hasItem(DEFAULT_CURRENT_ISO_CODE)));
    }
}
