package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.DeviceValues;
import com.sang9xpro.autopro.repository.DeviceValuesRepository;
import com.sang9xpro.autopro.service.DeviceValuesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DeviceValues}.
 */
@Service
@Transactional
public class DeviceValuesServiceImpl implements DeviceValuesService {

    private final Logger log = LoggerFactory.getLogger(DeviceValuesServiceImpl.class);

    private final DeviceValuesRepository deviceValuesRepository;

    public DeviceValuesServiceImpl(DeviceValuesRepository deviceValuesRepository) {
        this.deviceValuesRepository = deviceValuesRepository;
    }

    @Override
    public DeviceValues save(DeviceValues deviceValues) {
        log.debug("Request to save DeviceValues : {}", deviceValues);
        return deviceValuesRepository.save(deviceValues);
    }

    @Override
    public Optional<DeviceValues> partialUpdate(DeviceValues deviceValues) {
        log.debug("Request to partially update DeviceValues : {}", deviceValues);

        return deviceValuesRepository
            .findById(deviceValues.getId())
            .map(existingDeviceValues -> {
                if (deviceValues.getValue() != null) {
                    existingDeviceValues.setValue(deviceValues.getValue());
                }

                return existingDeviceValues;
            })
            .map(deviceValuesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceValues> findAll() {
        log.debug("Request to get all DeviceValues");
        return deviceValuesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceValues> findOne(Long id) {
        log.debug("Request to get DeviceValues : {}", id);
        return deviceValuesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceValues : {}", id);
        deviceValuesRepository.deleteById(id);
    }
}
