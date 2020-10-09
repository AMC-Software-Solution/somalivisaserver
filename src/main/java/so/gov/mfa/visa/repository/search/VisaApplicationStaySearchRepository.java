package so.gov.mfa.visa.repository.search;

import so.gov.mfa.visa.domain.VisaApplicationStay;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link VisaApplicationStay} entity.
 */
public interface VisaApplicationStaySearchRepository extends ElasticsearchRepository<VisaApplicationStay, Long> {
}
