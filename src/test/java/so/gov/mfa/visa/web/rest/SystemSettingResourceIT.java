package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.SomalivisaserverApp;
import so.gov.mfa.visa.domain.SystemSetting;
import so.gov.mfa.visa.repository.SystemSettingRepository;
import so.gov.mfa.visa.repository.search.SystemSettingSearchRepository;
import so.gov.mfa.visa.service.SystemSettingService;
import so.gov.mfa.visa.service.dto.SystemSettingDTO;
import so.gov.mfa.visa.service.mapper.SystemSettingMapper;

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
 * Integration tests for the {@link SystemSettingResource} REST controller.
 */
@SpringBootTest(classes = SomalivisaserverApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SystemSettingResourceIT {

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
    private SystemSettingRepository systemSettingRepository;

    @Autowired
    private SystemSettingMapper systemSettingMapper;

    @Autowired
    private SystemSettingService systemSettingService;

    /**
     * This repository is mocked in the so.gov.mfa.visa.repository.search test package.
     *
     * @see so.gov.mfa.visa.repository.search.SystemSettingSearchRepositoryMockConfiguration
     */
    @Autowired
    private SystemSettingSearchRepository mockSystemSettingSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemSettingMockMvc;

    private SystemSetting systemSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemSetting createEntity(EntityManager em) {
        SystemSetting systemSetting = new SystemSetting()
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldValue(DEFAULT_FIELD_VALUE)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .enabled(DEFAULT_ENABLED)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return systemSetting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemSetting createUpdatedEntity(EntityManager em) {
        SystemSetting systemSetting = new SystemSetting()
            .fieldName(UPDATED_FIELD_NAME)
            .fieldValue(UPDATED_FIELD_VALUE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .enabled(UPDATED_ENABLED)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return systemSetting;
    }

    @BeforeEach
    public void initTest() {
        systemSetting = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemSetting() throws Exception {
        int databaseSizeBeforeCreate = systemSettingRepository.findAll().size();
        // Create the SystemSetting
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);
        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isCreated());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeCreate + 1);
        SystemSetting testSystemSetting = systemSettingList.get(systemSettingList.size() - 1);
        assertThat(testSystemSetting.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testSystemSetting.getFieldValue()).isEqualTo(DEFAULT_FIELD_VALUE);
        assertThat(testSystemSetting.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testSystemSetting.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testSystemSetting.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSystemSetting.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);

        // Validate the SystemSetting in Elasticsearch
        verify(mockSystemSettingSearchRepository, times(1)).save(testSystemSetting);
    }

    @Test
    @Transactional
    public void createSystemSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemSettingRepository.findAll().size();

        // Create the SystemSetting with an existing ID
        systemSetting.setId(1L);
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeCreate);

        // Validate the SystemSetting in Elasticsearch
        verify(mockSystemSettingSearchRepository, times(0)).save(systemSetting);
    }


    @Test
    @Transactional
    public void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSettingRepository.findAll().size();
        // set the field null
        systemSetting.setFieldName(null);

        // Create the SystemSetting, which fails.
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);


        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isBadRequest());

        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSettingRepository.findAll().size();
        // set the field null
        systemSetting.setFieldValue(null);

        // Create the SystemSetting, which fails.
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);


        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isBadRequest());

        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDefaultValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSettingRepository.findAll().size();
        // set the field null
        systemSetting.setDefaultValue(null);

        // Create the SystemSetting, which fails.
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);


        restSystemSettingMockMvc.perform(post("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isBadRequest());

        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystemSettings() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettingList
        restSystemSettingMockMvc.perform(get("/api/system-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldValue").value(hasItem(DEFAULT_FIELD_VALUE)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getSystemSetting() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get the systemSetting
        restSystemSettingMockMvc.perform(get("/api/system-settings/{id}", systemSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(systemSetting.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME))
            .andExpect(jsonPath("$.fieldValue").value(DEFAULT_FIELD_VALUE))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingSystemSetting() throws Exception {
        // Get the systemSetting
        restSystemSettingMockMvc.perform(get("/api/system-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemSetting() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        int databaseSizeBeforeUpdate = systemSettingRepository.findAll().size();

        // Update the systemSetting
        SystemSetting updatedSystemSetting = systemSettingRepository.findById(systemSetting.getId()).get();
        // Disconnect from session so that the updates on updatedSystemSetting are not directly saved in db
        em.detach(updatedSystemSetting);
        updatedSystemSetting
            .fieldName(UPDATED_FIELD_NAME)
            .fieldValue(UPDATED_FIELD_VALUE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .enabled(UPDATED_ENABLED)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(updatedSystemSetting);

        restSystemSettingMockMvc.perform(put("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isOk());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeUpdate);
        SystemSetting testSystemSetting = systemSettingList.get(systemSettingList.size() - 1);
        assertThat(testSystemSetting.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testSystemSetting.getFieldValue()).isEqualTo(UPDATED_FIELD_VALUE);
        assertThat(testSystemSetting.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testSystemSetting.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testSystemSetting.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSystemSetting.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);

        // Validate the SystemSetting in Elasticsearch
        verify(mockSystemSettingSearchRepository, times(1)).save(testSystemSetting);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemSetting() throws Exception {
        int databaseSizeBeforeUpdate = systemSettingRepository.findAll().size();

        // Create the SystemSetting
        SystemSettingDTO systemSettingDTO = systemSettingMapper.toDto(systemSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemSettingMockMvc.perform(put("/api/system-settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SystemSetting in Elasticsearch
        verify(mockSystemSettingSearchRepository, times(0)).save(systemSetting);
    }

    @Test
    @Transactional
    public void deleteSystemSetting() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        int databaseSizeBeforeDelete = systemSettingRepository.findAll().size();

        // Delete the systemSetting
        restSystemSettingMockMvc.perform(delete("/api/system-settings/{id}", systemSetting.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SystemSetting> systemSettingList = systemSettingRepository.findAll();
        assertThat(systemSettingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SystemSetting in Elasticsearch
        verify(mockSystemSettingSearchRepository, times(1)).deleteById(systemSetting.getId());
    }

    @Test
    @Transactional
    public void searchSystemSetting() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);
        when(mockSystemSettingSearchRepository.search(queryStringQuery("id:" + systemSetting.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(systemSetting), PageRequest.of(0, 1), 1));

        // Search the systemSetting
        restSystemSettingMockMvc.perform(get("/api/_search/system-settings?query=id:" + systemSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldValue").value(hasItem(DEFAULT_FIELD_VALUE)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }
}
