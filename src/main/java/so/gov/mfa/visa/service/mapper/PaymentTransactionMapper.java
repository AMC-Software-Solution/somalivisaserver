package so.gov.mfa.visa.service.mapper;


import so.gov.mfa.visa.domain.*;
import so.gov.mfa.visa.service.dto.PaymentTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentTransaction} and its DTO {@link PaymentTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {VisaApplicationMapper.class})
public interface PaymentTransactionMapper extends EntityMapper<PaymentTransactionDTO, PaymentTransaction> {

    @Mapping(source = "visaApplication.id", target = "visaApplicationId")
    @Mapping(source = "visaApplication.applicationName", target = "visaApplicationApplicationName")
    PaymentTransactionDTO toDto(PaymentTransaction paymentTransaction);

    @Mapping(source = "visaApplicationId", target = "visaApplication")
    PaymentTransaction toEntity(PaymentTransactionDTO paymentTransactionDTO);

    default PaymentTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setId(id);
        return paymentTransaction;
    }
}
