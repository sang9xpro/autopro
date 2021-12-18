package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SchedulerTaskFields.
 */
@Entity
@Table(name = "scheduler_task_fields")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SchedulerTaskFields implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @OneToMany(mappedBy = "schedulerTaskFields")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "schedulerTask", "schedulerTaskFields" }, allowSetters = true)
    private Set<SchedulerTaskValues> schedulerTaskValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SchedulerTaskFields id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public SchedulerTaskFields fieldName(String fieldName) {
        this.setFieldName(fieldName);
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Set<SchedulerTaskValues> getSchedulerTaskValues() {
        return this.schedulerTaskValues;
    }

    public void setSchedulerTaskValues(Set<SchedulerTaskValues> schedulerTaskValues) {
        if (this.schedulerTaskValues != null) {
            this.schedulerTaskValues.forEach(i -> i.setSchedulerTaskFields(null));
        }
        if (schedulerTaskValues != null) {
            schedulerTaskValues.forEach(i -> i.setSchedulerTaskFields(this));
        }
        this.schedulerTaskValues = schedulerTaskValues;
    }

    public SchedulerTaskFields schedulerTaskValues(Set<SchedulerTaskValues> schedulerTaskValues) {
        this.setSchedulerTaskValues(schedulerTaskValues);
        return this;
    }

    public SchedulerTaskFields addSchedulerTaskValues(SchedulerTaskValues schedulerTaskValues) {
        this.schedulerTaskValues.add(schedulerTaskValues);
        schedulerTaskValues.setSchedulerTaskFields(this);
        return this;
    }

    public SchedulerTaskFields removeSchedulerTaskValues(SchedulerTaskValues schedulerTaskValues) {
        this.schedulerTaskValues.remove(schedulerTaskValues);
        schedulerTaskValues.setSchedulerTaskFields(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchedulerTaskFields)) {
            return false;
        }
        return id != null && id.equals(((SchedulerTaskFields) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchedulerTaskFields{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            "}";
    }
}
