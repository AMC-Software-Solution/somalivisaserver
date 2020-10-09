package so.gov.mfa.visa.service;

import so.gov.mfa.visa.domain.VisaApplication;
import so.gov.mfa.visa.repository.VisaApplicationRepository;
import so.gov.mfa.visa.repository.search.VisaApplicationSearchRepository;
import so.gov.mfa.visa.service.dto.VisaApplicationDTO;
import so.gov.mfa.visa.service.mapper.VisaApplicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link VisaApplication}.
 */
@Service
@Transactional
public class VisaApplicationService {

    private final Logger log = LoggerFactory.getLogger(VisaApplicationService.class);

    private final VisaApplicationRepository visaApplicationRepository;

    private final VisaApplicationMapper visaApplicationMapper;

    private final VisaApplicationSearchRepository visaApplicationSearchRepository;

    public VisaApplicationService(VisaApplicationRepository visaApplicationRepository, VisaApplicationMapper visaApplicationMapper, VisaApplicationSearchRepository visaApplicationSearchRepository) {
        this.visaApplicationRepository = visaApplicationRepository;
        this.visaApplicationMapper = visaApplicationMapper;
        this.visaApplicationSearchRepository = visaApplicationSearchRepository;
    }

    /**
     * Save a visaApplication.
     *
     * @param visaApplicationDTO the entity to save.
     * @return the persisted entity.
     */
    public VisaApplicationDTO save(VisaApplicationDTO visaApplicationDTO) {
        log.debug("Request to save VisaApplication : {}", visaApplicationDTO);
        VisaApplication visaApplication = visaApplicationMapper.toEntity(visaApplicationDTO);
        visaApplication = visaApplicationRepository.save(visaApplication);
        VisaApplicationDTO result = visaApplicationMapper.toDto(visaApplication);
        visaApplicationSearchRepository.save(visaApplication);
        return result;
    }

    /**
     * Get all the visaApplications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VisaApplicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VisaApplications");
        return visaApplicationRepository.findAll(pageable)
            .map(visaApplicationMapper::toDto);
    }


    /**
     * Get one visaApplication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VisaApplicationDTO> findOne(Long id) {
        log.debug("Request to get VisaApplication : {}", id);
        return visaApplicationRepository.findById(id)
            .map(visaApplicationMapper::toDto);
    }

    /**
     * Delete the visaApplication by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VisaApplication : {}", id);
        visaApplicationRepository.deleteById(id);
        visaApplicationSearchRepository.deleteById(id);
    }

    /**
     * Search for the visaApplication corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VisaApplicationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of VisaApplications for query {}", query);
        return visaApplicationSearchRepository.search(queryStringQuery(query), pageable)
            .map(visaApplicationMapper::toDto);
    }
}
