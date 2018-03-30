package za.co.ajk.incident.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import za.co.ajk.incident.service.dto.EnumInformationDTO;
import za.co.ajk.incident.service.impl.EnumInformationImpl;

import static junit.framework.TestCase.assertTrue;


public class EnumInformationTest {
    

    private EnumInformation enumInformation = new EnumInformationImpl();
    
    @Before
    public void setup(){
    
    }

    @Test
    public void testGetInformationForEnum() throws Exception{
        String enumType = "EventType";
    
        List<EnumInformationDTO> list = enumInformation.retrieveEnumInformation(enumType);
        assertTrue(list.size() >= 1);
    
        enumType = "IncidentPriority";
        list = enumInformation.retrieveEnumInformation(enumType);
        assertTrue(list.size() >= 1);
    
        enumType = "IncidentStatusType";
        list = enumInformation.retrieveEnumInformation(enumType);
        assertTrue(list.size() >= 1);
    
        enumType = "Invalid";
        list = enumInformation.retrieveEnumInformation(enumType);
        assertTrue(list.size() == 0);
    }
    
}

