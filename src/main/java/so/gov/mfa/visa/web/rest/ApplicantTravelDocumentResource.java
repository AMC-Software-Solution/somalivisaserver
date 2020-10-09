package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.service.ApplicantTravelDocumentService;
import so.gov.mfa.visa.web.rest.errors.BadRequestAlertException;
import so.gov.mfa.visa.service.dto.ApplicantTravelDocumentDTO;

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
 * REST controller for managing {@link so.gov.mfa.visa.domain.ApplicantTravelDocument}.
 */
@RestController
@RequestMapping("/api")
public class ApplicantTravelDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantTravelDocumentResource.class);

    private static final String ENTITY_NAME = "applicantTravelDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantTravelDocumentService applicantTravelDocumentService;

    public ApplicantTravelDocumentResource(ApplicantTravelDocumentService applicantTravelDocumentService) {
        this.applicantTravelDocumentService = applicantTravelDocumentService;
    }

    /**
     * {@code POST  /applicant-travel-documents} : Create a new applicantTravelDocument.
     *
     * @param applicantTravelDocumentDTO the applicantTravelDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicantTravelDocumentDTO, or with status {@code 400 (Bad Request)} if the applicantTravelDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicant-travel-documents")
    public ResponseEntity<ApplicantTravelDocumentDTO> createApplicantTravelDocument(@Valid @RequestBody ApplicantTravelDocumentDTO applicantTravelDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicantTravelDocument : {}", applicantTravelDocumentDTO);
        if (applicantTravelDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicantTravelDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantTravelDocumentDTO result = applicantTravelDocumentService.save(applicantTravelDocumentDTO);
        return ResponseEntity.created(new URI("/api/applicant-travel-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicant-travel-documents} : Updates an existing applicantTravelDocument.
     *
     * @param applicantTravelDocumentDTO the applicantTravelDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantTravelDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the applicantTravelDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicantTravelDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicant-travel-documents")
    public ResponseEntity<ApplicantTravelDocumentDTO> updateApplicantTravelDocument(@Valid @RequestBody ApplicantTravelDocumentDTO applicantTravelDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicantTravelDocument : {}", applicantTravelDocumentDTO);
        if (applicantTravelDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicantTravelDocumentDTO result = applicantTravelDocumentService.save(applicantTravelDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicantTravelDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /applicant-travel-documents} : get all the applicantTravelDocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicantTravelDocuments in body.
     */
    @GetMapping("/applicant-travel-documents")
    public ResponseEntity<List<ApplicantTravelDocumentDTO>> getAllApplicantTravelDocuments(Pageable pageable) {
        log.debug("REST request to get a page of ApplicantTravelDocuments");
        Page<ApplicantTravelDocumentDTO> page = applicantTravelDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicant-travel-documents/:id} : get the "id" applicantTravelDocument.
     *
     * @param id the id of the applicantTravelDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicantTravelDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicant-travel-documents/{id}")
    public ResponseEntity<ApplicantTravelDocumentDTO> getApplicantTravelDocument(@PathVariable Long id) {
        log.debug("REST request to get ApplicantTravelDocument : {}", id);
        Optional<ApplicantTravelDocumentDTO> applicantTravelDocumentDTO = applicantTravelDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicantTravelDocumentDTO);
    }

    /**
     * {@code DELETE  /applicant-travel-documents/:id} : delete the "id" applicantTravelDocument.
     *
     * @param id the id of the applicantTravelDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicant-travel-documents/{id}")
    public ResponseEntity<Void> deleteApplicantTravelDocument(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantTravelDocument : {}", id);
        applicantTravelDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/applicant-travel-documents?query=:query} : search for the applicantTravelDocument corresponding
     * to the query.
     *
     * @param query the query of the applicantTravelDocument search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/applicant-travel-documents")
    public ResponseEntity<List<ApplicantTravelDocumentDTO>> searchApplicantTravelDocuments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ApplicantTravelDocuments for query {}", query);
        Page<ApplicantTravelDocumentDTO> page = applicantTravelDocumentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
