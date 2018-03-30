package za.co.ajk.incident.service.restio.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.ajk.incident.service.restio.RestTemplateService;


@Service
public class RestTemplateServiceImpl implements RestTemplateService {
    
    private static final Logger log = LoggerFactory.getLogger(RestTemplateServiceImpl.class);
    
    private static final String INVENTORY_MODULE = "http://inventorymodule/api/v1/equipment/{id}";
    private static final String SYSTEM_GATEWAY_MODULE = "http://systemgatewaymodule/api/users/{login}";
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public JsonNode getEquipmentFromInventory(Long equipmentId){
    
        Map<String, Object> params = new HashMap<>();
        params.put("id", equipmentId);
        
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(INVENTORY_MODULE,
                                                                        JsonNode.class,
                                                                        params);
        return response.getBody();
    }
    
    @Override
    public JsonNode getUserDetails(String loginName){
    
        Map<String, Object> params = new HashMap<>();
        params.put("login", loginName);
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(SYSTEM_GATEWAY_MODULE,
            JsonNode.class,
            params);
        return response.getBody();
    }
}
