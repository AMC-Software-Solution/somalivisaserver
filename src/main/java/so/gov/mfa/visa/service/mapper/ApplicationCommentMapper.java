package so.gov.mfa.visa.service.mapper;


import so.gov.mfa.visa.domain.*;
import so.gov.mfa.visa.service.dto.ApplicationCommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationComment} and its DTO {@link ApplicationCommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {VisaApplicationMapper.class, UserMapper.class})
public interface ApplicationCommentMapper extends EntityMapper<ApplicationCommentDTO, ApplicationComment> {

    @Mapping(source = "visaApplication.id", target = "visaApplicationId")
    @Mapping(source = "visaApplication.applicationName", target = "visaApplicationApplicationName")
    @Mapping(source = "commentedBy.id", target = "commentedById")
    @Mapping(source = "commentedBy.login", target = "commentedByLogin")
    ApplicationCommentDTO toDto(ApplicationComment applicationComment);

    @Mapping(source = "visaApplicationId", target = "visaApplication")
    @Mapping(source = "commentedById", target = "commentedBy")
    ApplicationComment toEntity(ApplicationCommentDTO applicationCommentDTO);

    default ApplicationComment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationComment applicationComment = new ApplicationComment();
        applicationComment.setId(id);
        return applicationComment;
    }
}
