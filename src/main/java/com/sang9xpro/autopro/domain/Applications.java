package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Applications.
 */
@Entity
@Table(name = "applications")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Applications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "app_name")
    private String appName;

    @Column(name = "app_code")
    private String appCode;

    @OneToMany(mappedBy = "applications")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applications", "applicationsFields" }, allowSetters = true)
    private Set<ApplicationsValues> applicationsValues = new HashSet<>();

    @OneToMany(mappedBy = "applications")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accountValues", "applications" }, allowSetters = true)
    private Set<Accounts> accounts = new HashSet<>();

    @OneToMany(mappedBy = "applications")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "taskValues", "applications" }, allowSetters = true)
    private Set<Tasks> tasks = new HashSet<>();

    @OneToMany(mappedBy = "applications")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commentsValues", "applications" }, allowSetters = true)
    private Set<Comments> comments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Applications id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return this.appName;
    }

    public Applications appName(String appName) {
        this.setAppName(appName);
        return this;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public Applications appCode(String appCode) {
        this.setAppCode(appCode);
        return this;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Set<ApplicationsValues> getApplicationsValues() {
        return this.applicationsValues;
    }

    public void setApplicationsValues(Set<ApplicationsValues> applicationsValues) {
        if (this.applicationsValues != null) {
            this.applicationsValues.forEach(i -> i.setApplications(null));
        }
        if (applicationsValues != null) {
            applicationsValues.forEach(i -> i.setApplications(this));
        }
        this.applicationsValues = applicationsValues;
    }

    public Applications applicationsValues(Set<ApplicationsValues> applicationsValues) {
        this.setApplicationsValues(applicationsValues);
        return this;
    }

    public Applications addApplicationsValues(ApplicationsValues applicationsValues) {
        this.applicationsValues.add(applicationsValues);
        applicationsValues.setApplications(this);
        return this;
    }

    public Applications removeApplicationsValues(ApplicationsValues applicationsValues) {
        this.applicationsValues.remove(applicationsValues);
        applicationsValues.setApplications(null);
        return this;
    }

    public Set<Accounts> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Set<Accounts> accounts) {
        if (this.accounts != null) {
            this.accounts.forEach(i -> i.setApplications(null));
        }
        if (accounts != null) {
            accounts.forEach(i -> i.setApplications(this));
        }
        this.accounts = accounts;
    }

    public Applications accounts(Set<Accounts> accounts) {
        this.setAccounts(accounts);
        return this;
    }

    public Applications addAccounts(Accounts accounts) {
        this.accounts.add(accounts);
        accounts.setApplications(this);
        return this;
    }

    public Applications removeAccounts(Accounts accounts) {
        this.accounts.remove(accounts);
        accounts.setApplications(null);
        return this;
    }

    public Set<Tasks> getTasks() {
        return this.tasks;
    }

    public void setTasks(Set<Tasks> tasks) {
        if (this.tasks != null) {
            this.tasks.forEach(i -> i.setApplications(null));
        }
        if (tasks != null) {
            tasks.forEach(i -> i.setApplications(this));
        }
        this.tasks = tasks;
    }

    public Applications tasks(Set<Tasks> tasks) {
        this.setTasks(tasks);
        return this;
    }

    public Applications addTasks(Tasks tasks) {
        this.tasks.add(tasks);
        tasks.setApplications(this);
        return this;
    }

    public Applications removeTasks(Tasks tasks) {
        this.tasks.remove(tasks);
        tasks.setApplications(null);
        return this;
    }

    public Set<Comments> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comments> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setApplications(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setApplications(this));
        }
        this.comments = comments;
    }

    public Applications comments(Set<Comments> comments) {
        this.setComments(comments);
        return this;
    }

    public Applications addComments(Comments comments) {
        this.comments.add(comments);
        comments.setApplications(this);
        return this;
    }

    public Applications removeComments(Comments comments) {
        this.comments.remove(comments);
        comments.setApplications(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Applications)) {
            return false;
        }
        return id != null && id.equals(((Applications) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Applications{" +
            "id=" + getId() +
            ", appName='" + getAppName() + "'" +
            ", appCode='" + getAppCode() + "'" +
            "}";
    }
}
