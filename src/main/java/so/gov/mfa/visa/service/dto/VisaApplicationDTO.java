package so.gov.mfa.visa.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import so.gov.mfa.visa.domain.enumeration.ApplicationStatus;
import so.gov.mfa.visa.domain.enumeration.TravelPurpose;
import so.gov.mfa.visa.domain.enumeration.VisaType;
import so.gov.mfa.visa.domain.enumeration.TravelMode;

/**
 * A DTO for the {@link so.gov.mfa.visa.domain.VisaApplication} entity.
 */
public class VisaApplicationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String applicationName;

    private String applicationCode;

    private ZonedDateTime applicationDate;

    private ApplicationStatus applicationStatus;

    @NotNull
    private TravelPurpose travelPurpose;

    @NotNull
    private VisaType visaType;

    @NotNull
    private TravelMode travelMode;

    @NotNull
    private String portOfEntry;

    @NotNull
    private String numberOfEntriesRequested;

    @NotNull
    private LocalDate intendedDateOfArrival;

    @NotNull
    private LocalDate intendedDateOfDeparture;

    @NotNull
    private LocalDate validUntil;

    private String travelPurposeOther;

    private String rejectReason;

    private ZonedDateTime approvedDate;


    private Long visaApplicationStayId;

    private String visaApplicationStayStayLocationFullAddress;

    private Long applicationFeeId;

    private String applicationFeeDescription;

    private Long electronicVisaId;

    private String electronicVisaVisaNumber;

    private Long approvedById;

    private String approvedByEmployeeFullName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public ZonedDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(ZonedDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public TravelPurpose getTravelPurpose() {
        return travelPurpose;
    }

    public void setTravelPurpose(TravelPurpose travelPurpose) {
        this.travelPurpose = travelPurpose;
    }

    public VisaType getVisaType() {
        return visaType;
    }

    public void setVisaType(VisaType visaType) {
        this.visaType = visaType;
    }

    public TravelMode getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
    }

    public String getPortOfEntry() {
        return portOfEntry;
    }

    public void setPortOfEntry(String portOfEntry) {
        this.portOfEntry = portOfEntry;
    }

    public String getNumberOfEntriesRequested() {
        return numberOfEntriesRequested;
    }

    public void setNumberOfEntriesRequested(String numberOfEntriesRequested) {
        this.numberOfEntriesRequested = numberOfEntriesRequested;
    }

    public LocalDate getIntendedDateOfArrival() {
        return intendedDateOfArrival;
    }

    public void setIntendedDateOfArrival(LocalDate intendedDateOfArrival) {
        this.intendedDateOfArrival = intendedDateOfArrival;
    }

    public LocalDate getIntendedDateOfDeparture() {
        return intendedDateOfDeparture;
    }

    public void setIntendedDateOfDeparture(LocalDate intendedDateOfDeparture) {
        this.intendedDateOfDeparture = intendedDateOfDeparture;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public String getTravelPurposeOther() {
        return travelPurposeOther;
    }

    public void setTravelPurposeOther(String travelPurposeOther) {
        this.travelPurposeOther = travelPurposeOther;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public ZonedDateTime getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(ZonedDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Long getVisaApplicationStayId() {
        return visaApplicationStayId;
    }

    public void setVisaApplicationStayId(Long visaApplicationStayId) {
        this.visaApplicationStayId = visaApplicationStayId;
    }

    public String getVisaApplicationStayStayLocationFullAddress() {
        return visaApplicationStayStayLocationFullAddress;
    }

    public void setVisaApplicationStayStayLocationFullAddress(String visaApplicationStayStayLocationFullAddress) {
        this.visaApplicationStayStayLocationFullAddress = visaApplicationStayStayLocationFullAddress;
    }

    public Long getApplicationFeeId() {
        return applicationFeeId;
    }

    public void setApplicationFeeId(Long applicationFeeId) {
        this.applicationFeeId = applicationFeeId;
    }

    public String getApplicationFeeDescription() {
        return applicationFeeDescription;
    }

    public void setApplicationFeeDescription(String applicationFeeDescription) {
        this.applicationFeeDescription = applicationFeeDescription;
    }

    public Long getElectronicVisaId() {
        return electronicVisaId;
    }

    public void setElectronicVisaId(Long electronicVisaId) {
        this.electronicVisaId = electronicVisaId;
    }

    public String getElectronicVisaVisaNumber() {
        return electronicVisaVisaNumber;
    }

    public void setElectronicVisaVisaNumber(String electronicVisaVisaNumber) {
        this.electronicVisaVisaNumber = electronicVisaVisaNumber;
    }

    public Long getApprovedById() {
        return approvedById;
    }

    public void setApprovedById(Long employeeId) {
        this.approvedById = employeeId;
    }

    public String getApprovedByEmployeeFullName() {
        return approvedByEmployeeFullName;
    }

    public void setApprovedByEmployeeFullName(String employeeEmployeeFullName) {
        this.approvedByEmployeeFullName = employeeEmployeeFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisaApplicationDTO)) {
            return false;
        }

        return id != null && id.equals(((VisaApplicationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisaApplicationDTO{" +
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
            ", visaApplicationStayId=" + getVisaApplicationStayId() +
            ", visaApplicationStayStayLocationFullAddress='" + getVisaApplicationStayStayLocationFullAddress() + "'" +
            ", applicationFeeId=" + getApplicationFeeId() +
            ", applicationFeeDescription='" + getApplicationFeeDescription() + "'" +
            ", electronicVisaId=" + getElectronicVisaId() +
            ", electronicVisaVisaNumber='" + getElectronicVisaVisaNumber() + "'" +
            ", approvedById=" + getApprovedById() +
            ", approvedByEmployeeFullName='" + getApprovedByEmployeeFullName() + "'" +
            "}";
    }
}
