package za.co.ajk.incident.service.mapper;

import za.co.ajk.incident.domain.*;
import za.co.ajk.incident.service.dto.IncidentActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IncidentActivity and its DTO IncidentActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {IncidentMapper.class})
public interface IncidentActivityMapper extends EntityMapper<IncidentActivityDTO, IncidentActivity> {

    @Mapping(source = "incident.id", target = "incidentId")
    IncidentActivityDTO toDto(IncidentActivity incidentActivity);

    @Mapping(target = "equipment", ignore = true)
    @Mapping(source = "incidentId", target = "incident")
    IncidentActivity toEntity(IncidentActivityDTO incidentActivityDTO);

    default IncidentActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        IncidentActivity incidentActivity = new IncidentActivity();
        incidentActivity.setId(id);
        return incidentActivity;
    }
}
