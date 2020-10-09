package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.SomalivisaserverApp;
import so.gov.mfa.visa.domain.ApplicationComment;
import so.gov.mfa.visa.repository.ApplicationCommentRepository;
import so.gov.mfa.visa.repository.search.ApplicationCommentSearchRepository;
import so.gov.mfa.visa.service.ApplicationCommentService;
import so.gov.mfa.visa.service.dto.ApplicationCommentDTO;
import so.gov.mfa.visa.service.mapper.ApplicationCommentMapper;

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

import so.gov.mfa.visa.domain.enumeration.CommenterType;
/**
 * Integration tests for the {@link ApplicationCommentResource} REST controller.
 */
@SpringBootTest(classes = SomalivisaserverApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplicationCommentResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_COMMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final CommenterType DEFAULT_COMMENTER_TYPE = CommenterType.EMPLOYEE;
    private static final CommenterType UPDATED_COMMENTER_TYPE = CommenterType.APPLICANT;

    @Autowired
    private ApplicationCommentRepository applicationCommentRepository;

    @Autowired
    private ApplicationCommentMapper applicationCommentMapper;

    @Autowired
    private ApplicationCommentService applicationCommentService;

    /**
     * This repository is mocked in the so.gov.mfa.visa.repository.search test package.
     *
     * @see so.gov.mfa.visa.repository.search.ApplicationCommentSearchRepositoryMockConfiguration
     */
    @Autowired
    private ApplicationCommentSearchRepository mockApplicationCommentSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationCommentMockMvc;

    private ApplicationComment applicationComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationComment createEntity(EntityManager em) {
        ApplicationComment applicationComment = new ApplicationComment()
            .title(DEFAULT_TITLE)
            .comment(DEFAULT_COMMENT)
            .commentDate(DEFAULT_COMMENT_DATE)
            .commenterType(DEFAULT_COMMENTER_TYPE);
        return applicationComment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationComment createUpdatedEntity(EntityManager em) {
        ApplicationComment applicationComment = new ApplicationComment()
            .title(UPDATED_TITLE)
            .comment(UPDATED_COMMENT)
            .commentDate(UPDATED_COMMENT_DATE)
            .commenterType(UPDATED_COMMENTER_TYPE);
        return applicationComment;
    }

    @BeforeEach
    public void initTest() {
        applicationComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationComment() throws Exception {
        int databaseSizeBeforeCreate = applicationCommentRepository.findAll().size();
        // Create the ApplicationComment
        ApplicationCommentDTO applicationCommentDTO = applicationCommentMapper.toDto(applicationComment);
        restApplicationCommentMockMvc.perform(post("/api/application-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationComment in the database
        List<ApplicationComment> applicationCommentList = applicationCommentRepository.findAll();
        assertThat(applicationCommentList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationComment testApplicationComment = applicationCommentList.get(applicationCommentList.size() - 1);
        assertThat(testApplicationComment.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testApplicationComment.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testApplicationComment.getCommentDate()).isEqualTo(DEFAULT_COMMENT_DATE);
        assertThat(testApplicationComment.getCommenterType()).isEqualTo(DEFAULT_COMMENTER_TYPE);

        // Validate the ApplicationComment in Elasticsearch
        verify(mockApplicationCommentSearchRepository, times(1)).save(testApplicationComment);
    }

    @Test
    @Transactional
    public void createApplicationCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationCommentRepository.findAll().size();

        // Create the ApplicationComment with an existing ID
        applicationComment.setId(1L);
        ApplicationCommentDTO applicationCommentDTO = applicationCommentMapper.toDto(applicationComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationCommentMockMvc.perform(post("/api/application-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationComment in the database
        List<ApplicationComment> applicationCommentList = applicationCommentRepository.findAll();
        assertThat(applicationCommentList).hasSize(databaseSizeBeforeCreate);

        // Validate the ApplicationComment in Elasticsearch
        verify(mockApplicationCommentSearchRepository, times(0)).save(applicationComment);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationCommentRepository.findAll().size();
        // set the field null
        applicationComment.setTitle(null);

        // Create the ApplicationComment, which fails.
        ApplicationCommentDTO applicationCommentDTO = applicationCommentMapper.toDto(applicationComment);


        restApplicationCommentMockMvc.perform(post("/api/application-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationCommentDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationComment> applicationCommentList = applicationCommentRepository.findAll();
        assertThat(applicationCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationCommentRepository.findAll().size();
        // set the field null
        applicationComment.setComment(null);

        // Create the ApplicationComment, which fails.
        ApplicationCommentDTO applicationCommentDTO = applicationCommentMapper.toDto(applicationComment);


        restApplicationCommentMockMvc.perform(post("/api/application-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationCommentDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationComment> applicationCommentList = applicationCommentRepository.findAll();
        assertThat(applicationCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationCommentRepository.findAll().size();
        // set the field null
        applicationComment.setCommentDate(null);

        // Create the ApplicationComment, which fails.
        ApplicationCommentDTO applicationCommentDTO = applicationCommentMapper.toDto(applicationComment);


        restApplicationCommentMockMvc.perform(post("/api/application-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationCommentDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationComment> applicationCommentList = applicationCommentRepository.findAll();
        assertThat(applicationCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationComments() throws Exception {
        // Initialize the database
        applicationCommentRepository.saveAndFlush(applicationComment);

        // Get all the applicationCommentList
        restApplicationCommentMockMvc.perform(get("/api/application-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].commentDate").value(hasItem(sameInstant(DEFAULT_COMMENT_DATE))))
            .andExpect(jsonPath("$.[*].commenterType").value(hasItem(DEFAULT_COMMENTER_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicationComment() throws Exception {
        // Initialize the database
        applicationCommentRepository.saveAndFlush(applicationComment);

        // Get the applicationComment
        restApplicationCommentMockMvc.perform(get("/api/application-comments/{id}", applicationComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationComment.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.commentDate").value(sameInstant(DEFAULT_COMMENT_DATE)))
            .andExpect(jsonPath("$.commenterType").value(DEFAULT_COMMENTER_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingApplicationComment() throws Exception {
        // Get the applicationComment
        restApplicationCommentMockMvc.perform(get("/api/application-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationComment() throws Exception {
        // Initialize the database
        applicationCommentRepository.saveAndFlush(applicationComment);

        int databaseSizeBeforeUpdate = applicationCommentRepository.findAll().size();

        // Update the applicationComment
        ApplicationComment updatedApplicationComment = applicationCommentRepository.findById(applicationComment.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationComment are not directly saved in db
        em.detach(updatedApplicationComment);
        updatedApplicationComment
            .title(UPDATED_TITLE)
            .comment(UPDATED_COMMENT)
            .commentDate(UPDATED_COMMENT_DATE)
            .commenterType(UPDATED_COMMENTER_TYPE);
        ApplicationCommentDTO applicationCommentDTO = applicationCommentMapper.toDto(updatedApplicationComment);

        restApplicationCommentMockMvc.perform(put("/api/application-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationCommentDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationComment in the database
        List<ApplicationComment> applicationCommentList = applicationCommentRepository.findAll();
        assertThat(applicationCommentList).hasSize(databaseSizeBeforeUpdate);
        ApplicationComment testApplicationComment = applicationCommentList.get(applicationCommentList.size() - 1);
        assertThat(testApplicationComment.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testApplicationComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testApplicationComment.getCommentDate()).isEqualTo(UPDATED_COMMENT_DATE);
        assertThat(testApplicationComment.getCommenterType()).isEqualTo(UPDATED_COMMENTER_TYPE);

        // Validate the ApplicationComment in Elasticsearch
        verify(mockApplicationCommentSearchRepository, times(1)).save(testApplicationComment);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationComment() throws Exception {
        int databaseSizeBeforeUpdate = applicationCommentRepository.findAll().size();

        // Create the ApplicationComment
        ApplicationCommentDTO applicationCommentDTO = applicationCommentMapper.toDto(applicationComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationCommentMockMvc.perform(put("/api/application-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationComment in the database
        List<ApplicationComment> applicationCommentList = applicationCommentRepository.findAll();
        assertThat(applicationCommentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ApplicationComment in Elasticsearch
        verify(mockApplicationCommentSearchRepository, times(0)).save(applicationComment);
    }

    @Test
    @Transactional
    public void deleteApplicationComment() throws Exception {
        // Initialize the database
        applicationCommentRepository.saveAndFlush(applicationComment);

        int databaseSizeBeforeDelete = applicationCommentRepository.findAll().size();

        // Delete the applicationComment
        restApplicationCommentMockMvc.perform(delete("/api/application-comments/{id}", applicationComment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationComment> applicationCommentList = applicationCommentRepository.findAll();
        assertThat(applicationCommentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ApplicationComment in Elasticsearch
        verify(mockApplicationCommentSearchRepository, times(1)).deleteById(applicationComment.getId());
    }

    @Test
    @Transactional
    public void searchApplicationComment() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        applicationCommentRepository.saveAndFlush(applicationComment);
        when(mockApplicationCommentSearchRepository.search(queryStringQuery("id:" + applicationComment.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(applicationComment), PageRequest.of(0, 1), 1));

        // Search the applicationComment
        restApplicationCommentMockMvc.perform(get("/api/_search/application-comments?query=id:" + applicationComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].commentDate").value(hasItem(sameInstant(DEFAULT_COMMENT_DATE))))
            .andExpect(jsonPath("$.[*].commenterType").value(hasItem(DEFAULT_COMMENTER_TYPE.toString())));
    }
}
