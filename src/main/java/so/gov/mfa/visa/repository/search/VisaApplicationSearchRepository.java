package so.gov.mfa.visa.repository.search;

import so.gov.mfa.visa.domain.VisaApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link VisaApplication} entity.
 */
public interface VisaApplicationSearchRepository extends ElasticsearchRepository<VisaApplication, Long> {
}
