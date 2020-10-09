package so.gov.mfa.visa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link so.gov.mfa.visa.domain.ApplicantContactInfo} entity.
 */
public class ApplicantContactInfoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String applicantsHomeAddress;

    @NotNull
    private String telephoneNumber;

    @NotNull
    private String email;

    private String employer;

    private String employersAddress;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicantsHomeAddress() {
        return applicantsHomeAddress;
    }

    public void setApplicantsHomeAddress(String applicantsHomeAddress) {
        this.applicantsHomeAddress = applicantsHomeAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmployersAddress() {
        return employersAddress;
    }

    public void setEmployersAddress(String employersAddress) {
        this.employersAddress = employersAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantContactInfoDTO)) {
            return false;
        }

        return id != null && id.equals(((ApplicantContactInfoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantContactInfoDTO{" +
            "id=" + getId() +
            ", applicantsHomeAddress='" + getApplicantsHomeAddress() + "'" +
            ", telephoneNumber='" + getTelephoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", employer='" + getEmployer() + "'" +
            ", employersAddress='" + getEmployersAddress() + "'" +
            "}";
    }
}
