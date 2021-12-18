package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Accounts.
 */
@Entity
@Table(name = "accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "url_login")
    private String urlLogin;

    @Column(name = "profile_firefox")
    private String profileFirefox;

    @Column(name = "profile_chrome")
    private String profileChrome;

    @Column(name = "last_update")
    private Instant lastUpdate;

    @Column(name = "owner")
    private String owner;

    @Min(value = 1)
    @Max(value = 1)
    @Column(name = "actived")
    private Integer actived;

    @OneToMany(mappedBy = "accounts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accounts", "accountFields" }, allowSetters = true)
    private Set<AccountValues> accountValues = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "applicationsValues", "accounts", "tasks", "comments" }, allowSetters = true)
    private Applications applications;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Accounts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public Accounts userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public Accounts password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlLogin() {
        return this.urlLogin;
    }

    public Accounts urlLogin(String urlLogin) {
        this.setUrlLogin(urlLogin);
        return this;
    }

    public void setUrlLogin(String urlLogin) {
        this.urlLogin = urlLogin;
    }

    public String getProfileFirefox() {
        return this.profileFirefox;
    }

    public Accounts profileFirefox(String profileFirefox) {
        this.setProfileFirefox(profileFirefox);
        return this;
    }

    public void setProfileFirefox(String profileFirefox) {
        this.profileFirefox = profileFirefox;
    }

    public String getProfileChrome() {
        return this.profileChrome;
    }

    public Accounts profileChrome(String profileChrome) {
        this.setProfileChrome(profileChrome);
        return this;
    }

    public void setProfileChrome(String profileChrome) {
        this.profileChrome = profileChrome;
    }

    public Instant getLastUpdate() {
        return this.lastUpdate;
    }

    public Accounts lastUpdate(Instant lastUpdate) {
        this.setLastUpdate(lastUpdate);
        return this;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getOwner() {
        return this.owner;
    }

    public Accounts owner(String owner) {
        this.setOwner(owner);
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getActived() {
        return this.actived;
    }

    public Accounts actived(Integer actived) {
        this.setActived(actived);
        return this;
    }

    public void setActived(Integer actived) {
        this.actived = actived;
    }

    public Set<AccountValues> getAccountValues() {
        return this.accountValues;
    }

    public void setAccountValues(Set<AccountValues> accountValues) {
        if (this.accountValues != null) {
            this.accountValues.forEach(i -> i.setAccounts(null));
        }
        if (accountValues != null) {
            accountValues.forEach(i -> i.setAccounts(this));
        }
        this.accountValues = accountValues;
    }

    public Accounts accountValues(Set<AccountValues> accountValues) {
        this.setAccountValues(accountValues);
        return this;
    }

    public Accounts addAccountValues(AccountValues accountValues) {
        this.accountValues.add(accountValues);
        accountValues.setAccounts(this);
        return this;
    }

    public Accounts removeAccountValues(AccountValues accountValues) {
        this.accountValues.remove(accountValues);
        accountValues.setAccounts(null);
        return this;
    }

    public Applications getApplications() {
        return this.applications;
    }

    public void setApplications(Applications applications) {
        this.applications = applications;
    }

    public Accounts applications(Applications applications) {
        this.setApplications(applications);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accounts)) {
            return false;
        }
        return id != null && id.equals(((Accounts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accounts{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", urlLogin='" + getUrlLogin() + "'" +
            ", profileFirefox='" + getProfileFirefox() + "'" +
            ", profileChrome='" + getProfileChrome() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", owner='" + getOwner() + "'" +
            ", actived=" + getActived() +
            "}";
    }
}
