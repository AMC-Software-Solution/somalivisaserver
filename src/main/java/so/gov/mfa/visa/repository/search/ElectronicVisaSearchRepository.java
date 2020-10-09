package so.gov.mfa.visa.repository.search;

import so.gov.mfa.visa.domain.ElectronicVisa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ElectronicVisa} entity.
 */
public interface ElectronicVisaSearchRepository extends ElasticsearchRepository<ElectronicVisa, Long> {
}
