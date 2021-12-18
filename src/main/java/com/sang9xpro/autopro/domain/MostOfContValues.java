package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MostOfContValues.
 */
@Entity
@Table(name = "most_of_cont_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MostOfContValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mostOfContValues" }, allowSetters = true)
    private MostOfContent mostOfContent;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mostOfContValues" }, allowSetters = true)
    private MostOfContFields mostOfContFields;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MostOfContValues id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public MostOfContValues value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MostOfContent getMostOfContent() {
        return this.mostOfContent;
    }

    public void setMostOfContent(MostOfContent mostOfContent) {
        this.mostOfContent = mostOfContent;
    }

    public MostOfContValues mostOfContent(MostOfContent mostOfContent) {
        this.setMostOfContent(mostOfContent);
        return this;
    }

    public MostOfContFields getMostOfContFields() {
        return this.mostOfContFields;
    }

    public void setMostOfContFields(MostOfContFields mostOfContFields) {
        this.mostOfContFields = mostOfContFields;
    }

    public MostOfContValues mostOfContFields(MostOfContFields mostOfContFields) {
        this.setMostOfContFields(mostOfContFields);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MostOfContValues)) {
            return false;
        }
        return id != null && id.equals(((MostOfContValues) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MostOfContValues{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
