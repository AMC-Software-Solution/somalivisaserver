package so.gov.mfa.visa.web.rest;

import so.gov.mfa.visa.SomalivisaserverApp;
import so.gov.mfa.visa.domain.PaymentTransaction;
import so.gov.mfa.visa.repository.PaymentTransactionRepository;
import so.gov.mfa.visa.repository.search.PaymentTransactionSearchRepository;
import so.gov.mfa.visa.service.PaymentTransactionService;
import so.gov.mfa.visa.service.dto.PaymentTransactionDTO;
import so.gov.mfa.visa.service.mapper.PaymentTransactionMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static so.gov.mfa.visa.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import so.gov.mfa.visa.domain.enumeration.PaymentType;
import so.gov.mfa.visa.domain.enumeration.PaymentStatus;
/**
 * Integration tests for the {@link PaymentTransactionResource} REST controller.
 */
@SpringBootTest(classes = SomalivisaserverApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentTransactionResourceIT {

    private static final Double DEFAULT_TRANSACTION_AMOUNT = 1D;
    private static final Double UPDATED_TRANSACTION_AMOUNT = 2D;

    private static final PaymentType DEFAULT_PAYMENT_TYPE = PaymentType.CASH;
    private static final PaymentType UPDATED_PAYMENT_TYPE = PaymentType.CARD;

    private static final String DEFAULT_PAYMENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_DESCRIPTION = "BBBBBBBBBB";

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PAID;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.UNPAID;

    private static final ZonedDateTime DEFAULT_TRANSACTION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TRANSACTION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PAYMENT_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_PROVIDER = "BBBBBBBBBB";

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;

    @Autowired
    private PaymentTransactionService paymentTransactionService;

    /**
     * This repository is mocked in the so.gov.mfa.visa.repository.search test package.
     *
     * @see so.gov.mfa.visa.repository.search.PaymentTransactionSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentTransactionSearchRepository mockPaymentTransactionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentTransactionMockMvc;

    private PaymentTransaction paymentTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentTransaction createEntity(EntityManager em) {
        PaymentTransaction paymentTransaction = new PaymentTransaction()
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .paymentDescription(DEFAULT_PAYMENT_DESCRIPTION)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .paymentProvider(DEFAULT_PAYMENT_PROVIDER);
        return paymentTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentTransaction createUpdatedEntity(EntityManager em) {
        PaymentTransaction paymentTransaction = new PaymentTransaction()
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .paymentDescription(UPDATED_PAYMENT_DESCRIPTION)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .paymentProvider(UPDATED_PAYMENT_PROVIDER);
        return paymentTransaction;
    }

    @BeforeEach
    public void initTest() {
        paymentTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentTransaction() throws Exception {
        int databaseSizeBeforeCreate = paymentTransactionRepository.findAll().size();
        // Create the PaymentTransaction
        PaymentTransactionDTO paymentTransactionDTO = paymentTransactionMapper.toDto(paymentTransaction);
        restPaymentTransactionMockMvc.perform(post("/api/payment-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentTransaction testPaymentTransaction = paymentTransactionList.get(paymentTransactionList.size() - 1);
        assertThat(testPaymentTransaction.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testPaymentTransaction.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testPaymentTransaction.getPaymentDescription()).isEqualTo(DEFAULT_PAYMENT_DESCRIPTION);
        assertThat(testPaymentTransaction.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPaymentTransaction.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testPaymentTransaction.getPaymentProvider()).isEqualTo(DEFAULT_PAYMENT_PROVIDER);

        // Validate the PaymentTransaction in Elasticsearch
        verify(mockPaymentTransactionSearchRepository, times(1)).save(testPaymentTransaction);
    }

    @Test
    @Transactional
    public void createPaymentTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentTransactionRepository.findAll().size();

        // Create the PaymentTransaction with an existing ID
        paymentTransaction.setId(1L);
        PaymentTransactionDTO paymentTransactionDTO = paymentTransactionMapper.toDto(paymentTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTransactionMockMvc.perform(post("/api/payment-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentTransaction in Elasticsearch
        verify(mockPaymentTransactionSearchRepository, times(0)).save(paymentTransaction);
    }


    @Test
    @Transactional
    public void checkTransactionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentTransactionRepository.findAll().size();
        // set the field null
        paymentTransaction.setTransactionAmount(null);

        // Create the PaymentTransaction, which fails.
        PaymentTransactionDTO paymentTransactionDTO = paymentTransactionMapper.toDto(paymentTransaction);


        restPaymentTransactionMockMvc.perform(post("/api/payment-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentTransactionRepository.findAll().size();
        // set the field null
        paymentTransaction.setPaymentType(null);

        // Create the PaymentTransaction, which fails.
        PaymentTransactionDTO paymentTransactionDTO = paymentTransactionMapper.toDto(paymentTransaction);


        restPaymentTransactionMockMvc.perform(post("/api/payment-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentTransactionRepository.findAll().size();
        // set the field null
        paymentTransaction.setPaymentDescription(null);

        // Create the PaymentTransaction, which fails.
        PaymentTransactionDTO paymentTransactionDTO = paymentTransactionMapper.toDto(paymentTransaction);


        restPaymentTransactionMockMvc.perform(post("/api/payment-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentTransactionRepository.findAll().size();
        // set the field null
        paymentTransaction.setPaymentStatus(null);

        // Create the PaymentTransaction, which fails.
        PaymentTransactionDTO paymentTransactionDTO = paymentTransactionMapper.toDto(paymentTransaction);


        restPaymentTransactionMockMvc.perform(post("/api/payment-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentTransactions() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransaction);

        // Get all the paymentTransactionList
        restPaymentTransactionMockMvc.perform(get("/api/payment-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentDescription").value(hasItem(DEFAULT_PAYMENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(sameInstant(DEFAULT_TRANSACTION_DATE))))
            .andExpect(jsonPath("$.[*].paymentProvider").value(hasItem(DEFAULT_PAYMENT_PROVIDER)));
    }
    
    @Test
    @Transactional
    public void getPaymentTransaction() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransaction);

        // Get the paymentTransaction
        restPaymentTransactionMockMvc.perform(get("/api/payment-transactions/{id}", paymentTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentTransaction.getId().intValue()))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.paymentDescription").value(DEFAULT_PAYMENT_DESCRIPTION))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.transactionDate").value(sameInstant(DEFAULT_TRANSACTION_DATE)))
            .andExpect(jsonPath("$.paymentProvider").value(DEFAULT_PAYMENT_PROVIDER));
    }
    @Test
    @Transactional
    public void getNonExistingPaymentTransaction() throws Exception {
        // Get the paymentTransaction
        restPaymentTransactionMockMvc.perform(get("/api/payment-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentTransaction() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransaction);

        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();

        // Update the paymentTransaction
        PaymentTransaction updatedPaymentTransaction = paymentTransactionRepository.findById(paymentTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentTransaction are not directly saved in db
        em.detach(updatedPaymentTransaction);
        updatedPaymentTransaction
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .paymentDescription(UPDATED_PAYMENT_DESCRIPTION)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .paymentProvider(UPDATED_PAYMENT_PROVIDER);
        PaymentTransactionDTO paymentTransactionDTO = paymentTransactionMapper.toDto(updatedPaymentTransaction);

        restPaymentTransactionMockMvc.perform(put("/api/payment-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeUpdate);
        PaymentTransaction testPaymentTransaction = paymentTransactionList.get(paymentTransactionList.size() - 1);
        assertThat(testPaymentTransaction.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testPaymentTransaction.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPaymentTransaction.getPaymentDescription()).isEqualTo(UPDATED_PAYMENT_DESCRIPTION);
        assertThat(testPaymentTransaction.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testPaymentTransaction.getPaymentProvider()).isEqualTo(UPDATED_PAYMENT_PROVIDER);

        // Validate the PaymentTransaction in Elasticsearch
        verify(mockPaymentTransactionSearchRepository, times(1)).save(testPaymentTransaction);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();

        // Create the PaymentTransaction
        PaymentTransactionDTO paymentTransactionDTO = paymentTransactionMapper.toDto(paymentTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTransactionMockMvc.perform(put("/api/payment-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentTransaction in Elasticsearch
        verify(mockPaymentTransactionSearchRepository, times(0)).save(paymentTransaction);
    }

    @Test
    @Transactional
    public void deletePaymentTransaction() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransaction);

        int databaseSizeBeforeDelete = paymentTransactionRepository.findAll().size();

        // Delete the paymentTransaction
        restPaymentTransactionMockMvc.perform(delete("/api/payment-transactions/{id}", paymentTransaction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentTransaction in Elasticsearch
        verify(mockPaymentTransactionSearchRepository, times(1)).deleteById(paymentTransaction.getId());
    }

    @Test
    @Transactional
    public void searchPaymentTransaction() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransaction);
        when(mockPaymentTransactionSearchRepository.search(queryStringQuery("id:" + paymentTransaction.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paymentTransaction), PageRequest.of(0, 1), 1));

        // Search the paymentTransaction
        restPaymentTransactionMockMvc.perform(get("/api/_search/payment-transactions?query=id:" + paymentTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentDescription").value(hasItem(DEFAULT_PAYMENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(sameInstant(DEFAULT_TRANSACTION_DATE))))
            .andExpect(jsonPath("$.[*].paymentProvider").value(hasItem(DEFAULT_PAYMENT_PROVIDER)));
    }
}
