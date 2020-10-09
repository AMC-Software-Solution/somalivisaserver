package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.service.SystemSettingService;
import so.gov.mfa.visa.web.rest.errors.BadRequestAlertException;
import so.gov.mfa.visa.service.dto.SystemSettingDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link so.gov.mfa.visa.domain.SystemSetting}.
 */
@RestController
@RequestMapping("/api")
public class SystemSettingResource {

    private final Logger log = LoggerFactory.getLogger(SystemSettingResource.class);

    private static final String ENTITY_NAME = "systemSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemSettingService systemSettingService;

    public SystemSettingResource(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

    /**
     * {@code POST  /system-settings} : Create a new systemSetting.
     *
     * @param systemSettingDTO the systemSettingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemSettingDTO, or with status {@code 400 (Bad Request)} if the systemSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/system-settings")
    public ResponseEntity<SystemSettingDTO> createSystemSetting(@Valid @RequestBody SystemSettingDTO systemSettingDTO) throws URISyntaxException {
        log.debug("REST request to save SystemSetting : {}", systemSettingDTO);
        if (systemSettingDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemSettingDTO result = systemSettingService.save(systemSettingDTO);
        return ResponseEntity.created(new URI("/api/system-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /system-settings} : Updates an existing systemSetting.
     *
     * @param systemSettingDTO the systemSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemSettingDTO,
     * or with status {@code 400 (Bad Request)} if the systemSettingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/system-settings")
    public ResponseEntity<SystemSettingDTO> updateSystemSetting(@Valid @RequestBody SystemSettingDTO systemSettingDTO) throws URISyntaxException {
        log.debug("REST request to update SystemSetting : {}", systemSettingDTO);
        if (systemSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemSettingDTO result = systemSettingService.save(systemSettingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /system-settings} : get all the systemSettings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemSettings in body.
     */
    @GetMapping("/system-settings")
    public ResponseEntity<List<SystemSettingDTO>> getAllSystemSettings(Pageable pageable) {
        log.debug("REST request to get a page of SystemSettings");
        Page<SystemSettingDTO> page = systemSettingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /system-settings/:id} : get the "id" systemSetting.
     *
     * @param id the id of the systemSettingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemSettingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/system-settings/{id}")
    public ResponseEntity<SystemSettingDTO> getSystemSetting(@PathVariable Long id) {
        log.debug("REST request to get SystemSetting : {}", id);
        Optional<SystemSettingDTO> systemSettingDTO = systemSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemSettingDTO);
    }

    /**
     * {@code DELETE  /system-settings/:id} : delete the "id" systemSetting.
     *
     * @param id the id of the systemSettingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/system-settings/{id}")
    public ResponseEntity<Void> deleteSystemSetting(@PathVariable Long id) {
        log.debug("REST request to delete SystemSetting : {}", id);
        systemSettingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/system-settings?query=:query} : search for the systemSetting corresponding
     * to the query.
     *
     * @param query the query of the systemSetting search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/system-settings")
    public ResponseEntity<List<SystemSettingDTO>> searchSystemSettings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SystemSettings for query {}", query);
        Page<SystemSettingDTO> page = systemSettingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
