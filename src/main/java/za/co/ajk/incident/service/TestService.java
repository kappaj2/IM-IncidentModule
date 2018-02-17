package za.co.ajk.incident.service;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.ajk.incident.security.SecurityUtils;


@Service
public class TestService {
    
    private RestTemplate restTemplate;
    
    public TestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public void getUserIdForCurrentUSer() {
        
        Optional<String> userNameOpt = SecurityUtils.getCurrentUserLogin();
        
        if (userNameOpt.isPresent()) {
            
            String userName = userNameOpt.get();
            String accountResourceUrl
                = "http://SystemGatewayModule/api/users/" + userName;
            
            Optional<String> jwtToken1Optional = SecurityUtils.getCurrentUserJWT();
            
            // anonymousUser does not have a valid jwt token
            if (jwtToken1Optional.isPresent() && jwtToken1Optional.get().length() > 1) {
                
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", "Bearer " + jwtToken1Optional.get());
                
                HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
                
                ResponseEntity<JsonNode> response = restTemplate
                    .exchange(accountResourceUrl, HttpMethod.GET, entity, JsonNode.class);
                
                JsonNode userNode = response.getBody();
                Integer userId = userNode.get("id").asInt();
                
                System.out
                    .println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());
                
            }
            //            String accountResourceUrl
            //                = "http://SystemGatewayModule/api/account";
            
            //            ResponseEntity<Object> responseEntity =
            //                this.restTemplate.exchange("http://systemgatewaymodule/api/account",
            //                    HttpMethod.GET,
            //                    JsonNode.class
            //                );
            
            //            List<Object> regions = responseEntity
            //                .getBody()
            //                .stream()
            //                .collect(Collectors.toList());
        }
        
    }
    
    
    /*
        RestTemplate returning a list of DTO's
        
            ParameterizedTypeReference<List<Object>> ptr =
                new ParameterizedTypeReference<List<Object>>() {
                };
    
            String googleTestUrl = "http://SystemGatewayModule/api/test/send-test";
            
            ResponseEntity<List<Object>> responseEntity =
                this.restTemplate.exchange(googleTestUrl,
                    HttpMethod.GET,
                    null,
                    ptr
                );
            
            List<Object> regions = responseEntity
                .getBody()
                .stream()
                .collect(Collectors.toList());
                
     */
}
