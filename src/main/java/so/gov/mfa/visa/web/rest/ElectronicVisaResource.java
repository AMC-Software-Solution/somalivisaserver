package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.service.ElectronicVisaService;
import so.gov.mfa.visa.web.rest.errors.BadRequestAlertException;
import so.gov.mfa.visa.service.dto.ElectronicVisaDTO;

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
 * REST controller for managing {@link so.gov.mfa.visa.domain.ElectronicVisa}.
 */
@RestController
@RequestMapping("/api")
public class ElectronicVisaResource {

    private final Logger log = LoggerFactory.getLogger(ElectronicVisaResource.class);

    private static final String ENTITY_NAME = "electronicVisa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElectronicVisaService electronicVisaService;

    public ElectronicVisaResource(ElectronicVisaService electronicVisaService) {
        this.electronicVisaService = electronicVisaService;
    }

    /**
     * {@code POST  /electronic-visas} : Create a new electronicVisa.
     *
     * @param electronicVisaDTO the electronicVisaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new electronicVisaDTO, or with status {@code 400 (Bad Request)} if the electronicVisa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/electronic-visas")
    public ResponseEntity<ElectronicVisaDTO> createElectronicVisa(@Valid @RequestBody ElectronicVisaDTO electronicVisaDTO) throws URISyntaxException {
        log.debug("REST request to save ElectronicVisa : {}", electronicVisaDTO);
        if (electronicVisaDTO.getId() != null) {
            throw new BadRequestAlertException("A new electronicVisa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElectronicVisaDTO result = electronicVisaService.save(electronicVisaDTO);
        return ResponseEntity.created(new URI("/api/electronic-visas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /electronic-visas} : Updates an existing electronicVisa.
     *
     * @param electronicVisaDTO the electronicVisaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated electronicVisaDTO,
     * or with status {@code 400 (Bad Request)} if the electronicVisaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the electronicVisaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/electronic-visas")
    public ResponseEntity<ElectronicVisaDTO> updateElectronicVisa(@Valid @RequestBody ElectronicVisaDTO electronicVisaDTO) throws URISyntaxException {
        log.debug("REST request to update ElectronicVisa : {}", electronicVisaDTO);
        if (electronicVisaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ElectronicVisaDTO result = electronicVisaService.save(electronicVisaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, electronicVisaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /electronic-visas} : get all the electronicVisas.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of electronicVisas in body.
     */
    @GetMapping("/electronic-visas")
    public ResponseEntity<List<ElectronicVisaDTO>> getAllElectronicVisas(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("visaapplication-is-null".equals(filter)) {
            log.debug("REST request to get all ElectronicVisas where visaApplication is null");
            return new ResponseEntity<>(electronicVisaService.findAllWhereVisaApplicationIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of ElectronicVisas");
        Page<ElectronicVisaDTO> page = electronicVisaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /electronic-visas/:id} : get the "id" electronicVisa.
     *
     * @param id the id of the electronicVisaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the electronicVisaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/electronic-visas/{id}")
    public ResponseEntity<ElectronicVisaDTO> getElectronicVisa(@PathVariable Long id) {
        log.debug("REST request to get ElectronicVisa : {}", id);
        Optional<ElectronicVisaDTO> electronicVisaDTO = electronicVisaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(electronicVisaDTO);
    }

    /**
     * {@code DELETE  /electronic-visas/:id} : delete the "id" electronicVisa.
     *
     * @param id the id of the electronicVisaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/electronic-visas/{id}")
    public ResponseEntity<Void> deleteElectronicVisa(@PathVariable Long id) {
        log.debug("REST request to delete ElectronicVisa : {}", id);
        electronicVisaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/electronic-visas?query=:query} : search for the electronicVisa corresponding
     * to the query.
     *
     * @param query the query of the electronicVisa search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/electronic-visas")
    public ResponseEntity<List<ElectronicVisaDTO>> searchElectronicVisas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ElectronicVisas for query {}", query);
        Page<ElectronicVisaDTO> page = electronicVisaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
