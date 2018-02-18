package za.co.ajk.incident.service.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class CreateNewIncidentDTO implements Serializable {
    
    @NotNull
    private String incidentPriorityCode;
    
    @NotNull
    private String incidentTypeCode;
    
    @NotNull
    private String incidentHeader;
    
    @NotNull
    private String incidentDescription;
    
    @NotNull
    private String incidentStatusCode;
    
    @NotNull
    private Long companyId;
    
    @NotNull
    private Integer operatorId;
}
