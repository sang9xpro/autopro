package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Loggers.
 */
@Entity
@Table(name = "loggers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Loggers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private String status;

    @Lob
    @Column(name = "log_detail")
    private byte[] logDetail;

    @Column(name = "log_detail_content_type")
    private String logDetailContentType;

    @Column(name = "last_update")
    private Instant lastUpdate;

    @OneToMany(mappedBy = "loggers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "loggers", "loggersFields" }, allowSetters = true)
    private Set<LoggersValues> loggersValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Loggers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public Loggers status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getLogDetail() {
        return this.logDetail;
    }

    public Loggers logDetail(byte[] logDetail) {
        this.setLogDetail(logDetail);
        return this;
    }

    public void setLogDetail(byte[] logDetail) {
        this.logDetail = logDetail;
    }

    public String getLogDetailContentType() {
        return this.logDetailContentType;
    }

    public Loggers logDetailContentType(String logDetailContentType) {
        this.logDetailContentType = logDetailContentType;
        return this;
    }

    public void setLogDetailContentType(String logDetailContentType) {
        this.logDetailContentType = logDetailContentType;
    }

    public Instant getLastUpdate() {
        return this.lastUpdate;
    }

    public Loggers lastUpdate(Instant lastUpdate) {
        this.setLastUpdate(lastUpdate);
        return this;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Set<LoggersValues> getLoggersValues() {
        return this.loggersValues;
    }

    public void setLoggersValues(Set<LoggersValues> loggersValues) {
        if (this.loggersValues != null) {
            this.loggersValues.forEach(i -> i.setLoggers(null));
        }
        if (loggersValues != null) {
            loggersValues.forEach(i -> i.setLoggers(this));
        }
        this.loggersValues = loggersValues;
    }

    public Loggers loggersValues(Set<LoggersValues> loggersValues) {
        this.setLoggersValues(loggersValues);
        return this;
    }

    public Loggers addLoggersValues(LoggersValues loggersValues) {
        this.loggersValues.add(loggersValues);
        loggersValues.setLoggers(this);
        return this;
    }

    public Loggers removeLoggersValues(LoggersValues loggersValues) {
        this.loggersValues.remove(loggersValues);
        loggersValues.setLoggers(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Loggers)) {
            return false;
        }
        return id != null && id.equals(((Loggers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Loggers{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", logDetail='" + getLogDetail() + "'" +
            ", logDetailContentType='" + getLogDetailContentType() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            "}";
    }
}
