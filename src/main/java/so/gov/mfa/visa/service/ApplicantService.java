package so.gov.mfa.visa.service;

import so.gov.mfa.visa.domain.Applicant;
import so.gov.mfa.visa.repository.ApplicantRepository;
import so.gov.mfa.visa.repository.search.ApplicantSearchRepository;
import so.gov.mfa.visa.service.dto.ApplicantDTO;
import so.gov.mfa.visa.service.mapper.ApplicantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Applicant}.
 */
@Service
@Transactional
public class ApplicantService {

    private final Logger log = LoggerFactory.getLogger(ApplicantService.class);

    private final ApplicantRepository applicantRepository;

    private final ApplicantMapper applicantMapper;

    private final ApplicantSearchRepository applicantSearchRepository;

    public ApplicantService(ApplicantRepository applicantRepository, ApplicantMapper applicantMapper, ApplicantSearchRepository applicantSearchRepository) {
        this.applicantRepository = applicantRepository;
        this.applicantMapper = applicantMapper;
        this.applicantSearchRepository = applicantSearchRepository;
    }

    /**
     * Save a applicant.
     *
     * @param applicantDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicantDTO save(ApplicantDTO applicantDTO) {
        log.debug("Request to save Applicant : {}", applicantDTO);
        Applicant applicant = applicantMapper.toEntity(applicantDTO);
        applicant = applicantRepository.save(applicant);
        ApplicantDTO result = applicantMapper.toDto(applicant);
        applicantSearchRepository.save(applicant);
        return result;
    }

    /**
     * Get all the applicants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Applicants");
        return applicantRepository.findAll(pageable)
            .map(applicantMapper::toDto);
    }


    /**
     * Get one applicant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicantDTO> findOne(Long id) {
        log.debug("Request to get Applicant : {}", id);
        return applicantRepository.findById(id)
            .map(applicantMapper::toDto);
    }

    /**
     * Delete the applicant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Applicant : {}", id);
        applicantRepository.deleteById(id);
        applicantSearchRepository.deleteById(id);
    }

    /**
     * Search for the applicant corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Applicants for query {}", query);
        return applicantSearchRepository.search(queryStringQuery(query), pageable)
            .map(applicantMapper::toDto);
    }
}
