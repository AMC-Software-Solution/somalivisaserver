package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.service.VisaApplicationStayService;
import so.gov.mfa.visa.web.rest.errors.BadRequestAlertException;
import so.gov.mfa.visa.service.dto.VisaApplicationStayDTO;

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
 * REST controller for managing {@link so.gov.mfa.visa.domain.VisaApplicationStay}.
 */
@RestController
@RequestMapping("/api")
public class VisaApplicationStayResource {

    private final Logger log = LoggerFactory.getLogger(VisaApplicationStayResource.class);

    private static final String ENTITY_NAME = "visaApplicationStay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VisaApplicationStayService visaApplicationStayService;

    public VisaApplicationStayResource(VisaApplicationStayService visaApplicationStayService) {
        this.visaApplicationStayService = visaApplicationStayService;
    }

    /**
     * {@code POST  /visa-application-stays} : Create a new visaApplicationStay.
     *
     * @param visaApplicationStayDTO the visaApplicationStayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new visaApplicationStayDTO, or with status {@code 400 (Bad Request)} if the visaApplicationStay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/visa-application-stays")
    public ResponseEntity<VisaApplicationStayDTO> createVisaApplicationStay(@Valid @RequestBody VisaApplicationStayDTO visaApplicationStayDTO) throws URISyntaxException {
        log.debug("REST request to save VisaApplicationStay : {}", visaApplicationStayDTO);
        if (visaApplicationStayDTO.getId() != null) {
            throw new BadRequestAlertException("A new visaApplicationStay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VisaApplicationStayDTO result = visaApplicationStayService.save(visaApplicationStayDTO);
        return ResponseEntity.created(new URI("/api/visa-application-stays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /visa-application-stays} : Updates an existing visaApplicationStay.
     *
     * @param visaApplicationStayDTO the visaApplicationStayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated visaApplicationStayDTO,
     * or with status {@code 400 (Bad Request)} if the visaApplicationStayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the visaApplicationStayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/visa-application-stays")
    public ResponseEntity<VisaApplicationStayDTO> updateVisaApplicationStay(@Valid @RequestBody VisaApplicationStayDTO visaApplicationStayDTO) throws URISyntaxException {
        log.debug("REST request to update VisaApplicationStay : {}", visaApplicationStayDTO);
        if (visaApplicationStayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VisaApplicationStayDTO result = visaApplicationStayService.save(visaApplicationStayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, visaApplicationStayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /visa-application-stays} : get all the visaApplicationStays.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of visaApplicationStays in body.
     */
    @GetMapping("/visa-application-stays")
    public ResponseEntity<List<VisaApplicationStayDTO>> getAllVisaApplicationStays(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("visaapplication-is-null".equals(filter)) {
            log.debug("REST request to get all VisaApplicationStays where visaApplication is null");
            return new ResponseEntity<>(visaApplicationStayService.findAllWhereVisaApplicationIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of VisaApplicationStays");
        Page<VisaApplicationStayDTO> page = visaApplicationStayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /visa-application-stays/:id} : get the "id" visaApplicationStay.
     *
     * @param id the id of the visaApplicationStayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the visaApplicationStayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/visa-application-stays/{id}")
    public ResponseEntity<VisaApplicationStayDTO> getVisaApplicationStay(@PathVariable Long id) {
        log.debug("REST request to get VisaApplicationStay : {}", id);
        Optional<VisaApplicationStayDTO> visaApplicationStayDTO = visaApplicationStayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(visaApplicationStayDTO);
    }

    /**
     * {@code DELETE  /visa-application-stays/:id} : delete the "id" visaApplicationStay.
     *
     * @param id the id of the visaApplicationStayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/visa-application-stays/{id}")
    public ResponseEntity<Void> deleteVisaApplicationStay(@PathVariable Long id) {
        log.debug("REST request to delete VisaApplicationStay : {}", id);
        visaApplicationStayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/visa-application-stays?query=:query} : search for the visaApplicationStay corresponding
     * to the query.
     *
     * @param query the query of the visaApplicationStay search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/visa-application-stays")
    public ResponseEntity<List<VisaApplicationStayDTO>> searchVisaApplicationStays(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of VisaApplicationStays for query {}", query);
        Page<VisaApplicationStayDTO> page = visaApplicationStayService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
