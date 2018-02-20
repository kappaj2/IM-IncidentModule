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
    private String createdBy;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "incidentActivity", fetch = FetchType.LAZY)
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

    public String getCreatedBy() {
        return createdBy;
    }

    public IncidentActivity createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public IncidentActivity dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public IncidentActivity updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
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
            ", createdBy='" + getCreatedBy() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
