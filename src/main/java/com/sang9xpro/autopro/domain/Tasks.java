package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sang9xpro.autopro.domain.enumeration.TaskType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tasks.
 */
@Entity
@Table(name = "tasks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tasks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "source")
    private String source;

    @Column(name = "price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaskType type;

    @OneToMany(mappedBy = "tasks")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tasks", "taskFields" }, allowSetters = true)
    private Set<TaskValues> taskValues = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "applicationsValues", "accounts", "tasks", "comments" }, allowSetters = true)
    private Applications applications;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tasks id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public Tasks taskName(String taskName) {
        this.setTaskName(taskName);
        return this;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return this.description;
    }

    public Tasks description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return this.source;
    }

    public Tasks source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Double getPrice() {
        return this.price;
    }

    public Tasks price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TaskType getType() {
        return this.type;
    }

    public Tasks type(TaskType type) {
        this.setType(type);
        return this;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Set<TaskValues> getTaskValues() {
        return this.taskValues;
    }

    public void setTaskValues(Set<TaskValues> taskValues) {
        if (this.taskValues != null) {
            this.taskValues.forEach(i -> i.setTasks(null));
        }
        if (taskValues != null) {
            taskValues.forEach(i -> i.setTasks(this));
        }
        this.taskValues = taskValues;
    }

    public Tasks taskValues(Set<TaskValues> taskValues) {
        this.setTaskValues(taskValues);
        return this;
    }

    public Tasks addTaskValues(TaskValues taskValues) {
        this.taskValues.add(taskValues);
        taskValues.setTasks(this);
        return this;
    }

    public Tasks removeTaskValues(TaskValues taskValues) {
        this.taskValues.remove(taskValues);
        taskValues.setTasks(null);
        return this;
    }

    public Applications getApplications() {
        return this.applications;
    }

    public void setApplications(Applications applications) {
        this.applications = applications;
    }

    public Tasks applications(Applications applications) {
        this.setApplications(applications);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tasks)) {
            return false;
        }
        return id != null && id.equals(((Tasks) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tasks{" +
            "id=" + getId() +
            ", taskName='" + getTaskName() + "'" +
            ", description='" + getDescription() + "'" +
            ", source='" + getSource() + "'" +
            ", price=" + getPrice() +
            ", type='" + getType() + "'" +
            "}";
    }
}
