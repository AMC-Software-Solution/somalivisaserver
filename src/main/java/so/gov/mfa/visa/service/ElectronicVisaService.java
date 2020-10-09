package so.gov.mfa.visa.service;

import so.gov.mfa.visa.domain.ElectronicVisa;
import so.gov.mfa.visa.repository.ElectronicVisaRepository;
import so.gov.mfa.visa.repository.search.ElectronicVisaSearchRepository;
import so.gov.mfa.visa.service.dto.ElectronicVisaDTO;
import so.gov.mfa.visa.service.mapper.ElectronicVisaMapper;
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
 * Service Implementation for managing {@link ElectronicVisa}.
 */
@Service
@Transactional
public class ElectronicVisaService {

    private final Logger log = LoggerFactory.getLogger(ElectronicVisaService.class);

    private final ElectronicVisaRepository electronicVisaRepository;

    private final ElectronicVisaMapper electronicVisaMapper;

    private final ElectronicVisaSearchRepository electronicVisaSearchRepository;

    public ElectronicVisaService(ElectronicVisaRepository electronicVisaRepository, ElectronicVisaMapper electronicVisaMapper, ElectronicVisaSearchRepository electronicVisaSearchRepository) {
        this.electronicVisaRepository = electronicVisaRepository;
        this.electronicVisaMapper = electronicVisaMapper;
        this.electronicVisaSearchRepository = electronicVisaSearchRepository;
    }

    /**
     * Save a electronicVisa.
     *
     * @param electronicVisaDTO the entity to save.
     * @return the persisted entity.
     */
    public ElectronicVisaDTO save(ElectronicVisaDTO electronicVisaDTO) {
        log.debug("Request to save ElectronicVisa : {}", electronicVisaDTO);
        ElectronicVisa electronicVisa = electronicVisaMapper.toEntity(electronicVisaDTO);
        electronicVisa = electronicVisaRepository.save(electronicVisa);
        ElectronicVisaDTO result = electronicVisaMapper.toDto(electronicVisa);
        electronicVisaSearchRepository.save(electronicVisa);
        return result;
    }

    /**
     * Get all the electronicVisas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ElectronicVisaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ElectronicVisas");
        return electronicVisaRepository.findAll(pageable)
            .map(electronicVisaMapper::toDto);
    }



    /**
     *  Get all the electronicVisas where VisaApplication is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ElectronicVisaDTO> findAllWhereVisaApplicationIsNull() {
        log.debug("Request to get all electronicVisas where VisaApplication is null");
        return StreamSupport
            .stream(electronicVisaRepository.findAll().spliterator(), false)
            .filter(electronicVisa -> electronicVisa.getVisaApplication() == null)
            .map(electronicVisaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one electronicVisa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ElectronicVisaDTO> findOne(Long id) {
        log.debug("Request to get ElectronicVisa : {}", id);
        return electronicVisaRepository.findById(id)
            .map(electronicVisaMapper::toDto);
    }

    /**
     * Delete the electronicVisa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ElectronicVisa : {}", id);
        electronicVisaRepository.deleteById(id);
        electronicVisaSearchRepository.deleteById(id);
    }

    /**
     * Search for the electronicVisa corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ElectronicVisaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ElectronicVisas for query {}", query);
        return electronicVisaSearchRepository.search(queryStringQuery(query), pageable)
            .map(electronicVisaMapper::toDto);
    }
}
