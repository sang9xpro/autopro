package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SchedulerTaskValues.
 */
@Entity
@Table(name = "scheduler_task_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SchedulerTaskValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties(value = { "schedulerTaskValues" }, allowSetters = true)
    private SchedulerTask schedulerTask;

    @ManyToOne
    @JsonIgnoreProperties(value = { "schedulerTaskValues" }, allowSetters = true)
    private SchedulerTaskFields schedulerTaskFields;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SchedulerTaskValues id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public SchedulerTaskValues value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SchedulerTask getSchedulerTask() {
        return this.schedulerTask;
    }

    public void setSchedulerTask(SchedulerTask schedulerTask) {
        this.schedulerTask = schedulerTask;
    }

    public SchedulerTaskValues schedulerTask(SchedulerTask schedulerTask) {
        this.setSchedulerTask(schedulerTask);
        return this;
    }

    public SchedulerTaskFields getSchedulerTaskFields() {
        return this.schedulerTaskFields;
    }

    public void setSchedulerTaskFields(SchedulerTaskFields schedulerTaskFields) {
        this.schedulerTaskFields = schedulerTaskFields;
    }

    public SchedulerTaskValues schedulerTaskFields(SchedulerTaskFields schedulerTaskFields) {
        this.setSchedulerTaskFields(schedulerTaskFields);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchedulerTaskValues)) {
            return false;
        }
        return id != null && id.equals(((SchedulerTaskValues) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchedulerTaskValues{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
