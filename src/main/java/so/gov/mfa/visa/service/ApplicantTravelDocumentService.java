package so.gov.mfa.visa.service;

import so.gov.mfa.visa.domain.ApplicantTravelDocument;
import so.gov.mfa.visa.repository.ApplicantTravelDocumentRepository;
import so.gov.mfa.visa.repository.search.ApplicantTravelDocumentSearchRepository;
import so.gov.mfa.visa.service.dto.ApplicantTravelDocumentDTO;
import so.gov.mfa.visa.service.mapper.ApplicantTravelDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ApplicantTravelDocument}.
 */
@Service
@Transactional
public class ApplicantTravelDocumentService {

    private final Logger log = LoggerFactory.getLogger(ApplicantTravelDocumentService.class);

    private final ApplicantTravelDocumentRepository applicantTravelDocumentRepository;

    private final ApplicantTravelDocumentMapper applicantTravelDocumentMapper;

    private final ApplicantTravelDocumentSearchRepository applicantTravelDocumentSearchRepository;

    public ApplicantTravelDocumentService(ApplicantTravelDocumentRepository applicantTravelDocumentRepository, ApplicantTravelDocumentMapper applicantTravelDocumentMapper, ApplicantTravelDocumentSearchRepository applicantTravelDocumentSearchRepository) {
        this.applicantTravelDocumentRepository = applicantTravelDocumentRepository;
        this.applicantTravelDocumentMapper = applicantTravelDocumentMapper;
        this.applicantTravelDocumentSearchRepository = applicantTravelDocumentSearchRepository;
    }

    /**
     * Save a applicantTravelDocument.
     *
     * @param applicantTravelDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicantTravelDocumentDTO save(ApplicantTravelDocumentDTO applicantTravelDocumentDTO) {
        log.debug("Request to save ApplicantTravelDocument : {}", applicantTravelDocumentDTO);
        ApplicantTravelDocument applicantTravelDocument = applicantTravelDocumentMapper.toEntity(applicantTravelDocumentDTO);
        applicantTravelDocument = applicantTravelDocumentRepository.save(applicantTravelDocument);
        ApplicantTravelDocumentDTO result = applicantTravelDocumentMapper.toDto(applicantTravelDocument);
        applicantTravelDocumentSearchRepository.save(applicantTravelDocument);
        return result;
    }

    /**
     * Get all the applicantTravelDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantTravelDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicantTravelDocuments");
        return applicantTravelDocumentRepository.findAll(pageable)
            .map(applicantTravelDocumentMapper::toDto);
    }


    /**
     * Get one applicantTravelDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicantTravelDocumentDTO> findOne(Long id) {
        log.debug("Request to get ApplicantTravelDocument : {}", id);
        return applicantTravelDocumentRepository.findById(id)
            .map(applicantTravelDocumentMapper::toDto);
    }

    /**
     * Delete the applicantTravelDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicantTravelDocument : {}", id);
        applicantTravelDocumentRepository.deleteById(id);
        applicantTravelDocumentSearchRepository.deleteById(id);
    }

    /**
     * Search for the applicantTravelDocument corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantTravelDocumentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ApplicantTravelDocuments for query {}", query);
        return applicantTravelDocumentSearchRepository.search(queryStringQuery(query), pageable)
            .map(applicantTravelDocumentMapper::toDto);
    }
}
