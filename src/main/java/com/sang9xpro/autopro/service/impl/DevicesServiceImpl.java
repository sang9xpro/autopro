package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.Devices;
import com.sang9xpro.autopro.repository.DevicesRepository;
import com.sang9xpro.autopro.service.DevicesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Devices}.
 */
@Service
@Transactional
public class DevicesServiceImpl implements DevicesService {

    private final Logger log = LoggerFactory.getLogger(DevicesServiceImpl.class);

    private final DevicesRepository devicesRepository;

    public DevicesServiceImpl(DevicesRepository devicesRepository) {
        this.devicesRepository = devicesRepository;
    }

    @Override
    public Devices save(Devices devices) {
        log.debug("Request to save Devices : {}", devices);
        return devicesRepository.save(devices);
    }

    @Override
    public Optional<Devices> partialUpdate(Devices devices) {
        log.debug("Request to partially update Devices : {}", devices);

        return devicesRepository
            .findById(devices.getId())
            .map(existingDevices -> {
                if (devices.getIpAddress() != null) {
                    existingDevices.setIpAddress(devices.getIpAddress());
                }
                if (devices.getMacAddress() != null) {
                    existingDevices.setMacAddress(devices.getMacAddress());
                }
                if (devices.getOs() != null) {
                    existingDevices.setOs(devices.getOs());
                }
                if (devices.getDeviceType() != null) {
                    existingDevices.setDeviceType(devices.getDeviceType());
                }
                if (devices.getStatus() != null) {
                    existingDevices.setStatus(devices.getStatus());
                }
                if (devices.getLastUpdate() != null) {
                    existingDevices.setLastUpdate(devices.getLastUpdate());
                }
                if (devices.getOwner() != null) {
                    existingDevices.setOwner(devices.getOwner());
                }

                return existingDevices;
            })
            .map(devicesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Devices> findAll() {
        log.debug("Request to get all Devices");
        return devicesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Devices> findOne(Long id) {
        log.debug("Request to get Devices : {}", id);
        return devicesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Devices : {}", id);
        devicesRepository.deleteById(id);
    }
}
