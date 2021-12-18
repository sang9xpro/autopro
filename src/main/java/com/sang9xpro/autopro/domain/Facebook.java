package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sang9xpro.autopro.domain.enumeration.FbType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Facebook.
 */
@Entity
@Table(name = "facebook")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Facebook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "id_on_fb")
    private String idOnFb;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FbType type;

    @OneToMany(mappedBy = "facebook")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "facebook", "facebookFields" }, allowSetters = true)
    private Set<FacebookValues> facebookValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Facebook id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Facebook name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public Facebook url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdOnFb() {
        return this.idOnFb;
    }

    public Facebook idOnFb(String idOnFb) {
        this.setIdOnFb(idOnFb);
        return this;
    }

    public void setIdOnFb(String idOnFb) {
        this.idOnFb = idOnFb;
    }

    public FbType getType() {
        return this.type;
    }

    public Facebook type(FbType type) {
        this.setType(type);
        return this;
    }

    public void setType(FbType type) {
        this.type = type;
    }

    public Set<FacebookValues> getFacebookValues() {
        return this.facebookValues;
    }

    public void setFacebookValues(Set<FacebookValues> facebookValues) {
        if (this.facebookValues != null) {
            this.facebookValues.forEach(i -> i.setFacebook(null));
        }
        if (facebookValues != null) {
            facebookValues.forEach(i -> i.setFacebook(this));
        }
        this.facebookValues = facebookValues;
    }

    public Facebook facebookValues(Set<FacebookValues> facebookValues) {
        this.setFacebookValues(facebookValues);
        return this;
    }

    public Facebook addFacebookValues(FacebookValues facebookValues) {
        this.facebookValues.add(facebookValues);
        facebookValues.setFacebook(this);
        return this;
    }

    public Facebook removeFacebookValues(FacebookValues facebookValues) {
        this.facebookValues.remove(facebookValues);
        facebookValues.setFacebook(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facebook)) {
            return false;
        }
        return id != null && id.equals(((Facebook) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facebook{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", url='" + getUrl() + "'" +
            ", idOnFb='" + getIdOnFb() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
