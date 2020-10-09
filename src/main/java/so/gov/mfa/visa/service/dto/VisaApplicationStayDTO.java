package so.gov.mfa.visa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link so.gov.mfa.visa.domain.VisaApplicationStay} entity.
 */
public class VisaApplicationStayDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer durationOfProposedStayInDays;

    private String nameOfHostingPersonOrcompany;

    private String stayingLocationName;

    private String stayLocationFullAddress;

    private String stayLocationTelephoneNumber;

    private String stayLocationEmail;

    private String whoCoversCostOfApplicantsStay;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDurationOfProposedStayInDays() {
        return durationOfProposedStayInDays;
    }

    public void setDurationOfProposedStayInDays(Integer durationOfProposedStayInDays) {
        this.durationOfProposedStayInDays = durationOfProposedStayInDays;
    }

    public String getNameOfHostingPersonOrcompany() {
        return nameOfHostingPersonOrcompany;
    }

    public void setNameOfHostingPersonOrcompany(String nameOfHostingPersonOrcompany) {
        this.nameOfHostingPersonOrcompany = nameOfHostingPersonOrcompany;
    }

    public String getStayingLocationName() {
        return stayingLocationName;
    }

    public void setStayingLocationName(String stayingLocationName) {
        this.stayingLocationName = stayingLocationName;
    }

    public String getStayLocationFullAddress() {
        return stayLocationFullAddress;
    }

    public void setStayLocationFullAddress(String stayLocationFullAddress) {
        this.stayLocationFullAddress = stayLocationFullAddress;
    }

    public String getStayLocationTelephoneNumber() {
        return stayLocationTelephoneNumber;
    }

    public void setStayLocationTelephoneNumber(String stayLocationTelephoneNumber) {
        this.stayLocationTelephoneNumber = stayLocationTelephoneNumber;
    }

    public String getStayLocationEmail() {
        return stayLocationEmail;
    }

    public void setStayLocationEmail(String stayLocationEmail) {
        this.stayLocationEmail = stayLocationEmail;
    }

    public String getWhoCoversCostOfApplicantsStay() {
        return whoCoversCostOfApplicantsStay;
    }

    public void setWhoCoversCostOfApplicantsStay(String whoCoversCostOfApplicantsStay) {
        this.whoCoversCostOfApplicantsStay = whoCoversCostOfApplicantsStay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisaApplicationStayDTO)) {
            return false;
        }

        return id != null && id.equals(((VisaApplicationStayDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisaApplicationStayDTO{" +
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
