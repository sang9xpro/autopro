package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sang9xpro.autopro.domain.enumeration.SchedulerStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SchedulerTask.
 */
@Entity
@Table(name = "scheduler_task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SchedulerTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "count_from")
    private Integer countFrom;

    @Column(name = "count_to")
    private Integer countTo;

    @Column(name = "point")
    private Double point;

    @Column(name = "last_update")
    private Instant lastUpdate;

    @Column(name = "owner")
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SchedulerStatus status;

    @OneToMany(mappedBy = "schedulerTask")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "schedulerTask", "schedulerTaskFields" }, allowSetters = true)
    private Set<SchedulerTaskValues> schedulerTaskValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SchedulerTask id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public SchedulerTask startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public SchedulerTask endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getCountFrom() {
        return this.countFrom;
    }

    public SchedulerTask countFrom(Integer countFrom) {
        this.setCountFrom(countFrom);
        return this;
    }

    public void setCountFrom(Integer countFrom) {
        this.countFrom = countFrom;
    }

    public Integer getCountTo() {
        return this.countTo;
    }

    public SchedulerTask countTo(Integer countTo) {
        this.setCountTo(countTo);
        return this;
    }

    public void setCountTo(Integer countTo) {
        this.countTo = countTo;
    }

    public Double getPoint() {
        return this.point;
    }

    public SchedulerTask point(Double point) {
        this.setPoint(point);
        return this;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public Instant getLastUpdate() {
        return this.lastUpdate;
    }

    public SchedulerTask lastUpdate(Instant lastUpdate) {
        this.setLastUpdate(lastUpdate);
        return this;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getOwner() {
        return this.owner;
    }

    public SchedulerTask owner(String owner) {
        this.setOwner(owner);
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public SchedulerStatus getStatus() {
        return this.status;
    }

    public SchedulerTask status(SchedulerStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(SchedulerStatus status) {
        this.status = status;
    }

    public Set<SchedulerTaskValues> getSchedulerTaskValues() {
        return this.schedulerTaskValues;
    }

    public void setSchedulerTaskValues(Set<SchedulerTaskValues> schedulerTaskValues) {
        if (this.schedulerTaskValues != null) {
            this.schedulerTaskValues.forEach(i -> i.setSchedulerTask(null));
        }
        if (schedulerTaskValues != null) {
            schedulerTaskValues.forEach(i -> i.setSchedulerTask(this));
        }
        this.schedulerTaskValues = schedulerTaskValues;
    }

    public SchedulerTask schedulerTaskValues(Set<SchedulerTaskValues> schedulerTaskValues) {
        this.setSchedulerTaskValues(schedulerTaskValues);
        return this;
    }

    public SchedulerTask addSchedulerTaskValues(SchedulerTaskValues schedulerTaskValues) {
        this.schedulerTaskValues.add(schedulerTaskValues);
        schedulerTaskValues.setSchedulerTask(this);
        return this;
    }

    public SchedulerTask removeSchedulerTaskValues(SchedulerTaskValues schedulerTaskValues) {
        this.schedulerTaskValues.remove(schedulerTaskValues);
        schedulerTaskValues.setSchedulerTask(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchedulerTask)) {
            return false;
        }
        return id != null && id.equals(((SchedulerTask) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchedulerTask{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", countFrom=" + getCountFrom() +
            ", countTo=" + getCountTo() +
            ", point=" + getPoint() +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", owner='" + getOwner() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
