package so.gov.mfa.visa.service.mapper;


import so.gov.mfa.visa.domain.*;
import so.gov.mfa.visa.service.dto.ApplicantContactInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantContactInfo} and its DTO {@link ApplicantContactInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicantContactInfoMapper extends EntityMapper<ApplicantContactInfoDTO, ApplicantContactInfo> {


    @Mapping(target = "applicant", ignore = true)
    ApplicantContactInfo toEntity(ApplicantContactInfoDTO applicantContactInfoDTO);

    default ApplicantContactInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicantContactInfo applicantContactInfo = new ApplicantContactInfo();
        applicantContactInfo.setId(id);
        return applicantContactInfo;
    }
}
