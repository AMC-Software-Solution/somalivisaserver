package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.SomalivisaserverApp;
import so.gov.mfa.visa.domain.AppSetting;
import so.gov.mfa.visa.repository.AppSettingRepository;
import so.gov.mfa.visa.repository.search.AppSettingSearchRepository;
import so.gov.mfa.visa.service.AppSettingService;
import so.gov.mfa.visa.service.dto.AppSettingDTO;
import so.gov.mfa.visa.service.mapper.AppSettingMapper;

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

/**
 * Integration tests for the {@link AppSettingResource} REST controller.
 */
@SpringBootTest(classes = SomalivisaserverApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AppSettingResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AppSettingRepository appSettingRepository;

    @Autowired
    private AppSettingMapper appSettingMapper;

    @Autowired
    private AppSettingService appSettingService;

    /**
     * This repository is mocked in the so.gov.mfa.visa.repository.search test package.
     *
     * @see so.gov.mfa.visa.repository.search.AppSettingSearchRepositoryMockConfiguration
     */
    @Autowired
    private AppSettingSearchRepository mockAppSettingSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppSettingMockMvc;

    private AppSetting appSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppSetting createEntity(EntityManager em) {
        AppSetting appSetting = new AppSetting()
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldValue(DEFAULT_FIELD_VALUE)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .enabled(DEFAULT_ENABLED)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return appSetting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppSetting createUpdatedEntity(EntityManager em) {
        AppSetting appSetting = new AppSetting()
            .fieldName(UPDATED_FIELD_NAME)
            .fieldValue(UPDATED_FIELD_VALUE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .enabled(UPDATED_ENABLED)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return appSetting;
    }

    @BeforeEach
    public void initTest() {
        appSetting = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppSetting() throws Exception {
        int databaseSizeBeforeCreate = appSettingRepository.findAll().size();
        // Create the AppSetting
        AppSettingDTO appSettingDTO = appSettingMapper.toDto(appSetting);
        restAppSettingMockMvc.perform(post("/api/app-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appSettingDTO)))
            .andExpect(status().isCreated());

        // Validate the AppSetting in the database
        List<AppSetting> appSettingList = appSettingRepository.findAll();
        assertThat(appSettingList).hasSize(databaseSizeBeforeCreate + 1);
        AppSetting testAppSetting = appSettingList.get(appSettingList.size() - 1);
        assertThat(testAppSetting.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testAppSetting.getFieldValue()).isEqualTo(DEFAULT_FIELD_VALUE);
        assertThat(testAppSetting.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testAppSetting.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testAppSetting.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAppSetting.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);

        // Validate the AppSetting in Elasticsearch
        verify(mockAppSettingSearchRepository, times(1)).save(testAppSetting);
    }

    @Test
    @Transactional
    public void createAppSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appSettingRepository.findAll().size();

        // Create the AppSetting with an existing ID
        appSetting.setId(1L);
        AppSettingDTO appSettingDTO = appSettingMapper.toDto(appSetting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppSettingMockMvc.perform(post("/api/app-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppSetting in the database
        List<AppSetting> appSettingList = appSettingRepository.findAll();
        assertThat(appSettingList).hasSize(databaseSizeBeforeCreate);

        // Validate the AppSetting in Elasticsearch
        verify(mockAppSettingSearchRepository, times(0)).save(appSetting);
    }


    @Test
    @Transactional
    public void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appSettingRepository.findAll().size();
        // set the field null
        appSetting.setFieldName(null);

        // Create the AppSetting, which fails.
        AppSettingDTO appSettingDTO = appSettingMapper.toDto(appSetting);


        restAppSettingMockMvc.perform(post("/api/app-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appSettingDTO)))
            .andExpect(status().isBadRequest());

        List<AppSetting> appSettingList = appSettingRepository.findAll();
        assertThat(appSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = appSettingRepository.findAll().size();
        // set the field null
        appSetting.setFieldValue(null);

        // Create the AppSetting, which fails.
        AppSettingDTO appSettingDTO = appSettingMapper.toDto(appSetting);


        restAppSettingMockMvc.perform(post("/api/app-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appSettingDTO)))
            .andExpect(status().isBadRequest());

        List<AppSetting> appSettingList = appSettingRepository.findAll();
        assertThat(appSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDefaultValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = appSettingRepository.findAll().size();
        // set the field null
        appSetting.setDefaultValue(null);

        // Create the AppSetting, which fails.
        AppSettingDTO appSettingDTO = appSettingMapper.toDto(appSetting);


        restAppSettingMockMvc.perform(post("/api/app-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appSettingDTO)))
            .andExpect(status().isBadRequest());

        List<AppSetting> appSettingList = appSettingRepository.findAll();
        assertThat(appSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppSettings() throws Exception {
        // Initialize the database
        appSettingRepository.saveAndFlush(appSetting);

        // Get all the appSettingList
        restAppSettingMockMvc.perform(get("/api/app-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldValue").value(hasItem(DEFAULT_FIELD_VALUE)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getAppSetting() throws Exception {
        // Initialize the database
        appSettingRepository.saveAndFlush(appSetting);

        // Get the appSetting
        restAppSettingMockMvc.perform(get("/api/app-settings/{id}", appSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appSetting.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME))
            .andExpect(jsonPath("$.fieldValue").value(DEFAULT_FIELD_VALUE))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingAppSetting() throws Exception {
        // Get the appSetting
        restAppSettingMockMvc.perform(get("/api/app-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppSetting() throws Exception {
        // Initialize the database
        appSettingRepository.saveAndFlush(appSetting);

        int databaseSizeBeforeUpdate = appSettingRepository.findAll().size();

        // Update the appSetting
        AppSetting updatedAppSetting = appSettingRepository.findById(appSetting.getId()).get();
        // Disconnect from session so that the updates on updatedAppSetting are not directly saved in db
        em.detach(updatedAppSetting);
        updatedAppSetting
            .fieldName(UPDATED_FIELD_NAME)
            .fieldValue(UPDATED_FIELD_VALUE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .enabled(UPDATED_ENABLED)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        AppSettingDTO appSettingDTO = appSettingMapper.toDto(updatedAppSetting);

        restAppSettingMockMvc.perform(put("/api/app-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appSettingDTO)))
            .andExpect(status().isOk());

        // Validate the AppSetting in the database
        List<AppSetting> appSettingList = appSettingRepository.findAll();
        assertThat(appSettingList).hasSize(databaseSizeBeforeUpdate);
        AppSetting testAppSetting = appSettingList.get(appSettingList.size() - 1);
        assertThat(testAppSetting.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testAppSetting.getFieldValue()).isEqualTo(UPDATED_FIELD_VALUE);
        assertThat(testAppSetting.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testAppSetting.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testAppSetting.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAppSetting.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);

        // Validate the AppSetting in Elasticsearch
        verify(mockAppSettingSearchRepository, times(1)).save(testAppSetting);
    }

    @Test
    @Transactional
    public void updateNonExistingAppSetting() throws Exception {
        int databaseSizeBeforeUpdate = appSettingRepository.findAll().size();

        // Create the AppSetting
        AppSettingDTO appSettingDTO = appSettingMapper.toDto(appSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppSettingMockMvc.perform(put("/api/app-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppSetting in the database
        List<AppSetting> appSettingList = appSettingRepository.findAll();
        assertThat(appSettingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AppSetting in Elasticsearch
        verify(mockAppSettingSearchRepository, times(0)).save(appSetting);
    }

    @Test
    @Transactional
    public void deleteAppSetting() throws Exception {
        // Initialize the database
        appSettingRepository.saveAndFlush(appSetting);

        int databaseSizeBeforeDelete = appSettingRepository.findAll().size();

        // Delete the appSetting
        restAppSettingMockMvc.perform(delete("/api/app-settings/{id}", appSetting.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppSetting> appSettingList = appSettingRepository.findAll();
        assertThat(appSettingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AppSetting in Elasticsearch
        verify(mockAppSettingSearchRepository, times(1)).deleteById(appSetting.getId());
    }

    @Test
    @Transactional
    public void searchAppSetting() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        appSettingRepository.saveAndFlush(appSetting);
        when(mockAppSettingSearchRepository.search(queryStringQuery("id:" + appSetting.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(appSetting), PageRequest.of(0, 1), 1));

        // Search the appSetting
        restAppSettingMockMvc.perform(get("/api/_search/app-settings?query=id:" + appSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldValue").value(hasItem(DEFAULT_FIELD_VALUE)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }
}
