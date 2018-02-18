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
 * A Equipment.
 */
@Entity
@Table(name = "equipment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "equipment")
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "equipment_id", nullable = false)
    private Integer equipmentId;

    @NotNull
    @Column(name = "date_added", nullable = false)
    private Instant dateAdded;

    @NotNull
    @Column(name = "added_by", nullable = false)
    private String addedBy;

    @OneToMany(mappedBy = "equipment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EquipmentActivity> equipmentActivities = new HashSet<>();

    @ManyToOne
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public Equipment equipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
        return this;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Instant getDateAdded() {
        return dateAdded;
    }

    public Equipment dateAdded(Instant dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(Instant dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public Equipment addedBy(String addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Set<EquipmentActivity> getEquipmentActivities() {
        return equipmentActivities;
    }

    public Equipment equipmentActivities(Set<EquipmentActivity> equipmentActivities) {
        this.equipmentActivities = equipmentActivities;
        return this;
    }

    public Equipment addEquipmentActivity(EquipmentActivity equipmentActivity) {
        this.equipmentActivities.add(equipmentActivity);
        equipmentActivity.setEquipment(this);
        return this;
    }

    public Equipment removeEquipmentActivity(EquipmentActivity equipmentActivity) {
        this.equipmentActivities.remove(equipmentActivity);
        equipmentActivity.setEquipment(null);
        return this;
    }

    public void setEquipmentActivities(Set<EquipmentActivity> equipmentActivities) {
        this.equipmentActivities = equipmentActivities;
    }

    public Company getCompany() {
        return company;
    }

    public Equipment company(Company company) {
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
        Equipment equipment = (Equipment) o;
        if (equipment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Equipment{" +
            "id=" + getId() +
            ", equipmentId=" + getEquipmentId() +
            ", dateAdded='" + getDateAdded() + "'" +
            ", addedBy='" + getAddedBy() + "'" +
            "}";
    }
}
