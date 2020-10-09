package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.service.ApplicantService;
import so.gov.mfa.visa.web.rest.errors.BadRequestAlertException;
import so.gov.mfa.visa.service.dto.ApplicantDTO;

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
 * REST controller for managing {@link so.gov.mfa.visa.domain.Applicant}.
 */
@RestController
@RequestMapping("/api")
public class ApplicantResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantResource.class);

    private static final String ENTITY_NAME = "applicant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantService applicantService;

    public ApplicantResource(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    /**
     * {@code POST  /applicants} : Create a new applicant.
     *
     * @param applicantDTO the applicantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicantDTO, or with status {@code 400 (Bad Request)} if the applicant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicants")
    public ResponseEntity<ApplicantDTO> createApplicant(@Valid @RequestBody ApplicantDTO applicantDTO) throws URISyntaxException {
        log.debug("REST request to save Applicant : {}", applicantDTO);
        if (applicantDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantDTO result = applicantService.save(applicantDTO);
        return ResponseEntity.created(new URI("/api/applicants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicants} : Updates an existing applicant.
     *
     * @param applicantDTO the applicantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantDTO,
     * or with status {@code 400 (Bad Request)} if the applicantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicants")
    public ResponseEntity<ApplicantDTO> updateApplicant(@Valid @RequestBody ApplicantDTO applicantDTO) throws URISyntaxException {
        log.debug("REST request to update Applicant : {}", applicantDTO);
        if (applicantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicantDTO result = applicantService.save(applicantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /applicants} : get all the applicants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicants in body.
     */
    @GetMapping("/applicants")
    public ResponseEntity<List<ApplicantDTO>> getAllApplicants(Pageable pageable) {
        log.debug("REST request to get a page of Applicants");
        Page<ApplicantDTO> page = applicantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicants/:id} : get the "id" applicant.
     *
     * @param id the id of the applicantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicants/{id}")
    public ResponseEntity<ApplicantDTO> getApplicant(@PathVariable Long id) {
        log.debug("REST request to get Applicant : {}", id);
        Optional<ApplicantDTO> applicantDTO = applicantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicantDTO);
    }

    /**
     * {@code DELETE  /applicants/:id} : delete the "id" applicant.
     *
     * @param id the id of the applicantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicants/{id}")
    public ResponseEntity<Void> deleteApplicant(@PathVariable Long id) {
        log.debug("REST request to delete Applicant : {}", id);
        applicantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/applicants?query=:query} : search for the applicant corresponding
     * to the query.
     *
     * @param query the query of the applicant search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/applicants")
    public ResponseEntity<List<ApplicantDTO>> searchApplicants(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Applicants for query {}", query);
        Page<ApplicantDTO> page = applicantService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
