package za.co.ajk.incident.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the IncidentActivity entity.
 */
public class IncidentActivityDTO implements Serializable {

    private Long id;

    @NotNull
    private String eventTypeCode;

    @NotNull
    private Integer eventNumber;

    @NotNull
    private String updatedPriorityCode;

    @NotNull
    private String updatedStatusCode;

    @NotNull
    private String incidentComment;

    private Instant dateCreated;

    private String createdBy;

    private Instant dateUpdated;

    private String updatedBy;

    private Long incidentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventTypeCode() {
        return eventTypeCode;
    }

    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }

    public Integer getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(Integer eventNumber) {
        this.eventNumber = eventNumber;
    }

    public String getUpdatedPriorityCode() {
        return updatedPriorityCode;
    }

    public void setUpdatedPriorityCode(String updatedPriorityCode) {
        this.updatedPriorityCode = updatedPriorityCode;
    }

    public String getUpdatedStatusCode() {
        return updatedStatusCode;
    }

    public void setUpdatedStatusCode(String updatedStatusCode) {
        this.updatedStatusCode = updatedStatusCode;
    }

    public String getIncidentComment() {
        return incidentComment;
    }

    public void setIncidentComment(String incidentComment) {
        this.incidentComment = incidentComment;
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

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Long incidentId) {
        this.incidentId = incidentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidentActivityDTO incidentActivityDTO = (IncidentActivityDTO) o;
        if(incidentActivityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidentActivityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidentActivityDTO{" +
            "id=" + getId() +
            ", eventTypeCode='" + getEventTypeCode() + "'" +
            ", eventNumber=" + getEventNumber() +
            ", updatedPriorityCode='" + getUpdatedPriorityCode() + "'" +
            ", updatedStatusCode='" + getUpdatedStatusCode() + "'" +
            ", incidentComment='" + getIncidentComment() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
