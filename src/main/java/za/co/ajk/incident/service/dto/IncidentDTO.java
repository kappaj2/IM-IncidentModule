package za.co.ajk.incident.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Incident entity.
 */
public class IncidentDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer incidentNumber;

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
    private Instant dateCreated;

    @NotNull
    private String createdBy;

    @NotNull
    private Instant dateUpdated;

    @NotNull
    private String updatedBy;

    private String incidentResolution;

    private Instant dateClosed;

    private String closedBy;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIncidentNumber() {
        return incidentNumber;
    }

    public void setIncidentNumber(Integer incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

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

    public String getIncidentResolution() {
        return incidentResolution;
    }

    public void setIncidentResolution(String incidentResolution) {
        this.incidentResolution = incidentResolution;
    }

    public Instant getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Instant dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
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

        IncidentDTO incidentDTO = (IncidentDTO) o;
        if(incidentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidentDTO{" +
            "id=" + getId() +
            ", incidentNumber=" + getIncidentNumber() +
            ", incidentPriorityCode='" + getIncidentPriorityCode() + "'" +
            ", incidentTypeCode='" + getIncidentTypeCode() + "'" +
            ", incidentHeader='" + getIncidentHeader() + "'" +
            ", incidentDescription='" + getIncidentDescription() + "'" +
            ", incidentStatusCode='" + getIncidentStatusCode() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", incidentResolution='" + getIncidentResolution() + "'" +
            ", dateClosed='" + getDateClosed() + "'" +
            ", closedBy='" + getClosedBy() + "'" +
            "}";
    }
}
