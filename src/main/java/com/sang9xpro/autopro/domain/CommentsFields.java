package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CommentsFields.
 */
@Entity
@Table(name = "comments_fields")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommentsFields implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @OneToMany(mappedBy = "commentsFields")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "commentsFields" }, allowSetters = true)
    private Set<CommentsValues> commentsValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CommentsFields id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public CommentsFields fieldName(String fieldName) {
        this.setFieldName(fieldName);
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Set<CommentsValues> getCommentsValues() {
        return this.commentsValues;
    }

    public void setCommentsValues(Set<CommentsValues> commentsValues) {
        if (this.commentsValues != null) {
            this.commentsValues.forEach(i -> i.setCommentsFields(null));
        }
        if (commentsValues != null) {
            commentsValues.forEach(i -> i.setCommentsFields(this));
        }
        this.commentsValues = commentsValues;
    }

    public CommentsFields commentsValues(Set<CommentsValues> commentsValues) {
        this.setCommentsValues(commentsValues);
        return this;
    }

    public CommentsFields addCommentsValues(CommentsValues commentsValues) {
        this.commentsValues.add(commentsValues);
        commentsValues.setCommentsFields(this);
        return this;
    }

    public CommentsFields removeCommentsValues(CommentsValues commentsValues) {
        this.commentsValues.remove(commentsValues);
        commentsValues.setCommentsFields(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentsFields)) {
            return false;
        }
        return id != null && id.equals(((CommentsFields) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentsFields{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            "}";
    }
}
