package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CommentsValues.
 */
@Entity
@Table(name = "comments_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommentsValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties(value = { "commentsValues", "applications" }, allowSetters = true)
    private Comments comments;

    @ManyToOne
    @JsonIgnoreProperties(value = { "commentsValues" }, allowSetters = true)
    private CommentsFields commentsFields;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CommentsValues id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public CommentsValues value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Comments getComments() {
        return this.comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public CommentsValues comments(Comments comments) {
        this.setComments(comments);
        return this;
    }

    public CommentsFields getCommentsFields() {
        return this.commentsFields;
    }

    public void setCommentsFields(CommentsFields commentsFields) {
        this.commentsFields = commentsFields;
    }

    public CommentsValues commentsFields(CommentsFields commentsFields) {
        this.setCommentsFields(commentsFields);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentsValues)) {
            return false;
        }
        return id != null && id.equals(((CommentsValues) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentsValues{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
