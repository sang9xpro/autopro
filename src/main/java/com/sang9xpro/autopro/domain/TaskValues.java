package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskValues.
 */
@Entity
@Table(name = "task_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties(value = { "taskValues", "applications" }, allowSetters = true)
    private Tasks tasks;

    @ManyToOne
    @JsonIgnoreProperties(value = { "taskValues" }, allowSetters = true)
    private TaskFields taskFields;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TaskValues id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public TaskValues value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Tasks getTasks() {
        return this.tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public TaskValues tasks(Tasks tasks) {
        this.setTasks(tasks);
        return this;
    }

    public TaskFields getTaskFields() {
        return this.taskFields;
    }

    public void setTaskFields(TaskFields taskFields) {
        this.taskFields = taskFields;
    }

    public TaskValues taskFields(TaskFields taskFields) {
        this.setTaskFields(taskFields);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskValues)) {
            return false;
        }
        return id != null && id.equals(((TaskValues) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskValues{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
