package com.sang9xpro.autopro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sang9xpro.autopro.domain.enumeration.DeviceStatus;
import com.sang9xpro.autopro.domain.enumeration.DeviceType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Devices.
 */
@Entity
@Table(name = "devices")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Devices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "os")
    private String os;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type")
    private DeviceType deviceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeviceStatus status;

    @Column(name = "last_update")
    private Instant lastUpdate;

    @Column(name = "owner")
    private String owner;

    @OneToMany(mappedBy = "devices")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "devices", "devicesFields" }, allowSetters = true)
    private Set<DeviceValues> deviceValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Devices id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public Devices ipAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public Devices macAddress(String macAddress) {
        this.setMacAddress(macAddress);
        return this;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getOs() {
        return this.os;
    }

    public Devices os(String os) {
        this.setOs(os);
        return this;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    public Devices deviceType(DeviceType deviceType) {
        this.setDeviceType(deviceType);
        return this;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public DeviceStatus getStatus() {
        return this.status;
    }

    public Devices status(DeviceStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public Instant getLastUpdate() {
        return this.lastUpdate;
    }

    public Devices lastUpdate(Instant lastUpdate) {
        this.setLastUpdate(lastUpdate);
        return this;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getOwner() {
        return this.owner;
    }

    public Devices owner(String owner) {
        this.setOwner(owner);
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Set<DeviceValues> getDeviceValues() {
        return this.deviceValues;
    }

    public void setDeviceValues(Set<DeviceValues> deviceValues) {
        if (this.deviceValues != null) {
            this.deviceValues.forEach(i -> i.setDevices(null));
        }
        if (deviceValues != null) {
            deviceValues.forEach(i -> i.setDevices(this));
        }
        this.deviceValues = deviceValues;
    }

    public Devices deviceValues(Set<DeviceValues> deviceValues) {
        this.setDeviceValues(deviceValues);
        return this;
    }

    public Devices addDeviceValues(DeviceValues deviceValues) {
        this.deviceValues.add(deviceValues);
        deviceValues.setDevices(this);
        return this;
    }

    public Devices removeDeviceValues(DeviceValues deviceValues) {
        this.deviceValues.remove(deviceValues);
        deviceValues.setDevices(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Devices)) {
            return false;
        }
        return id != null && id.equals(((Devices) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Devices{" +
            "id=" + getId() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", macAddress='" + getMacAddress() + "'" +
            ", os='" + getOs() + "'" +
            ", deviceType='" + getDeviceType() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", owner='" + getOwner() + "'" +
            "}";
    }
}
