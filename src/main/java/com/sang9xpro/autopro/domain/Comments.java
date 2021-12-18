package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comments.
 */
@Entity
@Table(name = "comments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "owner")
    private String owner;

    @OneToMany(mappedBy = "comments")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "commentsFields" }, allowSetters = true)
    private Set<CommentsValues> commentsValues = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "applicationsValues", "accounts", "tasks", "comments" }, allowSetters = true)
    private Applications applications;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Comments id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public Comments content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Comments image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Comments imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getOwner() {
        return this.owner;
    }

    public Comments owner(String owner) {
        this.setOwner(owner);
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Set<CommentsValues> getCommentsValues() {
        return this.commentsValues;
    }

    public void setCommentsValues(Set<CommentsValues> commentsValues) {
        if (this.commentsValues != null) {
            this.commentsValues.forEach(i -> i.setComments(null));
        }
        if (commentsValues != null) {
            commentsValues.forEach(i -> i.setComments(this));
        }
        this.commentsValues = commentsValues;
    }

    public Comments commentsValues(Set<CommentsValues> commentsValues) {
        this.setCommentsValues(commentsValues);
        return this;
    }

    public Comments addCommentsValues(CommentsValues commentsValues) {
        this.commentsValues.add(commentsValues);
        commentsValues.setComments(this);
        return this;
    }

    public Comments removeCommentsValues(CommentsValues commentsValues) {
        this.commentsValues.remove(commentsValues);
        commentsValues.setComments(null);
        return this;
    }

    public Applications getApplications() {
        return this.applications;
    }

    public void setApplications(Applications applications) {
        this.applications = applications;
    }

    public Comments applications(Applications applications) {
        this.setApplications(applications);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comments)) {
            return false;
        }
        return id != null && id.equals(((Comments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comments{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", owner='" + getOwner() + "'" +
            "}";
    }
}
