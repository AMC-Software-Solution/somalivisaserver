package so.gov.mfa.visa.service;

import so.gov.mfa.visa.domain.PaymentTransaction;
import so.gov.mfa.visa.repository.PaymentTransactionRepository;
import so.gov.mfa.visa.repository.search.PaymentTransactionSearchRepository;
import so.gov.mfa.visa.service.dto.PaymentTransactionDTO;
import so.gov.mfa.visa.service.mapper.PaymentTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PaymentTransaction}.
 */
@Service
@Transactional
public class PaymentTransactionService {

    private final Logger log = LoggerFactory.getLogger(PaymentTransactionService.class);

    private final PaymentTransactionRepository paymentTransactionRepository;

    private final PaymentTransactionMapper paymentTransactionMapper;

    private final PaymentTransactionSearchRepository paymentTransactionSearchRepository;

    public PaymentTransactionService(PaymentTransactionRepository paymentTransactionRepository, PaymentTransactionMapper paymentTransactionMapper, PaymentTransactionSearchRepository paymentTransactionSearchRepository) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.paymentTransactionMapper = paymentTransactionMapper;
        this.paymentTransactionSearchRepository = paymentTransactionSearchRepository;
    }

    /**
     * Save a paymentTransaction.
     *
     * @param paymentTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentTransactionDTO save(PaymentTransactionDTO paymentTransactionDTO) {
        log.debug("Request to save PaymentTransaction : {}", paymentTransactionDTO);
        PaymentTransaction paymentTransaction = paymentTransactionMapper.toEntity(paymentTransactionDTO);
        paymentTransaction = paymentTransactionRepository.save(paymentTransaction);
        PaymentTransactionDTO result = paymentTransactionMapper.toDto(paymentTransaction);
        paymentTransactionSearchRepository.save(paymentTransaction);
        return result;
    }

    /**
     * Get all the paymentTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentTransactions");
        return paymentTransactionRepository.findAll(pageable)
            .map(paymentTransactionMapper::toDto);
    }


    /**
     * Get one paymentTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentTransactionDTO> findOne(Long id) {
        log.debug("Request to get PaymentTransaction : {}", id);
        return paymentTransactionRepository.findById(id)
            .map(paymentTransactionMapper::toDto);
    }

    /**
     * Delete the paymentTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentTransaction : {}", id);
        paymentTransactionRepository.deleteById(id);
        paymentTransactionSearchRepository.deleteById(id);
    }

    /**
     * Search for the paymentTransaction corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentTransactionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaymentTransactions for query {}", query);
        return paymentTransactionSearchRepository.search(queryStringQuery(query), pageable)
            .map(paymentTransactionMapper::toDto);
    }
}
