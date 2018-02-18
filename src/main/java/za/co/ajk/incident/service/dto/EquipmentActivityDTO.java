package za.co.ajk.incident.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the EquipmentActivity entity.
 */
public class EquipmentActivityDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean onLoan;

    @NotNull
    private Boolean replacement;

    @NotNull
    private String equipmentActionCode;

    @NotNull
    private String activityComment;

    private Instant dateCreated;

    private String createdBy;

    private Long incidentActivityId;

    private Long equipmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isOnLoan() {
        return onLoan;
    }

    public void setOnLoan(Boolean onLoan) {
        this.onLoan = onLoan;
    }

    public Boolean isReplacement() {
        return replacement;
    }

    public void setReplacement(Boolean replacement) {
        this.replacement = replacement;
    }

    public String getEquipmentActionCode() {
        return equipmentActionCode;
    }

    public void setEquipmentActionCode(String equipmentActionCode) {
        this.equipmentActionCode = equipmentActionCode;
    }

    public String getActivityComment() {
        return activityComment;
    }

    public void setActivityComment(String activityComment) {
        this.activityComment = activityComment;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getIncidentActivityId() {
        return incidentActivityId;
    }

    public void setIncidentActivityId(Long incidentActivityId) {
        this.incidentActivityId = incidentActivityId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquipmentActivityDTO equipmentActivityDTO = (EquipmentActivityDTO) o;
        if(equipmentActivityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipmentActivityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EquipmentActivityDTO{" +
            "id=" + getId() +
            ", onLoan='" + isOnLoan() + "'" +
            ", replacement='" + isReplacement() + "'" +
            ", equipmentActionCode='" + getEquipmentActionCode() + "'" +
            ", activityComment='" + getActivityComment() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
