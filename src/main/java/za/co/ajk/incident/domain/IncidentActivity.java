package za.co.ajk.incident.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A IncidentActivity.
 */
@Entity
@Table(name = "incident_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "incidentactivity")
public class IncidentActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "event_type_code", nullable = false)
    private String eventTypeCode;

    @NotNull
    @Column(name = "event_number", nullable = false)
    private Integer eventNumber;

    @NotNull
    @Column(name = "updated_priority_code", nullable = false)
    private String updatedPriorityCode;

    @NotNull
    @Column(name = "updated_status_code", nullable = false)
    private String updatedStatusCode;

    @NotNull
    @Column(name = "incident_comment", nullable = false)
    private String incidentComment;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "date_last_updated")
    private Instant dateLastUpdated;

    @Column(name = "updated_by")
    private Instant updatedBy;

    @OneToMany(mappedBy = "incidentActivity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EquipmentActivity> equipment = new HashSet<>();

    @ManyToOne
    private Incident incident;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventTypeCode() {
        return eventTypeCode;
    }

    public IncidentActivity eventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
        return this;
    }

    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }

    public Integer getEventNumber() {
        return eventNumber;
    }

    public IncidentActivity eventNumber(Integer eventNumber) {
        this.eventNumber = eventNumber;
        return this;
    }

    public void setEventNumber(Integer eventNumber) {
        this.eventNumber = eventNumber;
    }

    public String getUpdatedPriorityCode() {
        return updatedPriorityCode;
    }

    public IncidentActivity updatedPriorityCode(String updatedPriorityCode) {
        this.updatedPriorityCode = updatedPriorityCode;
        return this;
    }

    public void setUpdatedPriorityCode(String updatedPriorityCode) {
        this.updatedPriorityCode = updatedPriorityCode;
    }

    public String getUpdatedStatusCode() {
        return updatedStatusCode;
    }

    public IncidentActivity updatedStatusCode(String updatedStatusCode) {
        this.updatedStatusCode = updatedStatusCode;
        return this;
    }

    public void setUpdatedStatusCode(String updatedStatusCode) {
        this.updatedStatusCode = updatedStatusCode;
    }

    public String getIncidentComment() {
        return incidentComment;
    }

    public IncidentActivity incidentComment(String incidentComment) {
        this.incidentComment = incidentComment;
        return this;
    }

    public void setIncidentComment(String incidentComment) {
        this.incidentComment = incidentComment;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public IncidentActivity dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public IncidentActivity createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getDateLastUpdated() {
        return dateLastUpdated;
    }

    public IncidentActivity dateLastUpdated(Instant dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
        return this;
    }

    public void setDateLastUpdated(Instant dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public Instant getUpdatedBy() {
        return updatedBy;
    }

    public IncidentActivity updatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<EquipmentActivity> getEquipment() {
        return equipment;
    }

    public IncidentActivity equipment(Set<EquipmentActivity> equipmentActivities) {
        this.equipment = equipmentActivities;
        return this;
    }

    public IncidentActivity addEquipment(EquipmentActivity equipmentActivity) {
        this.equipment.add(equipmentActivity);
        equipmentActivity.setIncidentActivity(this);
        return this;
    }

    public IncidentActivity removeEquipment(EquipmentActivity equipmentActivity) {
        this.equipment.remove(equipmentActivity);
        equipmentActivity.setIncidentActivity(null);
        return this;
    }

    public void setEquipment(Set<EquipmentActivity> equipmentActivities) {
        this.equipment = equipmentActivities;
    }

    public Incident getIncident() {
        return incident;
    }

    public IncidentActivity incident(Incident incident) {
        this.incident = incident;
        return this;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
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
        IncidentActivity incidentActivity = (IncidentActivity) o;
        if (incidentActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidentActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidentActivity{" +
            "id=" + getId() +
            ", eventTypeCode='" + getEventTypeCode() + "'" +
            ", eventNumber=" + getEventNumber() +
            ", updatedPriorityCode='" + getUpdatedPriorityCode() + "'" +
            ", updatedStatusCode='" + getUpdatedStatusCode() + "'" +
            ", incidentComment='" + getIncidentComment() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", dateLastUpdated='" + getDateLastUpdated() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
