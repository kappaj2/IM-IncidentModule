package za.co.ajk.incident.service.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class EnumInformationDTO implements Serializable {
    private String enumCode;
    private String enumDescription;
}
