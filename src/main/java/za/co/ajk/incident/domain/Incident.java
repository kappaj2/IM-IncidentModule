package za.co.ajk.incident.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Incident.
 */
@Entity
@Table(name = "incident")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "incident")
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "incident_number", nullable = false)
    private Integer incidentNumber;

    @NotNull
    @Column(name = "incident_priority_code", nullable = false)
    private String incidentPriorityCode;

    @NotNull
    @Column(name = "incident_type_code", nullable = false)
    private String incidentTypeCode;

    @NotNull
    @Column(name = "incident_header", nullable = false)
    private String incidentHeader;

    @NotNull
    @Column(name = "incident_description", nullable = false)
    private String incidentDescription;

    @NotNull
    @Column(name = "incident_status_code", nullable = false)
    private String incidentStatusCode;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @NotNull
    @Column(name = "date_updated", nullable = false)
    private Instant dateUpdated;

    @NotNull
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Column(name = "incident_resolution")
    private String incidentResolution;

    @Column(name = "date_closed")
    private Instant dateClosed;

    @Column(name = "closed_by")
    private String closedBy;

    @OneToMany(mappedBy = "incident",fetch = FetchType.LAZY)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IncidentActivity> incidentActivities = new HashSet<>();

    @ManyToOne
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIncidentNumber() {
        return incidentNumber;
    }

    public Incident incidentNumber(Integer incidentNumber) {
        this.incidentNumber = incidentNumber;
        return this;
    }

    public void setIncidentNumber(Integer incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

    public String getIncidentPriorityCode() {
        return incidentPriorityCode;
    }

    public Incident incidentPriorityCode(String incidentPriorityCode) {
        this.incidentPriorityCode = incidentPriorityCode;
        return this;
    }

    public void setIncidentPriorityCode(String incidentPriorityCode) {
        this.incidentPriorityCode = incidentPriorityCode;
    }

    public String getIncidentTypeCode() {
        return incidentTypeCode;
    }

    public Incident incidentTypeCode(String incidentTypeCode) {
        this.incidentTypeCode = incidentTypeCode;
        return this;
    }

    public void setIncidentTypeCode(String incidentTypeCode) {
        this.incidentTypeCode = incidentTypeCode;
    }

    public String getIncidentHeader() {
        return incidentHeader;
    }

    public Incident incidentHeader(String incidentHeader) {
        this.incidentHeader = incidentHeader;
        return this;
    }

    public void setIncidentHeader(String incidentHeader) {
        this.incidentHeader = incidentHeader;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public Incident incidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
        return this;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public String getIncidentStatusCode() {
        return incidentStatusCode;
    }

    public Incident incidentStatusCode(String incidentStatusCode) {
        this.incidentStatusCode = incidentStatusCode;
        return this;
    }

    public void setIncidentStatusCode(String incidentStatusCode) {
        this.incidentStatusCode = incidentStatusCode;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Incident dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Incident createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Incident dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Incident updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getIncidentResolution() {
        return incidentResolution;
    }

    public Incident incidentResolution(String incidentResolution) {
        this.incidentResolution = incidentResolution;
        return this;
    }

    public void setIncidentResolution(String incidentResolution) {
        this.incidentResolution = incidentResolution;
    }

    public Instant getDateClosed() {
        return dateClosed;
    }

    public Incident dateClosed(Instant dateClosed) {
        this.dateClosed = dateClosed;
        return this;
    }

    public void setDateClosed(Instant dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public Incident closedBy(String closedBy) {
        this.closedBy = closedBy;
        return this;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public Set<IncidentActivity> getIncidentActivities() {
        return incidentActivities;
    }

    public Incident incidentActivities(Set<IncidentActivity> incidentActivities) {
        this.incidentActivities = incidentActivities;
        return this;
    }

    public Incident addIncidentActivity(IncidentActivity incidentActivity) {
        this.incidentActivities.add(incidentActivity);
        incidentActivity.setIncident(this);
        return this;
    }

    public Incident removeIncidentActivity(IncidentActivity incidentActivity) {
        this.incidentActivities.remove(incidentActivity);
        incidentActivity.setIncident(null);
        return this;
    }

    public void setIncidentActivities(Set<IncidentActivity> incidentActivities) {
        this.incidentActivities = incidentActivities;
    }

    public Company getCompany() {
        return company;
    }

    public Incident company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Incident incident = (Incident) o;
        if (incident.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incident.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Incident{" +
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
