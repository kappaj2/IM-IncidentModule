package za.co.ajk.incident.service.restio;

import com.fasterxml.jackson.databind.JsonNode;


public interface RestTemplateService {
    
    JsonNode getEquipmentFromInventory(Long equipmentId);

    JsonNode getUserDetails(String loginName);
}
