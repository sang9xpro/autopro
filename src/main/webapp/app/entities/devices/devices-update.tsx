import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './devices.reducer';
import { IDevices } from 'app/shared/model/devices.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { DeviceType } from 'app/shared/model/enumerations/device-type.model';
import { DeviceStatus } from 'app/shared/model/enumerations/device-status.model';

export const DevicesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const devicesEntity = useAppSelector(state => state.devices.entity);
  const loading = useAppSelector(state => state.devices.loading);
  const updating = useAppSelector(state => state.devices.updating);
  const updateSuccess = useAppSelector(state => state.devices.updateSuccess);
  const deviceTypeValues = Object.keys(DeviceType);
  const deviceStatusValues = Object.keys(DeviceStatus);
  const handleClose = () => {
    props.history.push('/devices');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.lastUpdate = convertDateTimeToServer(values.lastUpdate);

    const entity = {
      ...devicesEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          lastUpdate: displayDefaultDateTime(),
        }
      : {
          deviceType: 'MOBILE',
          status: 'Online',
          ...devicesEntity,
          lastUpdate: convertDateTimeFromServer(devicesEntity.lastUpdate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.devices.home.createOrEditLabel" data-cy="DevicesCreateUpdateHeading">
            <Translate contentKey="autoproApp.devices.home.createOrEditLabel">Create or edit a Devices</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="devices-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.devices.ipAddress')}
                id="devices-ipAddress"
                name="ipAddress"
                data-cy="ipAddress"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.devices.macAddress')}
                id="devices-macAddress"
                name="macAddress"
                data-cy="macAddress"
                type="text"
              />
              <ValidatedField label={translate('autoproApp.devices.os')} id="devices-os" name="os" data-cy="os" type="text" />
              <ValidatedField
                label={translate('autoproApp.devices.deviceType')}
                id="devices-deviceType"
                name="deviceType"
                data-cy="deviceType"
                type="select"
              >
                {deviceTypeValues.map(deviceType => (
                  <option value={deviceType} key={deviceType}>
                    {translate('autoproApp.DeviceType.' + deviceType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('autoproApp.devices.status')}
                id="devices-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {deviceStatusValues.map(deviceStatus => (
                  <option value={deviceStatus} key={deviceStatus}>
                    {translate('autoproApp.DeviceStatus.' + deviceStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('autoproApp.devices.lastUpdate')}
                id="devices-lastUpdate"
                name="lastUpdate"
                data-cy="lastUpdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('autoproApp.devices.owner')} id="devices-owner" name="owner" data-cy="owner" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/devices" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default DevicesUpdate;
