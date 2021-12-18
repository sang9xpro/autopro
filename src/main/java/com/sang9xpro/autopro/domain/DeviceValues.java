package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DeviceValues.
 */
@Entity
@Table(name = "device_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties(value = { "deviceValues" }, allowSetters = true)
    private Devices devices;

    @ManyToOne
    @JsonIgnoreProperties(value = { "deviceValues" }, allowSetters = true)
    private DevicesFields devicesFields;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DeviceValues id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public DeviceValues value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Devices getDevices() {
        return this.devices;
    }

    public void setDevices(Devices devices) {
        this.devices = devices;
    }

    public DeviceValues devices(Devices devices) {
        this.setDevices(devices);
        return this;
    }

    public DevicesFields getDevicesFields() {
        return this.devicesFields;
    }

    public void setDevicesFields(DevicesFields devicesFields) {
        this.devicesFields = devicesFields;
    }

    public DeviceValues devicesFields(DevicesFields devicesFields) {
        this.setDevicesFields(devicesFields);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceValues)) {
            return false;
        }
        return id != null && id.equals(((DeviceValues) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceValues{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
