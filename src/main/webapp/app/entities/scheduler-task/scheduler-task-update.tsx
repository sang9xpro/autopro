import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './scheduler-task.reducer';
import { ISchedulerTask } from 'app/shared/model/scheduler-task.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { SchedulerStatus } from 'app/shared/model/enumerations/scheduler-status.model';

export const SchedulerTaskUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const schedulerTaskEntity = useAppSelector(state => state.schedulerTask.entity);
  const loading = useAppSelector(state => state.schedulerTask.loading);
  const updating = useAppSelector(state => state.schedulerTask.updating);
  const updateSuccess = useAppSelector(state => state.schedulerTask.updateSuccess);
  const schedulerStatusValues = Object.keys(SchedulerStatus);
  const handleClose = () => {
    props.history.push('/scheduler-task');
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
    values.startTime = convertDateTimeToServer(values.startTime);
    values.endTime = convertDateTimeToServer(values.endTime);
    values.lastUpdate = convertDateTimeToServer(values.lastUpdate);

    const entity = {
      ...schedulerTaskEntity,
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
          startTime: displayDefaultDateTime(),
          endTime: displayDefaultDateTime(),
          lastUpdate: displayDefaultDateTime(),
        }
      : {
          status: 'Open',
          ...schedulerTaskEntity,
          startTime: convertDateTimeFromServer(schedulerTaskEntity.startTime),
          endTime: convertDateTimeFromServer(schedulerTaskEntity.endTime),
          lastUpdate: convertDateTimeFromServer(schedulerTaskEntity.lastUpdate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.schedulerTask.home.createOrEditLabel" data-cy="SchedulerTaskCreateUpdateHeading">
            <Translate contentKey="autoproApp.schedulerTask.home.createOrEditLabel">Create or edit a SchedulerTask</Translate>
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
                  id="scheduler-task-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.schedulerTask.startTime')}
                id="scheduler-task-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('autoproApp.schedulerTask.endTime')}
                id="scheduler-task-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('autoproApp.schedulerTask.countFrom')}
                id="scheduler-task-countFrom"
                name="countFrom"
                data-cy="countFrom"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.schedulerTask.countTo')}
                id="scheduler-task-countTo"
                name="countTo"
                data-cy="countTo"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.schedulerTask.point')}
                id="scheduler-task-point"
                name="point"
                data-cy="point"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.schedulerTask.lastUpdate')}
                id="scheduler-task-lastUpdate"
                name="lastUpdate"
                data-cy="lastUpdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('autoproApp.schedulerTask.owner')}
                id="scheduler-task-owner"
                name="owner"
                data-cy="owner"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.schedulerTask.status')}
                id="scheduler-task-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {schedulerStatusValues.map(schedulerStatus => (
                  <option value={schedulerStatus} key={schedulerStatus}>
                    {translate('autoproApp.SchedulerStatus.' + schedulerStatus)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/scheduler-task" replace color="info">
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

export default SchedulerTaskUpdate;
