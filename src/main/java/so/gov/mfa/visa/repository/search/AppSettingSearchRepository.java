package so.gov.mfa.visa.repository.search;

import so.gov.mfa.visa.domain.AppSetting;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AppSetting} entity.
 */
public interface AppSettingSearchRepository extends ElasticsearchRepository<AppSetting, Long> {
}
