package so.gov.mfa.visa.repository.search;

import so.gov.mfa.visa.domain.ApplicantContactInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ApplicantContactInfo} entity.
 */
public interface ApplicantContactInfoSearchRepository extends ElasticsearchRepository<ApplicantContactInfo, Long> {
}
