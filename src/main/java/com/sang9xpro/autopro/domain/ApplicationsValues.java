package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApplicationsValues.
 */
@Entity
@Table(name = "applications_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationsValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties(value = { "applicationsValues", "accounts", "tasks", "comments" }, allowSetters = true)
    private Applications applications;

    @ManyToOne
    @JsonIgnoreProperties(value = { "applicationsValues" }, allowSetters = true)
    private ApplicationsFields applicationsFields;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicationsValues id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public ApplicationsValues value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Applications getApplications() {
        return this.applications;
    }

    public void setApplications(Applications applications) {
        this.applications = applications;
    }

    public ApplicationsValues applications(Applications applications) {
        this.setApplications(applications);
        return this;
    }

    public ApplicationsFields getApplicationsFields() {
        return this.applicationsFields;
    }

    public void setApplicationsFields(ApplicationsFields applicationsFields) {
        this.applicationsFields = applicationsFields;
    }

    public ApplicationsValues applicationsFields(ApplicationsFields applicationsFields) {
        this.setApplicationsFields(applicationsFields);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationsValues)) {
            return false;
        }
        return id != null && id.equals(((ApplicationsValues) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationsValues{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
