package za.co.ajk.incident.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "company_code", nullable = false)
    private String companyCode;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "branch_code")
    private String branchCode;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Incident> incidents = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Equipment> equipment = new HashSet<>();

    @ManyToOne
    private Region region;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public Company companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public Company branchCode(String branchCode) {
        this.branchCode = branchCode;
        return this;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public Set<Incident> getIncidents() {
        return incidents;
    }

    public Company incidents(Set<Incident> incidents) {
        this.incidents = incidents;
        return this;
    }

    public Company addIncident(Incident incident) {
        this.incidents.add(incident);
        incident.setCompany(this);
        return this;
    }

    public Company removeIncident(Incident incident) {
        this.incidents.remove(incident);
        incident.setCompany(null);
        return this;
    }

    public void setIncidents(Set<Incident> incidents) {
        this.incidents = incidents;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public Company equipment(Set<Equipment> equipment) {
        this.equipment = equipment;
        return this;
    }

    public Company addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
        equipment.setCompany(this);
        return this;
    }

    public Company removeEquipment(Equipment equipment) {
        this.equipment.remove(equipment);
        equipment.setCompany(null);
        return this;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    public Region getRegion() {
        return region;
    }

    public Company region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
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
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyCode='" + getCompanyCode() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", branchCode='" + getBranchCode() + "'" +
            "}";
    }
}
