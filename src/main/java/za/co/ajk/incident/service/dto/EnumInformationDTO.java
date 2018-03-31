package za.co.ajk.incident.service.dto;

import java.io.Serializable;

public class EnumInformationDTO implements Serializable {
    private String enumCode;
    private String enumDescription;
    
    public EnumInformationDTO() {
    }
    
    public EnumInformationDTO(String enumCode, String enumDescription) {
        this.enumCode = enumCode;
        this.enumDescription = enumDescription;
    }
    
    public String getEnumCode() {
        return enumCode;
    }
    
    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }
    
    public String getEnumDescription() {
        return enumDescription;
    }
    
    public void setEnumDescription(String enumDescription) {
        this.enumDescription = enumDescription;
    }
}
