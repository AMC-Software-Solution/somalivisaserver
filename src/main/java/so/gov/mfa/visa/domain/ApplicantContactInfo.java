package so.gov.mfa.visa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ApplicantContactInfo.
 */
@Entity
@Table(name = "applicant_contact_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "applicantcontactinfo")
public class ApplicantContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "applicants_home_address", nullable = false)
    private String applicantsHomeAddress;

    @NotNull
    @Column(name = "telephone_number", nullable = false)
    private String telephoneNumber;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "employer")
    private String employer;

    @Column(name = "employers_address")
    private String employersAddress;

    @OneToOne(mappedBy = "applicantContactInfo")
    @JsonIgnore
    private Applicant applicant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicantsHomeAddress() {
        return applicantsHomeAddress;
    }

    public ApplicantContactInfo applicantsHomeAddress(String applicantsHomeAddress) {
        this.applicantsHomeAddress = applicantsHomeAddress;
        return this;
    }

    public void setApplicantsHomeAddress(String applicantsHomeAddress) {
        this.applicantsHomeAddress = applicantsHomeAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public ApplicantContactInfo telephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public ApplicantContactInfo email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployer() {
        return employer;
    }

    public ApplicantContactInfo employer(String employer) {
        this.employer = employer;
        return this;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmployersAddress() {
        return employersAddress;
    }

    public ApplicantContactInfo employersAddress(String employersAddress) {
        this.employersAddress = employersAddress;
        return this;
    }

    public void setEmployersAddress(String employersAddress) {
        this.employersAddress = employersAddress;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public ApplicantContactInfo applicant(Applicant applicant) {
        this.applicant = applicant;
        return this;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantContactInfo)) {
            return false;
        }
        return id != null && id.equals(((ApplicantContactInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantContactInfo{" +
            "id=" + getId() +
            ", applicantsHomeAddress='" + getApplicantsHomeAddress() + "'" +
            ", telephoneNumber='" + getTelephoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", employer='" + getEmployer() + "'" +
            ", employersAddress='" + getEmployersAddress() + "'" +
            "}";
    }
}
