package so.gov.mfa.visa.service.mapper;


import so.gov.mfa.visa.domain.*;
import so.gov.mfa.visa.service.dto.ApplicantTravelDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicantTravelDocument} and its DTO {@link ApplicantTravelDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicantMapper.class})
public interface ApplicantTravelDocumentMapper extends EntityMapper<ApplicantTravelDocumentDTO, ApplicantTravelDocument> {

    @Mapping(source = "applicant.id", target = "applicantId")
    @Mapping(source = "applicant.fullName", target = "applicantFullName")
    ApplicantTravelDocumentDTO toDto(ApplicantTravelDocument applicantTravelDocument);

    @Mapping(source = "applicantId", target = "applicant")
    ApplicantTravelDocument toEntity(ApplicantTravelDocumentDTO applicantTravelDocumentDTO);

    default ApplicantTravelDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicantTravelDocument applicantTravelDocument = new ApplicantTravelDocument();
        applicantTravelDocument.setId(id);
        return applicantTravelDocument;
    }
}
