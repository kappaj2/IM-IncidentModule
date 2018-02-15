package za.co.ajk.incident.service.mapper;

import za.co.ajk.incident.domain.*;
import za.co.ajk.incident.service.dto.IncidentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Incident and its DTO IncidentDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface IncidentMapper extends EntityMapper<IncidentDTO, Incident> {

    @Mapping(source = "company.id", target = "companyId")
    IncidentDTO toDto(Incident incident);

    @Mapping(target = "incidentActivities", ignore = true)
    @Mapping(source = "companyId", target = "company")
    Incident toEntity(IncidentDTO incidentDTO);

    default Incident fromId(Long id) {
        if (id == null) {
            return null;
        }
        Incident incident = new Incident();
        incident.setId(id);
        return incident;
    }
}
