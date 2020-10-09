package so.gov.mfa.visa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

import so.gov.mfa.visa.domain.enumeration.PaymentType;

import so.gov.mfa.visa.domain.enumeration.PaymentStatus;

/**
 * A PaymentTransaction.
 */
@Entity
@Table(name = "payment_transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "paymenttransaction")
public class PaymentTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "transaction_amount", nullable = false)
    private Double transactionAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @NotNull
    @Column(name = "payment_description", nullable = false)
    private String paymentDescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "transaction_date")
    private ZonedDateTime transactionDate;

    @Column(name = "payment_provider")
    private String paymentProvider;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentTransactions", allowSetters = true)
    private VisaApplication visaApplication;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public PaymentTransaction transactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public PaymentTransaction paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public PaymentTransaction paymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
        return this;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public PaymentTransaction paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ZonedDateTime getTransactionDate() {
        return transactionDate;
    }

    public PaymentTransaction transactionDate(ZonedDateTime transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(ZonedDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public PaymentTransaction paymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
        return this;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public VisaApplication getVisaApplication() {
        return visaApplication;
    }

    public PaymentTransaction visaApplication(VisaApplication visaApplication) {
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
        if (!(o instanceof PaymentTransaction)) {
            return false;
        }
        return id != null && id.equals(((PaymentTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentTransaction{" +
            "id=" + getId() +
            ", transactionAmount=" + getTransactionAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", paymentDescription='" + getPaymentDescription() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", paymentProvider='" + getPaymentProvider() + "'" +
            "}";
    }
}
