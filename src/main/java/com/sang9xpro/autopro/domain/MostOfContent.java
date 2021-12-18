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
 * A MostOfContent.
 */
@Entity
@Table(name = "most_of_content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MostOfContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url_original")
    private String urlOriginal;

    @Column(name = "content_text")
    private String contentText;

    @Column(name = "post_time")
    private Instant postTime;

    @Column(name = "number_like")
    private Integer numberLike;

    @Column(name = "number_comment")
    private Integer numberComment;

    @OneToMany(mappedBy = "mostOfContent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mostOfContent", "mostOfContFields" }, allowSetters = true)
    private Set<MostOfContValues> mostOfContValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MostOfContent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlOriginal() {
        return this.urlOriginal;
    }

    public MostOfContent urlOriginal(String urlOriginal) {
        this.setUrlOriginal(urlOriginal);
        return this;
    }

    public void setUrlOriginal(String urlOriginal) {
        this.urlOriginal = urlOriginal;
    }

    public String getContentText() {
        return this.contentText;
    }

    public MostOfContent contentText(String contentText) {
        this.setContentText(contentText);
        return this;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public Instant getPostTime() {
        return this.postTime;
    }

    public MostOfContent postTime(Instant postTime) {
        this.setPostTime(postTime);
        return this;
    }

    public void setPostTime(Instant postTime) {
        this.postTime = postTime;
    }

    public Integer getNumberLike() {
        return this.numberLike;
    }

    public MostOfContent numberLike(Integer numberLike) {
        this.setNumberLike(numberLike);
        return this;
    }

    public void setNumberLike(Integer numberLike) {
        this.numberLike = numberLike;
    }

    public Integer getNumberComment() {
        return this.numberComment;
    }

    public MostOfContent numberComment(Integer numberComment) {
        this.setNumberComment(numberComment);
        return this;
    }

    public void setNumberComment(Integer numberComment) {
        this.numberComment = numberComment;
    }

    public Set<MostOfContValues> getMostOfContValues() {
        return this.mostOfContValues;
    }

    public void setMostOfContValues(Set<MostOfContValues> mostOfContValues) {
        if (this.mostOfContValues != null) {
            this.mostOfContValues.forEach(i -> i.setMostOfContent(null));
        }
        if (mostOfContValues != null) {
            mostOfContValues.forEach(i -> i.setMostOfContent(this));
        }
        this.mostOfContValues = mostOfContValues;
    }

    public MostOfContent mostOfContValues(Set<MostOfContValues> mostOfContValues) {
        this.setMostOfContValues(mostOfContValues);
        return this;
    }

    public MostOfContent addMostOfContValues(MostOfContValues mostOfContValues) {
        this.mostOfContValues.add(mostOfContValues);
        mostOfContValues.setMostOfContent(this);
        return this;
    }

    public MostOfContent removeMostOfContValues(MostOfContValues mostOfContValues) {
        this.mostOfContValues.remove(mostOfContValues);
        mostOfContValues.setMostOfContent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MostOfContent)) {
            return false;
        }
        return id != null && id.equals(((MostOfContent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MostOfContent{" +
            "id=" + getId() +
            ", urlOriginal='" + getUrlOriginal() + "'" +
            ", contentText='" + getContentText() + "'" +
            ", postTime='" + getPostTime() + "'" +
            ", numberLike=" + getNumberLike() +
            ", numberComment=" + getNumberComment() +
            "}";
    }
}
