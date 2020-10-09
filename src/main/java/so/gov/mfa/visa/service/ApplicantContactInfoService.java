package so.gov.mfa.visa.service;

import so.gov.mfa.visa.domain.ApplicantContactInfo;
import so.gov.mfa.visa.repository.ApplicantContactInfoRepository;
import so.gov.mfa.visa.repository.search.ApplicantContactInfoSearchRepository;
import so.gov.mfa.visa.service.dto.ApplicantContactInfoDTO;
import so.gov.mfa.visa.service.mapper.ApplicantContactInfoMapper;
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
 * Service Implementation for managing {@link ApplicantContactInfo}.
 */
@Service
@Transactional
public class ApplicantContactInfoService {

    private final Logger log = LoggerFactory.getLogger(ApplicantContactInfoService.class);

    private final ApplicantContactInfoRepository applicantContactInfoRepository;

    private final ApplicantContactInfoMapper applicantContactInfoMapper;

    private final ApplicantContactInfoSearchRepository applicantContactInfoSearchRepository;

    public ApplicantContactInfoService(ApplicantContactInfoRepository applicantContactInfoRepository, ApplicantContactInfoMapper applicantContactInfoMapper, ApplicantContactInfoSearchRepository applicantContactInfoSearchRepository) {
        this.applicantContactInfoRepository = applicantContactInfoRepository;
        this.applicantContactInfoMapper = applicantContactInfoMapper;
        this.applicantContactInfoSearchRepository = applicantContactInfoSearchRepository;
    }

    /**
     * Save a applicantContactInfo.
     *
     * @param applicantContactInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicantContactInfoDTO save(ApplicantContactInfoDTO applicantContactInfoDTO) {
        log.debug("Request to save ApplicantContactInfo : {}", applicantContactInfoDTO);
        ApplicantContactInfo applicantContactInfo = applicantContactInfoMapper.toEntity(applicantContactInfoDTO);
        applicantContactInfo = applicantContactInfoRepository.save(applicantContactInfo);
        ApplicantContactInfoDTO result = applicantContactInfoMapper.toDto(applicantContactInfo);
        applicantContactInfoSearchRepository.save(applicantContactInfo);
        return result;
    }

    /**
     * Get all the applicantContactInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantContactInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicantContactInfos");
        return applicantContactInfoRepository.findAll(pageable)
            .map(applicantContactInfoMapper::toDto);
    }



    /**
     *  Get all the applicantContactInfos where Applicant is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ApplicantContactInfoDTO> findAllWhereApplicantIsNull() {
        log.debug("Request to get all applicantContactInfos where Applicant is null");
        return StreamSupport
            .stream(applicantContactInfoRepository.findAll().spliterator(), false)
            .filter(applicantContactInfo -> applicantContactInfo.getApplicant() == null)
            .map(applicantContactInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one applicantContactInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicantContactInfoDTO> findOne(Long id) {
        log.debug("Request to get ApplicantContactInfo : {}", id);
        return applicantContactInfoRepository.findById(id)
            .map(applicantContactInfoMapper::toDto);
    }

    /**
     * Delete the applicantContactInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicantContactInfo : {}", id);
        applicantContactInfoRepository.deleteById(id);
        applicantContactInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the applicantContactInfo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantContactInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ApplicantContactInfos for query {}", query);
        return applicantContactInfoSearchRepository.search(queryStringQuery(query), pageable)
            .map(applicantContactInfoMapper::toDto);
    }
}
