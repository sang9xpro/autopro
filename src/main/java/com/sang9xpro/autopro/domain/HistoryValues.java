package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HistoryValues.
 */
@Entity
@Table(name = "history_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HistoryValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties(value = { "historyValues" }, allowSetters = true)
    private History history;

    @ManyToOne
    @JsonIgnoreProperties(value = { "historyValues" }, allowSetters = true)
    private HistoryFields historyFields;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HistoryValues id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public HistoryValues value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public History getHistory() {
        return this.history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public HistoryValues history(History history) {
        this.setHistory(history);
        return this;
    }

    public HistoryFields getHistoryFields() {
        return this.historyFields;
    }

    public void setHistoryFields(HistoryFields historyFields) {
        this.historyFields = historyFields;
    }

    public HistoryValues historyFields(HistoryFields historyFields) {
        this.setHistoryFields(historyFields);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoryValues)) {
            return false;
        }
        return id != null && id.equals(((HistoryValues) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistoryValues{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
