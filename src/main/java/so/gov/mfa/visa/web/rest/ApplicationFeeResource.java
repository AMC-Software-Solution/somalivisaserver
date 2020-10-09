package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.service.ApplicationFeeService;
import so.gov.mfa.visa.web.rest.errors.BadRequestAlertException;
import so.gov.mfa.visa.service.dto.ApplicationFeeDTO;

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
 * REST controller for managing {@link so.gov.mfa.visa.domain.ApplicationFee}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationFeeResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationFeeResource.class);

    private static final String ENTITY_NAME = "applicationFee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationFeeService applicationFeeService;

    public ApplicationFeeResource(ApplicationFeeService applicationFeeService) {
        this.applicationFeeService = applicationFeeService;
    }

    /**
     * {@code POST  /application-fees} : Create a new applicationFee.
     *
     * @param applicationFeeDTO the applicationFeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationFeeDTO, or with status {@code 400 (Bad Request)} if the applicationFee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-fees")
    public ResponseEntity<ApplicationFeeDTO> createApplicationFee(@Valid @RequestBody ApplicationFeeDTO applicationFeeDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationFee : {}", applicationFeeDTO);
        if (applicationFeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationFee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationFeeDTO result = applicationFeeService.save(applicationFeeDTO);
        return ResponseEntity.created(new URI("/api/application-fees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-fees} : Updates an existing applicationFee.
     *
     * @param applicationFeeDTO the applicationFeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationFeeDTO,
     * or with status {@code 400 (Bad Request)} if the applicationFeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationFeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-fees")
    public ResponseEntity<ApplicationFeeDTO> updateApplicationFee(@Valid @RequestBody ApplicationFeeDTO applicationFeeDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationFee : {}", applicationFeeDTO);
        if (applicationFeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationFeeDTO result = applicationFeeService.save(applicationFeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationFeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-fees} : get all the applicationFees.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationFees in body.
     */
    @GetMapping("/application-fees")
    public ResponseEntity<List<ApplicationFeeDTO>> getAllApplicationFees(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("visaapplication-is-null".equals(filter)) {
            log.debug("REST request to get all ApplicationFees where visaApplication is null");
            return new ResponseEntity<>(applicationFeeService.findAllWhereVisaApplicationIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of ApplicationFees");
        Page<ApplicationFeeDTO> page = applicationFeeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /application-fees/:id} : get the "id" applicationFee.
     *
     * @param id the id of the applicationFeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationFeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-fees/{id}")
    public ResponseEntity<ApplicationFeeDTO> getApplicationFee(@PathVariable Long id) {
        log.debug("REST request to get ApplicationFee : {}", id);
        Optional<ApplicationFeeDTO> applicationFeeDTO = applicationFeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationFeeDTO);
    }

    /**
     * {@code DELETE  /application-fees/:id} : delete the "id" applicationFee.
     *
     * @param id the id of the applicationFeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-fees/{id}")
    public ResponseEntity<Void> deleteApplicationFee(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationFee : {}", id);
        applicationFeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/application-fees?query=:query} : search for the applicationFee corresponding
     * to the query.
     *
     * @param query the query of the applicationFee search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/application-fees")
    public ResponseEntity<List<ApplicationFeeDTO>> searchApplicationFees(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ApplicationFees for query {}", query);
        Page<ApplicationFeeDTO> page = applicationFeeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
