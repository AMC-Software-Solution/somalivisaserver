package so.gov.mfa.visa.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import so.gov.mfa.visa.domain.enumeration.PaymentType;
import so.gov.mfa.visa.domain.enumeration.PaymentStatus;

/**
 * A DTO for the {@link so.gov.mfa.visa.domain.PaymentTransaction} entity.
 */
public class PaymentTransactionDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double transactionAmount;

    @NotNull
    private PaymentType paymentType;

    @NotNull
    private String paymentDescription;

    @NotNull
    private PaymentStatus paymentStatus;

    private ZonedDateTime transactionDate;

    private String paymentProvider;


    private Long visaApplicationId;

    private String visaApplicationApplicationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ZonedDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(ZonedDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public Long getVisaApplicationId() {
        return visaApplicationId;
    }

    public void setVisaApplicationId(Long visaApplicationId) {
        this.visaApplicationId = visaApplicationId;
    }

    public String getVisaApplicationApplicationName() {
        return visaApplicationApplicationName;
    }

    public void setVisaApplicationApplicationName(String visaApplicationApplicationName) {
        this.visaApplicationApplicationName = visaApplicationApplicationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentTransactionDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentTransactionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentTransactionDTO{" +
            "id=" + getId() +
            ", transactionAmount=" + getTransactionAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", paymentDescription='" + getPaymentDescription() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", paymentProvider='" + getPaymentProvider() + "'" +
            ", visaApplicationId=" + getVisaApplicationId() +
            ", visaApplicationApplicationName='" + getVisaApplicationApplicationName() + "'" +
            "}";
    }
}
