package so.gov.mfa.visa.repository;

import so.gov.mfa.visa.domain.VisaApplicationStay;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VisaApplicationStay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisaApplicationStayRepository extends JpaRepository<VisaApplicationStay, Long> {
}
