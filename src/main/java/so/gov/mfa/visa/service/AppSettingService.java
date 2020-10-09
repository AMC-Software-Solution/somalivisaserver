package so.gov.mfa.visa.service;

import so.gov.mfa.visa.domain.AppSetting;
import so.gov.mfa.visa.repository.AppSettingRepository;
import so.gov.mfa.visa.repository.search.AppSettingSearchRepository;
import so.gov.mfa.visa.service.dto.AppSettingDTO;
import so.gov.mfa.visa.service.mapper.AppSettingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AppSetting}.
 */
@Service
@Transactional
public class AppSettingService {

    private final Logger log = LoggerFactory.getLogger(AppSettingService.class);

    private final AppSettingRepository appSettingRepository;

    private final AppSettingMapper appSettingMapper;

    private final AppSettingSearchRepository appSettingSearchRepository;

    public AppSettingService(AppSettingRepository appSettingRepository, AppSettingMapper appSettingMapper, AppSettingSearchRepository appSettingSearchRepository) {
        this.appSettingRepository = appSettingRepository;
        this.appSettingMapper = appSettingMapper;
        this.appSettingSearchRepository = appSettingSearchRepository;
    }

    /**
     * Save a appSetting.
     *
     * @param appSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public AppSettingDTO save(AppSettingDTO appSettingDTO) {
        log.debug("Request to save AppSetting : {}", appSettingDTO);
        AppSetting appSetting = appSettingMapper.toEntity(appSettingDTO);
        appSetting = appSettingRepository.save(appSetting);
        AppSettingDTO result = appSettingMapper.toDto(appSetting);
        appSettingSearchRepository.save(appSetting);
        return result;
    }

    /**
     * Get all the appSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppSettings");
        return appSettingRepository.findAll(pageable)
            .map(appSettingMapper::toDto);
    }


    /**
     * Get one appSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppSettingDTO> findOne(Long id) {
        log.debug("Request to get AppSetting : {}", id);
        return appSettingRepository.findById(id)
            .map(appSettingMapper::toDto);
    }

    /**
     * Delete the appSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppSetting : {}", id);
        appSettingRepository.deleteById(id);
        appSettingSearchRepository.deleteById(id);
    }

    /**
     * Search for the appSetting corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppSettingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AppSettings for query {}", query);
        return appSettingSearchRepository.search(queryStringQuery(query), pageable)
            .map(appSettingMapper::toDto);
    }
}
