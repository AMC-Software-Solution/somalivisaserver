package so.gov.mfa.visa.repository.search;

import so.gov.mfa.visa.domain.ApplicationFee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ApplicationFee} entity.
 */
public interface ApplicationFeeSearchRepository extends ElasticsearchRepository<ApplicationFee, Long> {
}
