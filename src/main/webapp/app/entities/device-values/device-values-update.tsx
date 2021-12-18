import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDevices } from 'app/shared/model/devices.model';
import { getEntities as getDevices } from 'app/entities/devices/devices.reducer';
import { IDevicesFields } from 'app/shared/model/devices-fields.model';
import { getEntities as getDevicesFields } from 'app/entities/devices-fields/devices-fields.reducer';
import { getEntity, updateEntity, createEntity, reset } from './device-values.reducer';
import { IDeviceValues } from 'app/shared/model/device-values.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DeviceValuesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const devices = useAppSelector(state => state.devices.entities);
  const devicesFields = useAppSelector(state => state.devicesFields.entities);
  const deviceValuesEntity = useAppSelector(state => state.deviceValues.entity);
  const loading = useAppSelector(state => state.deviceValues.loading);
  const updating = useAppSelector(state => state.deviceValues.updating);
  const updateSuccess = useAppSelector(state => state.deviceValues.updateSuccess);
  const handleClose = () => {
    props.history.push('/device-values');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDevices({}));
    dispatch(getDevicesFields({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...deviceValuesEntity,
      ...values,
      devices: devices.find(it => it.id.toString() === values.devices.toString()),
      devicesFields: devicesFields.find(it => it.id.toString() === values.devicesFields.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...deviceValuesEntity,
          devices: deviceValuesEntity?.devices?.id,
          devicesFields: deviceValuesEntity?.devicesFields?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.deviceValues.home.createOrEditLabel" data-cy="DeviceValuesCreateUpdateHeading">
            <Translate contentKey="autoproApp.deviceValues.home.createOrEditLabel">Create or edit a DeviceValues</Translate>
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
                  id="device-values-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.deviceValues.value')}
                id="device-values-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                id="device-values-devices"
                name="devices"
                data-cy="devices"
                label={translate('autoproApp.deviceValues.devices')}
                type="select"
              >
                <option value="" key="0" />
                {devices
                  ? devices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="device-values-devicesFields"
                name="devicesFields"
                data-cy="devicesFields"
                label={translate('autoproApp.deviceValues.devicesFields')}
                type="select"
              >
                <option value="" key="0" />
                {devicesFields
                  ? devicesFields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/device-values" replace color="info">
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

export default DeviceValuesUpdate;
