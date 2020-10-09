package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.service.AppSettingService;
import so.gov.mfa.visa.web.rest.errors.BadRequestAlertException;
import so.gov.mfa.visa.service.dto.AppSettingDTO;

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
 * REST controller for managing {@link so.gov.mfa.visa.domain.AppSetting}.
 */
@RestController
@RequestMapping("/api")
public class AppSettingResource {

    private final Logger log = LoggerFactory.getLogger(AppSettingResource.class);

    private static final String ENTITY_NAME = "appSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppSettingService appSettingService;

    public AppSettingResource(AppSettingService appSettingService) {
        this.appSettingService = appSettingService;
    }

    /**
     * {@code POST  /app-settings} : Create a new appSetting.
     *
     * @param appSettingDTO the appSettingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appSettingDTO, or with status {@code 400 (Bad Request)} if the appSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-settings")
    public ResponseEntity<AppSettingDTO> createAppSetting(@Valid @RequestBody AppSettingDTO appSettingDTO) throws URISyntaxException {
        log.debug("REST request to save AppSetting : {}", appSettingDTO);
        if (appSettingDTO.getId() != null) {
            throw new BadRequestAlertException("A new appSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppSettingDTO result = appSettingService.save(appSettingDTO);
        return ResponseEntity.created(new URI("/api/app-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-settings} : Updates an existing appSetting.
     *
     * @param appSettingDTO the appSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appSettingDTO,
     * or with status {@code 400 (Bad Request)} if the appSettingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-settings")
    public ResponseEntity<AppSettingDTO> updateAppSetting(@Valid @RequestBody AppSettingDTO appSettingDTO) throws URISyntaxException {
        log.debug("REST request to update AppSetting : {}", appSettingDTO);
        if (appSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppSettingDTO result = appSettingService.save(appSettingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /app-settings} : get all the appSettings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appSettings in body.
     */
    @GetMapping("/app-settings")
    public ResponseEntity<List<AppSettingDTO>> getAllAppSettings(Pageable pageable) {
        log.debug("REST request to get a page of AppSettings");
        Page<AppSettingDTO> page = appSettingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /app-settings/:id} : get the "id" appSetting.
     *
     * @param id the id of the appSettingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appSettingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-settings/{id}")
    public ResponseEntity<AppSettingDTO> getAppSetting(@PathVariable Long id) {
        log.debug("REST request to get AppSetting : {}", id);
        Optional<AppSettingDTO> appSettingDTO = appSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appSettingDTO);
    }

    /**
     * {@code DELETE  /app-settings/:id} : delete the "id" appSetting.
     *
     * @param id the id of the appSettingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-settings/{id}")
    public ResponseEntity<Void> deleteAppSetting(@PathVariable Long id) {
        log.debug("REST request to delete AppSetting : {}", id);
        appSettingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/app-settings?query=:query} : search for the appSetting corresponding
     * to the query.
     *
     * @param query the query of the appSetting search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/app-settings")
    public ResponseEntity<List<AppSettingDTO>> searchAppSettings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AppSettings for query {}", query);
        Page<AppSettingDTO> page = appSettingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
