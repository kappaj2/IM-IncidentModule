package za.co.ajk.incident.service.mapper;

import za.co.ajk.incident.domain.*;
import za.co.ajk.incident.service.dto.EquipmentActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EquipmentActivity and its DTO EquipmentActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {IncidentActivityMapper.class, EquipmentMapper.class})
public interface EquipmentActivityMapper extends EntityMapper<EquipmentActivityDTO, EquipmentActivity> {

    @Mapping(source = "incidentActivity.id", target = "incidentActivityId")
    @Mapping(source = "equipment.id", target = "equipmentId")
    EquipmentActivityDTO toDto(EquipmentActivity equipmentActivity);

    @Mapping(source = "incidentActivityId", target = "incidentActivity")
    @Mapping(source = "equipmentId", target = "equipment")
    EquipmentActivity toEntity(EquipmentActivityDTO equipmentActivityDTO);

    default EquipmentActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        EquipmentActivity equipmentActivity = new EquipmentActivity();
        equipmentActivity.setId(id);
        return equipmentActivity;
    }
}
