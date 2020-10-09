package so.gov.mfa.visa.repository;

import so.gov.mfa.visa.domain.AppSetting;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AppSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppSettingRepository extends JpaRepository<AppSetting, Long> {
}
