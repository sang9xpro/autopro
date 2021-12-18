package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskFields.
 */
@Entity
@Table(name = "task_fields")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskFields implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @OneToMany(mappedBy = "taskFields")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tasks", "taskFields" }, allowSetters = true)
    private Set<TaskValues> taskValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TaskFields id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public TaskFields fieldName(String fieldName) {
        this.setFieldName(fieldName);
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Set<TaskValues> getTaskValues() {
        return this.taskValues;
    }

    public void setTaskValues(Set<TaskValues> taskValues) {
        if (this.taskValues != null) {
            this.taskValues.forEach(i -> i.setTaskFields(null));
        }
        if (taskValues != null) {
            taskValues.forEach(i -> i.setTaskFields(this));
        }
        this.taskValues = taskValues;
    }

    public TaskFields taskValues(Set<TaskValues> taskValues) {
        this.setTaskValues(taskValues);
        return this;
    }

    public TaskFields addTaskValues(TaskValues taskValues) {
        this.taskValues.add(taskValues);
        taskValues.setTaskFields(this);
        return this;
    }

    public TaskFields removeTaskValues(TaskValues taskValues) {
        this.taskValues.remove(taskValues);
        taskValues.setTaskFields(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskFields)) {
            return false;
        }
        return id != null && id.equals(((TaskFields) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskFields{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            "}";
    }
}
