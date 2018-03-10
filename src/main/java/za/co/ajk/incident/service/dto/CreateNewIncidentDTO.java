package za.co.ajk.incident.service.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;


//@Data
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
    private String operator;
    
    private List<Equipment> equipmentList;
    
    public String getIncidentPriorityCode() {
        return incidentPriorityCode;
    }
    
    public void setIncidentPriorityCode(String incidentPriorityCode) {
        this.incidentPriorityCode = incidentPriorityCode;
    }
    
    public String getIncidentTypeCode() {
        return incidentTypeCode;
    }
    
    public void setIncidentTypeCode(String incidentTypeCode) {
        this.incidentTypeCode = incidentTypeCode;
    }
    
    public String getIncidentHeader() {
        return incidentHeader;
    }
    
    public void setIncidentHeader(String incidentHeader) {
        this.incidentHeader = incidentHeader;
    }
    
    public String getIncidentDescription() {
        return incidentDescription;
    }
    
    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }
    
    public String getIncidentStatusCode() {
        return incidentStatusCode;
    }
    
    public void setIncidentStatusCode(String incidentStatusCode) {
        this.incidentStatusCode = incidentStatusCode;
    }
    
    public Long getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }
    
    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }
    
    public static class Equipment{
        private Long equipmentId;
        private boolean onLoan;
        private boolean isReplacement;
        private String equipmentComment;
        private String equipmentActionCode;
        
        public Long getEquipmentId() {
            return equipmentId;
        }
    
        public void setEquipmentId(Long equipmentId) {
            this.equipmentId = equipmentId;
        }
    
        public boolean isOnLoan() {
            return onLoan;
        }
    
        public void setOnLoan(boolean onLoan) {
            this.onLoan = onLoan;
        }
    
        public boolean isReplacement() {
            return isReplacement;
        }
    
        public void setReplacement(boolean replacement) {
            isReplacement = replacement;
        }
    
        public String getEquipmentComment() {
            return equipmentComment;
        }
    
        public void setEquipmentComment(String equipmentComment) {
            this.equipmentComment = equipmentComment;
        }
    
        public String getEquipmentActionCode() {
            return equipmentActionCode;
        }
    
        public void setEquipmentActionCode(String equipmentActionCode) {
            this.equipmentActionCode = equipmentActionCode;
        }
    }
}
