package za.co.ajk.incident.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import za.co.ajk.incident.enums.EventType;
import za.co.ajk.incident.enums.IncidentPriority;
import za.co.ajk.incident.enums.IncidentStatusType;
import za.co.ajk.incident.service.EnumInformation;
import za.co.ajk.incident.service.dto.EnumInformationDTO;


@Component
public class EnumInformationImpl implements EnumInformation {
    
    public List<EnumInformationDTO> retrieveEnumInformation(String enumType) {
        
        switch (enumType) {
            case "EventType":
                return retrieveEventTypeData();
            case "IncidentPriority":
                return retrieveIncidentPriorityData();
            case "IncidentStatusType":
                return retrieveIncidentStatusTypeData();
            default:
                return new ArrayList<>();
        }
        
    }
    
    private List<EnumInformationDTO> retrieveEventTypeData() {
        
        List<EnumInformationDTO> dtoList = new ArrayList<>();
        Arrays.stream(EventType.values()).forEach(eventType -> {
            EnumInformationDTO dto = new EnumInformationDTO(eventType.getEventTypeCode(),
                eventType.getEventDescription());
            dtoList.add(dto);
        });
        
        return dtoList;
    }
    
    private List<EnumInformationDTO> retrieveIncidentPriorityData() {
        
        List<EnumInformationDTO> dtoList = new ArrayList<>();
        Arrays.stream(IncidentPriority.values()).forEach(incidentPriority -> {
            EnumInformationDTO dto = new EnumInformationDTO(incidentPriority.getPriorityCode(),
                incidentPriority.getPriorityDescription());
            dtoList.add(dto);
        });
        
        return dtoList;
    }
    
    public List<EnumInformationDTO> retrieveIncidentStatusTypeData() {
        List<EnumInformationDTO> dtoList = new ArrayList<>();
        Arrays.stream(IncidentStatusType.values()).forEach(incidentStatusType -> {
            EnumInformationDTO dto = new EnumInformationDTO(incidentStatusType.getIncidentStatusCode(),
                incidentStatusType.getIncidentStatusDescription());
            dtoList.add(dto);
        });
        
        return dtoList;
    }
}
