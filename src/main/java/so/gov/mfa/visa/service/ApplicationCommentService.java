package so.gov.mfa.visa.service;

import so.gov.mfa.visa.domain.ApplicationComment;
import so.gov.mfa.visa.repository.ApplicationCommentRepository;
import so.gov.mfa.visa.repository.search.ApplicationCommentSearchRepository;
import so.gov.mfa.visa.service.dto.ApplicationCommentDTO;
import so.gov.mfa.visa.service.mapper.ApplicationCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ApplicationComment}.
 */
@Service
@Transactional
public class ApplicationCommentService {

    private final Logger log = LoggerFactory.getLogger(ApplicationCommentService.class);

    private final ApplicationCommentRepository applicationCommentRepository;

    private final ApplicationCommentMapper applicationCommentMapper;

    private final ApplicationCommentSearchRepository applicationCommentSearchRepository;

    public ApplicationCommentService(ApplicationCommentRepository applicationCommentRepository, ApplicationCommentMapper applicationCommentMapper, ApplicationCommentSearchRepository applicationCommentSearchRepository) {
        this.applicationCommentRepository = applicationCommentRepository;
        this.applicationCommentMapper = applicationCommentMapper;
        this.applicationCommentSearchRepository = applicationCommentSearchRepository;
    }

    /**
     * Save a applicationComment.
     *
     * @param applicationCommentDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicationCommentDTO save(ApplicationCommentDTO applicationCommentDTO) {
        log.debug("Request to save ApplicationComment : {}", applicationCommentDTO);
        ApplicationComment applicationComment = applicationCommentMapper.toEntity(applicationCommentDTO);
        applicationComment = applicationCommentRepository.save(applicationComment);
        ApplicationCommentDTO result = applicationCommentMapper.toDto(applicationComment);
        applicationCommentSearchRepository.save(applicationComment);
        return result;
    }

    /**
     * Get all the applicationComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationComments");
        return applicationCommentRepository.findAll(pageable)
            .map(applicationCommentMapper::toDto);
    }


    /**
     * Get one applicationComment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicationCommentDTO> findOne(Long id) {
        log.debug("Request to get ApplicationComment : {}", id);
        return applicationCommentRepository.findById(id)
            .map(applicationCommentMapper::toDto);
    }

    /**
     * Delete the applicationComment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicationComment : {}", id);
        applicationCommentRepository.deleteById(id);
        applicationCommentSearchRepository.deleteById(id);
    }

    /**
     * Search for the applicationComment corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationCommentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ApplicationComments for query {}", query);
        return applicationCommentSearchRepository.search(queryStringQuery(query), pageable)
            .map(applicationCommentMapper::toDto);
    }
}
