package so.gov.mfa.visa.repository.search;

import so.gov.mfa.visa.domain.PaymentTransaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link PaymentTransaction} entity.
 */
public interface PaymentTransactionSearchRepository extends ElasticsearchRepository<PaymentTransaction, Long> {
}
