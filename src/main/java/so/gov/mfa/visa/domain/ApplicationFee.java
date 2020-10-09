package so.gov.mfa.visa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ApplicationFee.
 */
@Entity
@Table(name = "application_fee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "applicationfee")
public class ApplicationFee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "currency", nullable = false)
    private String currency;

    @NotNull
    @Column(name = "current_iso_code", nullable = false)
    private String currentIsoCode;

    @OneToOne(mappedBy = "applicationFee")
    @JsonIgnore
    private VisaApplication visaApplication;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public ApplicationFee amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public ApplicationFee description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public ApplicationFee currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrentIsoCode() {
        return currentIsoCode;
    }

    public ApplicationFee currentIsoCode(String currentIsoCode) {
        this.currentIsoCode = currentIsoCode;
        return this;
    }

    public void setCurrentIsoCode(String currentIsoCode) {
        this.currentIsoCode = currentIsoCode;
    }

    public VisaApplication getVisaApplication() {
        return visaApplication;
    }

    public ApplicationFee visaApplication(VisaApplication visaApplication) {
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
        if (!(o instanceof ApplicationFee)) {
            return false;
        }
        return id != null && id.equals(((ApplicationFee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationFee{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", currentIsoCode='" + getCurrentIsoCode() + "'" +
            "}";
    }
}
