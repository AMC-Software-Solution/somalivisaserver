package so.gov.mfa.visa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A VisaApplicationStay.
 */
@Entity
@Table(name = "visa_application_stay")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "visaapplicationstay")
public class VisaApplicationStay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "duration_of_proposed_stay_in_days", nullable = false)
    private Integer durationOfProposedStayInDays;

    @Column(name = "name_of_hosting_person_orcompany")
    private String nameOfHostingPersonOrcompany;

    @Column(name = "staying_location_name")
    private String stayingLocationName;

    @Column(name = "stay_location_full_address")
    private String stayLocationFullAddress;

    @Column(name = "stay_location_telephone_number")
    private String stayLocationTelephoneNumber;

    @Column(name = "stay_location_email")
    private String stayLocationEmail;

    @Column(name = "who_covers_cost_of_applicants_stay")
    private String whoCoversCostOfApplicantsStay;

    @OneToOne(mappedBy = "visaApplicationStay")
    @JsonIgnore
    private VisaApplication visaApplication;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDurationOfProposedStayInDays() {
        return durationOfProposedStayInDays;
    }

    public VisaApplicationStay durationOfProposedStayInDays(Integer durationOfProposedStayInDays) {
        this.durationOfProposedStayInDays = durationOfProposedStayInDays;
        return this;
    }

    public void setDurationOfProposedStayInDays(Integer durationOfProposedStayInDays) {
        this.durationOfProposedStayInDays = durationOfProposedStayInDays;
    }

    public String getNameOfHostingPersonOrcompany() {
        return nameOfHostingPersonOrcompany;
    }

    public VisaApplicationStay nameOfHostingPersonOrcompany(String nameOfHostingPersonOrcompany) {
        this.nameOfHostingPersonOrcompany = nameOfHostingPersonOrcompany;
        return this;
    }

    public void setNameOfHostingPersonOrcompany(String nameOfHostingPersonOrcompany) {
        this.nameOfHostingPersonOrcompany = nameOfHostingPersonOrcompany;
    }

    public String getStayingLocationName() {
        return stayingLocationName;
    }

    public VisaApplicationStay stayingLocationName(String stayingLocationName) {
        this.stayingLocationName = stayingLocationName;
        return this;
    }

    public void setStayingLocationName(String stayingLocationName) {
        this.stayingLocationName = stayingLocationName;
    }

    public String getStayLocationFullAddress() {
        return stayLocationFullAddress;
    }

    public VisaApplicationStay stayLocationFullAddress(String stayLocationFullAddress) {
        this.stayLocationFullAddress = stayLocationFullAddress;
        return this;
    }

    public void setStayLocationFullAddress(String stayLocationFullAddress) {
        this.stayLocationFullAddress = stayLocationFullAddress;
    }

    public String getStayLocationTelephoneNumber() {
        return stayLocationTelephoneNumber;
    }

    public VisaApplicationStay stayLocationTelephoneNumber(String stayLocationTelephoneNumber) {
        this.stayLocationTelephoneNumber = stayLocationTelephoneNumber;
        return this;
    }

    public void setStayLocationTelephoneNumber(String stayLocationTelephoneNumber) {
        this.stayLocationTelephoneNumber = stayLocationTelephoneNumber;
    }

    public String getStayLocationEmail() {
        return stayLocationEmail;
    }

    public VisaApplicationStay stayLocationEmail(String stayLocationEmail) {
        this.stayLocationEmail = stayLocationEmail;
        return this;
    }

    public void setStayLocationEmail(String stayLocationEmail) {
        this.stayLocationEmail = stayLocationEmail;
    }

    public String getWhoCoversCostOfApplicantsStay() {
        return whoCoversCostOfApplicantsStay;
    }

    public VisaApplicationStay whoCoversCostOfApplicantsStay(String whoCoversCostOfApplicantsStay) {
        this.whoCoversCostOfApplicantsStay = whoCoversCostOfApplicantsStay;
        return this;
    }

    public void setWhoCoversCostOfApplicantsStay(String whoCoversCostOfApplicantsStay) {
        this.whoCoversCostOfApplicantsStay = whoCoversCostOfApplicantsStay;
    }

    public VisaApplication getVisaApplication() {
        return visaApplication;
    }

    public VisaApplicationStay visaApplication(VisaApplication visaApplication) {
        this.visaApplication = visaApplication;
        return this;
    }

    public void setVisaApplication(VisaApplication visaApplication) {
        this.visaApplication = visaApplication;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisaApplicationStay)) {
            return false;
        }
        return id != null && id.equals(((VisaApplicationStay) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisaApplicationStay{" +
            "id=" + getId() +
            ", durationOfProposedStayInDays=" + getDurationOfProposedStayInDays() +
            ", nameOfHostingPersonOrcompany='" + getNameOfHostingPersonOrcompany() + "'" +
            ", stayingLocationName='" + getStayingLocationName() + "'" +
            ", stayLocationFullAddress='" + getStayLocationFullAddress() + "'" +
            ", stayLocationTelephoneNumber='" + getStayLocationTelephoneNumber() + "'" +
            ", stayLocationEmail='" + getStayLocationEmail() + "'" +
            ", whoCoversCostOfApplicantsStay='" + getWhoCoversCostOfApplicantsStay() + "'" +
            "}";
    }
}
