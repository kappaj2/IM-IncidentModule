package za.co.ajk.incident.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A EquipmentActivity.
 */
@Entity
@Table(name = "equipment_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "equipmentactivity")
public class EquipmentActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "on_loan", nullable = false)
    private Boolean onLoan;

    @NotNull
    @Column(name = "replacement", nullable = false)
    private Boolean replacement;

    @NotNull
    @Column(name = "equipment_action_code", nullable = false)
    private String equipmentActionCode;

    @NotNull
    @Column(name = "activity_comment", nullable = false)
    private String activityComment;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToOne
    private IncidentActivity incidentActivity;

    @ManyToOne
    private Equipment equipment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isOnLoan() {
        return onLoan;
    }

    public EquipmentActivity onLoan(Boolean onLoan) {
        this.onLoan = onLoan;
        return this;
    }

    public void setOnLoan(Boolean onLoan) {
        this.onLoan = onLoan;
    }

    public Boolean isReplacement() {
        return replacement;
    }

    public EquipmentActivity replacement(Boolean replacement) {
        this.replacement = replacement;
        return this;
    }

    public void setReplacement(Boolean replacement) {
        this.replacement = replacement;
    }

    public String getEquipmentActionCode() {
        return equipmentActionCode;
    }

    public EquipmentActivity equipmentActionCode(String equipmentActionCode) {
        this.equipmentActionCode = equipmentActionCode;
        return this;
    }

    public void setEquipmentActionCode(String equipmentActionCode) {
        this.equipmentActionCode = equipmentActionCode;
    }

    public String getActivityComment() {
        return activityComment;
    }

    public EquipmentActivity activityComment(String activityComment) {
        this.activityComment = activityComment;
        return this;
    }

    public void setActivityComment(String activityComment) {
        this.activityComment = activityComment;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public EquipmentActivity dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public EquipmentActivity createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public IncidentActivity getIncidentActivity() {
        return incidentActivity;
    }

    public EquipmentActivity incidentActivity(IncidentActivity incidentActivity) {
        this.incidentActivity = incidentActivity;
        return this;
    }

    public void setIncidentActivity(IncidentActivity incidentActivity) {
        this.incidentActivity = incidentActivity;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public EquipmentActivity equipment(Equipment equipment) {
        this.equipment = equipment;
        return this;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
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
        EquipmentActivity equipmentActivity = (EquipmentActivity) o;
        if (equipmentActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipmentActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EquipmentActivity{" +
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
