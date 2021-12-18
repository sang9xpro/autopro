package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LoggersValues.
 */
@Entity
@Table(name = "loggers_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoggersValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties(value = { "loggersValues" }, allowSetters = true)
    private Loggers loggers;

    @ManyToOne
    @JsonIgnoreProperties(value = { "loggersValues" }, allowSetters = true)
    private LoggersFields loggersFields;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoggersValues id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public LoggersValues value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Loggers getLoggers() {
        return this.loggers;
    }

    public void setLoggers(Loggers loggers) {
        this.loggers = loggers;
    }

    public LoggersValues loggers(Loggers loggers) {
        this.setLoggers(loggers);
        return this;
    }

    public LoggersFields getLoggersFields() {
        return this.loggersFields;
    }

    public void setLoggersFields(LoggersFields loggersFields) {
        this.loggersFields = loggersFields;
    }

    public LoggersValues loggersFields(LoggersFields loggersFields) {
        this.setLoggersFields(loggersFields);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoggersValues)) {
            return false;
        }
        return id != null && id.equals(((LoggersValues) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoggersValues{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
