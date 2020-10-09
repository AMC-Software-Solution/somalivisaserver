package so.gov.mfa.visa.repository.search;

import so.gov.mfa.visa.domain.SystemSetting;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link SystemSetting} entity.
 */
public interface SystemSettingSearchRepository extends ElasticsearchRepository<SystemSetting, Long> {
}
