package so.gov.mfa.visa.repository.search;

import so.gov.mfa.visa.domain.Applicant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Applicant} entity.
 */
public interface ApplicantSearchRepository extends ElasticsearchRepository<Applicant, Long> {
}
