package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.service.VisaApplicationService;
import so.gov.mfa.visa.web.rest.errors.BadRequestAlertException;
import so.gov.mfa.visa.service.dto.VisaApplicationDTO;

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
 * REST controller for managing {@link so.gov.mfa.visa.domain.VisaApplication}.
 */
@RestController
@RequestMapping("/api")
public class VisaApplicationResource {

    private final Logger log = LoggerFactory.getLogger(VisaApplicationResource.class);

    private static final String ENTITY_NAME = "visaApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VisaApplicationService visaApplicationService;

    public VisaApplicationResource(VisaApplicationService visaApplicationService) {
        this.visaApplicationService = visaApplicationService;
    }

    /**
     * {@code POST  /visa-applications} : Create a new visaApplication.
     *
     * @param visaApplicationDTO the visaApplicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new visaApplicationDTO, or with status {@code 400 (Bad Request)} if the visaApplication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/visa-applications")
    public ResponseEntity<VisaApplicationDTO> createVisaApplication(@Valid @RequestBody VisaApplicationDTO visaApplicationDTO) throws URISyntaxException {
        log.debug("REST request to save VisaApplication : {}", visaApplicationDTO);
        if (visaApplicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new visaApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VisaApplicationDTO result = visaApplicationService.save(visaApplicationDTO);
        return ResponseEntity.created(new URI("/api/visa-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /visa-applications} : Updates an existing visaApplication.
     *
     * @param visaApplicationDTO the visaApplicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated visaApplicationDTO,
     * or with status {@code 400 (Bad Request)} if the visaApplicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the visaApplicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/visa-applications")
    public ResponseEntity<VisaApplicationDTO> updateVisaApplication(@Valid @RequestBody VisaApplicationDTO visaApplicationDTO) throws URISyntaxException {
        log.debug("REST request to update VisaApplication : {}", visaApplicationDTO);
        if (visaApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VisaApplicationDTO result = visaApplicationService.save(visaApplicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, visaApplicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /visa-applications} : get all the visaApplications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of visaApplications in body.
     */
    @GetMapping("/visa-applications")
    public ResponseEntity<List<VisaApplicationDTO>> getAllVisaApplications(Pageable pageable) {
        log.debug("REST request to get a page of VisaApplications");
        Page<VisaApplicationDTO> page = visaApplicationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /visa-applications/:id} : get the "id" visaApplication.
     *
     * @param id the id of the visaApplicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the visaApplicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/visa-applications/{id}")
    public ResponseEntity<VisaApplicationDTO> getVisaApplication(@PathVariable Long id) {
        log.debug("REST request to get VisaApplication : {}", id);
        Optional<VisaApplicationDTO> visaApplicationDTO = visaApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(visaApplicationDTO);
    }

    /**
     * {@code DELETE  /visa-applications/:id} : delete the "id" visaApplication.
     *
     * @param id the id of the visaApplicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/visa-applications/{id}")
    public ResponseEntity<Void> deleteVisaApplication(@PathVariable Long id) {
        log.debug("REST request to delete VisaApplication : {}", id);
        visaApplicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/visa-applications?query=:query} : search for the visaApplication corresponding
     * to the query.
     *
     * @param query the query of the visaApplication search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/visa-applications")
    public ResponseEntity<List<VisaApplicationDTO>> searchVisaApplications(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of VisaApplications for query {}", query);
        Page<VisaApplicationDTO> page = visaApplicationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
