package za.co.ajk.incident.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import za.co.ajk.incident.service.EnumInformation;
import za.co.ajk.incident.service.dto.EnumInformationDTO;


/**
 * Rest controller that will export the different Enum's so we can lookup and use in the UI.
 */
@RestController
@RequestMapping("/api")
public class EnumResource {
    
    private final Logger log = LoggerFactory.getLogger(EnumResource.class);
    
    @Autowired
    private EnumInformation enumInformation;
    
    /**
     * GET  /v1/enuminfo/{enumType} : get all the companies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companies in body
     */
    @GetMapping("/v1/enuminfo/{enumType}")
    @Timed
    public List<EnumInformationDTO> getENUMInformation(@PathVariable  String enumType) {
        log.debug("REST request to get information for enum "+enumType);
        return enumInformation.retrieveEnumInformation(enumType);
    }


}
