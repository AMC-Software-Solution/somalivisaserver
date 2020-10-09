package so.gov.mfa.visa.service;

import so.gov.mfa.visa.domain.ApplicationFee;
import so.gov.mfa.visa.repository.ApplicationFeeRepository;
import so.gov.mfa.visa.repository.search.ApplicationFeeSearchRepository;
import so.gov.mfa.visa.service.dto.ApplicationFeeDTO;
import so.gov.mfa.visa.service.mapper.ApplicationFeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ApplicationFee}.
 */
@Service
@Transactional
public class ApplicationFeeService {

    private final Logger log = LoggerFactory.getLogger(ApplicationFeeService.class);

    private final ApplicationFeeRepository applicationFeeRepository;

    private final ApplicationFeeMapper applicationFeeMapper;

    private final ApplicationFeeSearchRepository applicationFeeSearchRepository;

    public ApplicationFeeService(ApplicationFeeRepository applicationFeeRepository, ApplicationFeeMapper applicationFeeMapper, ApplicationFeeSearchRepository applicationFeeSearchRepository) {
        this.applicationFeeRepository = applicationFeeRepository;
        this.applicationFeeMapper = applicationFeeMapper;
        this.applicationFeeSearchRepository = applicationFeeSearchRepository;
    }

    /**
     * Save a applicationFee.
     *
     * @param applicationFeeDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicationFeeDTO save(ApplicationFeeDTO applicationFeeDTO) {
        log.debug("Request to save ApplicationFee : {}", applicationFeeDTO);
        ApplicationFee applicationFee = applicationFeeMapper.toEntity(applicationFeeDTO);
        applicationFee = applicationFeeRepository.save(applicationFee);
        ApplicationFeeDTO result = applicationFeeMapper.toDto(applicationFee);
        applicationFeeSearchRepository.save(applicationFee);
        return result;
    }

    /**
     * Get all the applicationFees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationFeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationFees");
        return applicationFeeRepository.findAll(pageable)
            .map(applicationFeeMapper::toDto);
    }



    /**
     *  Get all the applicationFees where VisaApplication is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ApplicationFeeDTO> findAllWhereVisaApplicationIsNull() {
        log.debug("Request to get all applicationFees where VisaApplication is null");
        return StreamSupport
            .stream(applicationFeeRepository.findAll().spliterator(), false)
            .filter(applicationFee -> applicationFee.getVisaApplication() == null)
            .map(applicationFeeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one applicationFee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicationFeeDTO> findOne(Long id) {
        log.debug("Request to get ApplicationFee : {}", id);
        return applicationFeeRepository.findById(id)
            .map(applicationFeeMapper::toDto);
    }

    /**
     * Delete the applicationFee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicationFee : {}", id);
        applicationFeeRepository.deleteById(id);
        applicationFeeSearchRepository.deleteById(id);
    }

    /**
     * Search for the applicationFee corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationFeeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ApplicationFees for query {}", query);
        return applicationFeeSearchRepository.search(queryStringQuery(query), pageable)
            .map(applicationFeeMapper::toDto);
    }
}
