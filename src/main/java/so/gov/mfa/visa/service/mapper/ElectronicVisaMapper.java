package so.gov.mfa.visa.service.mapper;


import so.gov.mfa.visa.domain.*;
import so.gov.mfa.visa.service.dto.ElectronicVisaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ElectronicVisa} and its DTO {@link ElectronicVisaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ElectronicVisaMapper extends EntityMapper<ElectronicVisaDTO, ElectronicVisa> {


    @Mapping(target = "visaApplication", ignore = true)
    ElectronicVisa toEntity(ElectronicVisaDTO electronicVisaDTO);

    default ElectronicVisa fromId(Long id) {
        if (id == null) {
            return null;
        }
        ElectronicVisa electronicVisa = new ElectronicVisa();
        electronicVisa.setId(id);
        return electronicVisa;
    }
}
