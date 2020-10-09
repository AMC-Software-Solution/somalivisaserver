package so.gov.mfa.visa.repository;

import so.gov.mfa.visa.domain.ApplicationFee;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicationFee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationFeeRepository extends JpaRepository<ApplicationFee, Long> {
}
