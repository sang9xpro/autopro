package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.DevicesFields;
import com.sang9xpro.autopro.repository.DevicesFieldsRepository;
import com.sang9xpro.autopro.service.DevicesFieldsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DevicesFields}.
 */
@Service
@Transactional
public class DevicesFieldsServiceImpl implements DevicesFieldsService {

    private final Logger log = LoggerFactory.getLogger(DevicesFieldsServiceImpl.class);

    private final DevicesFieldsRepository devicesFieldsRepository;

    public DevicesFieldsServiceImpl(DevicesFieldsRepository devicesFieldsRepository) {
        this.devicesFieldsRepository = devicesFieldsRepository;
    }

    @Override
    public DevicesFields save(DevicesFields devicesFields) {
        log.debug("Request to save DevicesFields : {}", devicesFields);
        return devicesFieldsRepository.save(devicesFields);
    }

    @Override
    public Optional<DevicesFields> partialUpdate(DevicesFields devicesFields) {
        log.debug("Request to partially update DevicesFields : {}", devicesFields);

        return devicesFieldsRepository
            .findById(devicesFields.getId())
            .map(existingDevicesFields -> {
                if (devicesFields.getFieldsName() != null) {
                    existingDevicesFields.setFieldsName(devicesFields.getFieldsName());
                }

                return existingDevicesFields;
            })
            .map(devicesFieldsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DevicesFields> findAll() {
        log.debug("Request to get all DevicesFields");
        return devicesFieldsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DevicesFields> findOne(Long id) {
        log.debug("Request to get DevicesFields : {}", id);
        return devicesFieldsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DevicesFields : {}", id);
        devicesFieldsRepository.deleteById(id);
    }
}
