package so.gov.mfa.visa.repository;

import so.gov.mfa.visa.domain.ElectronicVisa;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ElectronicVisa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElectronicVisaRepository extends JpaRepository<ElectronicVisa, Long> {
}
