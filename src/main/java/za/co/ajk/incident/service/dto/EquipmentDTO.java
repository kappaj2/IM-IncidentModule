package za.co.ajk.incident.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Equipment entity.
 */
public class EquipmentDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer equipmentId;

    @NotNull
    private Instant dateAdded;

    @NotNull
    private Integer addedBy;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Instant getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Instant dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquipmentDTO equipmentDTO = (EquipmentDTO) o;
        if(equipmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EquipmentDTO{" +
            "id=" + getId() +
            ", equipmentId=" + getEquipmentId() +
            ", dateAdded='" + getDateAdded() + "'" +
            ", addedBy=" + getAddedBy() +
            "}";
    }
}
