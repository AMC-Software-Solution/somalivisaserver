package so.gov.mfa.visa.repository.search;

import so.gov.mfa.visa.domain.ApplicantTravelDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ApplicantTravelDocument} entity.
 */
public interface ApplicantTravelDocumentSearchRepository extends ElasticsearchRepository<ApplicantTravelDocument, Long> {
}
