package za.co.ajk.incident.service;

import java.util.List;

import za.co.ajk.incident.service.dto.EnumInformationDTO;


public interface EnumInformation {

    List<EnumInformationDTO> retrieveEnumInformation(String enumType);
}
