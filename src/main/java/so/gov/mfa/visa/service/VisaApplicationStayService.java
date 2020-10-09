package so.gov.mfa.visa.service;

import so.gov.mfa.visa.domain.VisaApplicationStay;
import so.gov.mfa.visa.repository.VisaApplicationStayRepository;
import so.gov.mfa.visa.repository.search.VisaApplicationStaySearchRepository;
import so.gov.mfa.visa.service.dto.VisaApplicationStayDTO;
import so.gov.mfa.visa.service.mapper.VisaApplicationStayMapper;
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
 * Service Implementation for managing {@link VisaApplicationStay}.
 */
@Service
@Transactional
public class VisaApplicationStayService {

    private final Logger log = LoggerFactory.getLogger(VisaApplicationStayService.class);

    private final VisaApplicationStayRepository visaApplicationStayRepository;

    private final VisaApplicationStayMapper visaApplicationStayMapper;

    private final VisaApplicationStaySearchRepository visaApplicationStaySearchRepository;

    public VisaApplicationStayService(VisaApplicationStayRepository visaApplicationStayRepository, VisaApplicationStayMapper visaApplicationStayMapper, VisaApplicationStaySearchRepository visaApplicationStaySearchRepository) {
        this.visaApplicationStayRepository = visaApplicationStayRepository;
        this.visaApplicationStayMapper = visaApplicationStayMapper;
        this.visaApplicationStaySearchRepository = visaApplicationStaySearchRepository;
    }

    /**
     * Save a visaApplicationStay.
     *
     * @param visaApplicationStayDTO the entity to save.
     * @return the persisted entity.
     */
    public VisaApplicationStayDTO save(VisaApplicationStayDTO visaApplicationStayDTO) {
        log.debug("Request to save VisaApplicationStay : {}", visaApplicationStayDTO);
        VisaApplicationStay visaApplicationStay = visaApplicationStayMapper.toEntity(visaApplicationStayDTO);
        visaApplicationStay = visaApplicationStayRepository.save(visaApplicationStay);
        VisaApplicationStayDTO result = visaApplicationStayMapper.toDto(visaApplicationStay);
        visaApplicationStaySearchRepository.save(visaApplicationStay);
        return result;
    }

    /**
     * Get all the visaApplicationStays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VisaApplicationStayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VisaApplicationStays");
        return visaApplicationStayRepository.findAll(pageable)
            .map(visaApplicationStayMapper::toDto);
    }



    /**
     *  Get all the visaApplicationStays where VisaApplication is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<VisaApplicationStayDTO> findAllWhereVisaApplicationIsNull() {
        log.debug("Request to get all visaApplicationStays where VisaApplication is null");
        return StreamSupport
            .stream(visaApplicationStayRepository.findAll().spliterator(), false)
            .filter(visaApplicationStay -> visaApplicationStay.getVisaApplication() == null)
            .map(visaApplicationStayMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one visaApplicationStay by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VisaApplicationStayDTO> findOne(Long id) {
        log.debug("Request to get VisaApplicationStay : {}", id);
        return visaApplicationStayRepository.findById(id)
            .map(visaApplicationStayMapper::toDto);
    }

    /**
     * Delete the visaApplicationStay by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VisaApplicationStay : {}", id);
        visaApplicationStayRepository.deleteById(id);
        visaApplicationStaySearchRepository.deleteById(id);
    }

    /**
     * Search for the visaApplicationStay corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VisaApplicationStayDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of VisaApplicationStays for query {}", query);
        return visaApplicationStaySearchRepository.search(queryStringQuery(query), pageable)
            .map(visaApplicationStayMapper::toDto);
    }
}
