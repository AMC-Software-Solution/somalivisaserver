package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.service.ApplicationCommentService;
import so.gov.mfa.visa.web.rest.errors.BadRequestAlertException;
import so.gov.mfa.visa.service.dto.ApplicationCommentDTO;

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
 * REST controller for managing {@link so.gov.mfa.visa.domain.ApplicationComment}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationCommentResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationCommentResource.class);

    private static final String ENTITY_NAME = "applicationComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationCommentService applicationCommentService;

    public ApplicationCommentResource(ApplicationCommentService applicationCommentService) {
        this.applicationCommentService = applicationCommentService;
    }

    /**
     * {@code POST  /application-comments} : Create a new applicationComment.
     *
     * @param applicationCommentDTO the applicationCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationCommentDTO, or with status {@code 400 (Bad Request)} if the applicationComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-comments")
    public ResponseEntity<ApplicationCommentDTO> createApplicationComment(@Valid @RequestBody ApplicationCommentDTO applicationCommentDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationComment : {}", applicationCommentDTO);
        if (applicationCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationCommentDTO result = applicationCommentService.save(applicationCommentDTO);
        return ResponseEntity.created(new URI("/api/application-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-comments} : Updates an existing applicationComment.
     *
     * @param applicationCommentDTO the applicationCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationCommentDTO,
     * or with status {@code 400 (Bad Request)} if the applicationCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-comments")
    public ResponseEntity<ApplicationCommentDTO> updateApplicationComment(@Valid @RequestBody ApplicationCommentDTO applicationCommentDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationComment : {}", applicationCommentDTO);
        if (applicationCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationCommentDTO result = applicationCommentService.save(applicationCommentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-comments} : get all the applicationComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationComments in body.
     */
    @GetMapping("/application-comments")
    public ResponseEntity<List<ApplicationCommentDTO>> getAllApplicationComments(Pageable pageable) {
        log.debug("REST request to get a page of ApplicationComments");
        Page<ApplicationCommentDTO> page = applicationCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /application-comments/:id} : get the "id" applicationComment.
     *
     * @param id the id of the applicationCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-comments/{id}")
    public ResponseEntity<ApplicationCommentDTO> getApplicationComment(@PathVariable Long id) {
        log.debug("REST request to get ApplicationComment : {}", id);
        Optional<ApplicationCommentDTO> applicationCommentDTO = applicationCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationCommentDTO);
    }

    /**
     * {@code DELETE  /application-comments/:id} : delete the "id" applicationComment.
     *
     * @param id the id of the applicationCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-comments/{id}")
    public ResponseEntity<Void> deleteApplicationComment(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationComment : {}", id);
        applicationCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/application-comments?query=:query} : search for the applicationComment corresponding
     * to the query.
     *
     * @param query the query of the applicationComment search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/application-comments")
    public ResponseEntity<List<ApplicationCommentDTO>> searchApplicationComments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ApplicationComments for query {}", query);
        Page<ApplicationCommentDTO> page = applicationCommentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
