package so.gov.mfa.visa.repository;

import so.gov.mfa.visa.domain.Employee;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select employee from Employee employee where employee.user.login = ?#{principal.username}")
    List<Employee> findByUserIsCurrentUser();
}
