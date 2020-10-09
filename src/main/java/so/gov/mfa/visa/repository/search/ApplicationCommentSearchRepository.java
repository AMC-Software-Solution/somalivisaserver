package so.gov.mfa.visa.repository.search;

import so.gov.mfa.visa.domain.ApplicationComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ApplicationComment} entity.
 */
public interface ApplicationCommentSearchRepository extends ElasticsearchRepository<ApplicationComment, Long> {
}
