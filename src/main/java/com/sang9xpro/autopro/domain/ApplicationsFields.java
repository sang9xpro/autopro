package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApplicationsFields.
 */
@Entity
@Table(name = "applications_fields")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationsFields implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @OneToMany(mappedBy = "applicationsFields")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applications", "applicationsFields" }, allowSetters = true)
    private Set<ApplicationsValues> applicationsValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicationsFields id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public ApplicationsFields fieldName(String fieldName) {
        this.setFieldName(fieldName);
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Set<ApplicationsValues> getApplicationsValues() {
        return this.applicationsValues;
    }

    public void setApplicationsValues(Set<ApplicationsValues> applicationsValues) {
        if (this.applicationsValues != null) {
            this.applicationsValues.forEach(i -> i.setApplicationsFields(null));
        }
        if (applicationsValues != null) {
            applicationsValues.forEach(i -> i.setApplicationsFields(this));
        }
        this.applicationsValues = applicationsValues;
    }

    public ApplicationsFields applicationsValues(Set<ApplicationsValues> applicationsValues) {
        this.setApplicationsValues(applicationsValues);
        return this;
    }

    public ApplicationsFields addApplicationsValues(ApplicationsValues applicationsValues) {
        this.applicationsValues.add(applicationsValues);
        applicationsValues.setApplicationsFields(this);
        return this;
    }

    public ApplicationsFields removeApplicationsValues(ApplicationsValues applicationsValues) {
        this.applicationsValues.remove(applicationsValues);
        applicationsValues.setApplicationsFields(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationsFields)) {
            return false;
        }
        return id != null && id.equals(((ApplicationsFields) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationsFields{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            "}";
    }
}
