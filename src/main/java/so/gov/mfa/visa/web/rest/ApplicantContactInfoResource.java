package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.service.ApplicantContactInfoService;
import so.gov.mfa.visa.web.rest.errors.BadRequestAlertException;
import so.gov.mfa.visa.service.dto.ApplicantContactInfoDTO;

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
 * REST controller for managing {@link so.gov.mfa.visa.domain.ApplicantContactInfo}.
 */
@RestController
@RequestMapping("/api")
public class ApplicantContactInfoResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantContactInfoResource.class);

    private static final String ENTITY_NAME = "applicantContactInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantContactInfoService applicantContactInfoService;

    public ApplicantContactInfoResource(ApplicantContactInfoService applicantContactInfoService) {
        this.applicantContactInfoService = applicantContactInfoService;
    }

    /**
     * {@code POST  /applicant-contact-infos} : Create a new applicantContactInfo.
     *
     * @param applicantContactInfoDTO the applicantContactInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicantContactInfoDTO, or with status {@code 400 (Bad Request)} if the applicantContactInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicant-contact-infos")
    public ResponseEntity<ApplicantContactInfoDTO> createApplicantContactInfo(@Valid @RequestBody ApplicantContactInfoDTO applicantContactInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicantContactInfo : {}", applicantContactInfoDTO);
        if (applicantContactInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicantContactInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantContactInfoDTO result = applicantContactInfoService.save(applicantContactInfoDTO);
        return ResponseEntity.created(new URI("/api/applicant-contact-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicant-contact-infos} : Updates an existing applicantContactInfo.
     *
     * @param applicantContactInfoDTO the applicantContactInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantContactInfoDTO,
     * or with status {@code 400 (Bad Request)} if the applicantContactInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicantContactInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicant-contact-infos")
    public ResponseEntity<ApplicantContactInfoDTO> updateApplicantContactInfo(@Valid @RequestBody ApplicantContactInfoDTO applicantContactInfoDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicantContactInfo : {}", applicantContactInfoDTO);
        if (applicantContactInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicantContactInfoDTO result = applicantContactInfoService.save(applicantContactInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicantContactInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /applicant-contact-infos} : get all the applicantContactInfos.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicantContactInfos in body.
     */
    @GetMapping("/applicant-contact-infos")
    public ResponseEntity<List<ApplicantContactInfoDTO>> getAllApplicantContactInfos(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("applicant-is-null".equals(filter)) {
            log.debug("REST request to get all ApplicantContactInfos where applicant is null");
            return new ResponseEntity<>(applicantContactInfoService.findAllWhereApplicantIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of ApplicantContactInfos");
        Page<ApplicantContactInfoDTO> page = applicantContactInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicant-contact-infos/:id} : get the "id" applicantContactInfo.
     *
     * @param id the id of the applicantContactInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicantContactInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicant-contact-infos/{id}")
    public ResponseEntity<ApplicantContactInfoDTO> getApplicantContactInfo(@PathVariable Long id) {
        log.debug("REST request to get ApplicantContactInfo : {}", id);
        Optional<ApplicantContactInfoDTO> applicantContactInfoDTO = applicantContactInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicantContactInfoDTO);
    }

    /**
     * {@code DELETE  /applicant-contact-infos/:id} : delete the "id" applicantContactInfo.
     *
     * @param id the id of the applicantContactInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicant-contact-infos/{id}")
    public ResponseEntity<Void> deleteApplicantContactInfo(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantContactInfo : {}", id);
        applicantContactInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/applicant-contact-infos?query=:query} : search for the applicantContactInfo corresponding
     * to the query.
     *
     * @param query the query of the applicantContactInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/applicant-contact-infos")
    public ResponseEntity<List<ApplicantContactInfoDTO>> searchApplicantContactInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ApplicantContactInfos for query {}", query);
        Page<ApplicantContactInfoDTO> page = applicantContactInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
