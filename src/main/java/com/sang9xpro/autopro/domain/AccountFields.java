package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AccountFields.
 */
@Entity
@Table(name = "account_fields")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountFields implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @OneToMany(mappedBy = "accountFields")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accounts", "accountFields" }, allowSetters = true)
    private Set<AccountValues> accountValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AccountFields id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public AccountFields fieldName(String fieldName) {
        this.setFieldName(fieldName);
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Set<AccountValues> getAccountValues() {
        return this.accountValues;
    }

    public void setAccountValues(Set<AccountValues> accountValues) {
        if (this.accountValues != null) {
            this.accountValues.forEach(i -> i.setAccountFields(null));
        }
        if (accountValues != null) {
            accountValues.forEach(i -> i.setAccountFields(this));
        }
        this.accountValues = accountValues;
    }

    public AccountFields accountValues(Set<AccountValues> accountValues) {
        this.setAccountValues(accountValues);
        return this;
    }

    public AccountFields addAccountValues(AccountValues accountValues) {
        this.accountValues.add(accountValues);
        accountValues.setAccountFields(this);
        return this;
    }

    public AccountFields removeAccountValues(AccountValues accountValues) {
        this.accountValues.remove(accountValues);
        accountValues.setAccountFields(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountFields)) {
            return false;
        }
        return id != null && id.equals(((AccountFields) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountFields{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            "}";
    }
}
