package so.gov.mfa.visa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import so.gov.mfa.visa.domain.enumeration.ApplicationStatus;

import so.gov.mfa.visa.domain.enumeration.TravelPurpose;

import so.gov.mfa.visa.domain.enumeration.VisaType;

import so.gov.mfa.visa.domain.enumeration.TravelMode;

/**
 * A VisaApplication.
 */
@Entity
@Table(name = "visa_application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "visaapplication")
public class VisaApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "application_name", nullable = false)
    private String applicationName;

    @Column(name = "application_code")
    private String applicationCode;

    @Column(name = "application_date")
    private ZonedDateTime applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status")
    private ApplicationStatus applicationStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "travel_purpose", nullable = false)
    private TravelPurpose travelPurpose;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visa_type", nullable = false)
    private VisaType visaType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "travel_mode", nullable = false)
    private TravelMode travelMode;

    @NotNull
    @Column(name = "port_of_entry", nullable = false)
    private String portOfEntry;

    @NotNull
    @Column(name = "number_of_entries_requested", nullable = false)
    private String numberOfEntriesRequested;

    @NotNull
    @Column(name = "intended_date_of_arrival", nullable = false)
    private LocalDate intendedDateOfArrival;

    @NotNull
    @Column(name = "intended_date_of_departure", nullable = false)
    private LocalDate intendedDateOfDeparture;

    @NotNull
    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;

    @Column(name = "travel_purpose_other")
    private String travelPurposeOther;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Column(name = "approved_date")
    private ZonedDateTime approvedDate;

    @OneToOne
    @JoinColumn(unique = true)
    private VisaApplicationStay visaApplicationStay;

    @OneToOne
    @JoinColumn(unique = true)
    private ApplicationFee applicationFee;

    @OneToOne
    @JoinColumn(unique = true)
    private ElectronicVisa electronicVisa;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee approvedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public VisaApplication applicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public VisaApplication applicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
        return this;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public ZonedDateTime getApplicationDate() {
        return applicationDate;
    }

    public VisaApplication applicationDate(ZonedDateTime applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }

    public void setApplicationDate(ZonedDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public VisaApplication applicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
        return this;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public TravelPurpose getTravelPurpose() {
        return travelPurpose;
    }

    public VisaApplication travelPurpose(TravelPurpose travelPurpose) {
        this.travelPurpose = travelPurpose;
        return this;
    }

    public void setTravelPurpose(TravelPurpose travelPurpose) {
        this.travelPurpose = travelPurpose;
    }

    public VisaType getVisaType() {
        return visaType;
    }

    public VisaApplication visaType(VisaType visaType) {
        this.visaType = visaType;
        return this;
    }

    public void setVisaType(VisaType visaType) {
        this.visaType = visaType;
    }

    public TravelMode getTravelMode() {
        return travelMode;
    }

    public VisaApplication travelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
        return this;
    }

    public void setTravelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
    }

    public String getPortOfEntry() {
        return portOfEntry;
    }

    public VisaApplication portOfEntry(String portOfEntry) {
        this.portOfEntry = portOfEntry;
        return this;
    }

    public void setPortOfEntry(String portOfEntry) {
        this.portOfEntry = portOfEntry;
    }

    public String getNumberOfEntriesRequested() {
        return numberOfEntriesRequested;
    }

    public VisaApplication numberOfEntriesRequested(String numberOfEntriesRequested) {
        this.numberOfEntriesRequested = numberOfEntriesRequested;
        return this;
    }

    public void setNumberOfEntriesRequested(String numberOfEntriesRequested) {
        this.numberOfEntriesRequested = numberOfEntriesRequested;
    }

    public LocalDate getIntendedDateOfArrival() {
        return intendedDateOfArrival;
    }

    public VisaApplication intendedDateOfArrival(LocalDate intendedDateOfArrival) {
        this.intendedDateOfArrival = intendedDateOfArrival;
        return this;
    }

    public void setIntendedDateOfArrival(LocalDate intendedDateOfArrival) {
        this.intendedDateOfArrival = intendedDateOfArrival;
    }

    public LocalDate getIntendedDateOfDeparture() {
        return intendedDateOfDeparture;
    }

    public VisaApplication intendedDateOfDeparture(LocalDate intendedDateOfDeparture) {
        this.intendedDateOfDeparture = intendedDateOfDeparture;
        return this;
    }

    public void setIntendedDateOfDeparture(LocalDate intendedDateOfDeparture) {
        this.intendedDateOfDeparture = intendedDateOfDeparture;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public VisaApplication validUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public String getTravelPurposeOther() {
        return travelPurposeOther;
    }

    public VisaApplication travelPurposeOther(String travelPurposeOther) {
        this.travelPurposeOther = travelPurposeOther;
        return this;
    }

    public void setTravelPurposeOther(String travelPurposeOther) {
        this.travelPurposeOther = travelPurposeOther;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public VisaApplication rejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
        return this;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public ZonedDateTime getApprovedDate() {
        return approvedDate;
    }

    public VisaApplication approvedDate(ZonedDateTime approvedDate) {
        this.approvedDate = approvedDate;
        return this;
    }

    public void setApprovedDate(ZonedDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public VisaApplicationStay getVisaApplicationStay() {
        return visaApplicationStay;
    }

    public VisaApplication visaApplicationStay(VisaApplicationStay visaApplicationStay) {
        this.visaApplicationStay = visaApplicationStay;
        return this;
    }

    public void setVisaApplicationStay(VisaApplicationStay visaApplicationStay) {
        this.visaApplicationStay = visaApplicationStay;
    }

    public ApplicationFee getApplicationFee() {
        return applicationFee;
    }

    public VisaApplication applicationFee(ApplicationFee applicationFee) {
        this.applicationFee = applicationFee;
        return this;
    }

    public void setApplicationFee(ApplicationFee applicationFee) {
        this.applicationFee = applicationFee;
    }

    public ElectronicVisa getElectronicVisa() {
        return electronicVisa;
    }

    public VisaApplication electronicVisa(ElectronicVisa electronicVisa) {
        this.electronicVisa = electronicVisa;
        return this;
    }

    public void setElectronicVisa(ElectronicVisa electronicVisa) {
        this.electronicVisa = electronicVisa;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public VisaApplication approvedBy(Employee employee) {
        this.approvedBy = employee;
        return this;
    }

    public void setApprovedBy(Employee employee) {
        this.approvedBy = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisaApplication)) {
            return false;
        }
        return id != null && id.equals(((VisaApplication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisaApplication{" +
            "id=" + getId() +
            ", applicationName='" + getApplicationName() + "'" +
            ", applicationCode='" + getApplicationCode() + "'" +
            ", applicationDate='" + getApplicationDate() + "'" +
            ", applicationStatus='" + getApplicationStatus() + "'" +
            ", travelPurpose='" + getTravelPurpose() + "'" +
            ", visaType='" + getVisaType() + "'" +
            ", travelMode='" + getTravelMode() + "'" +
            ", portOfEntry='" + getPortOfEntry() + "'" +
            ", numberOfEntriesRequested='" + getNumberOfEntriesRequested() + "'" +
            ", intendedDateOfArrival='" + getIntendedDateOfArrival() + "'" +
            ", intendedDateOfDeparture='" + getIntendedDateOfDeparture() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            ", travelPurposeOther='" + getTravelPurposeOther() + "'" +
            ", rejectReason='" + getRejectReason() + "'" +
            ", approvedDate='" + getApprovedDate() + "'" +
            "}";
    }
}
