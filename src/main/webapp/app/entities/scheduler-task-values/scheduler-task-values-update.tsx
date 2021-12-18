import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ISchedulerTask } from 'app/shared/model/scheduler-task.model';
import { getEntities as getSchedulerTasks } from 'app/entities/scheduler-task/scheduler-task.reducer';
import { ISchedulerTaskFields } from 'app/shared/model/scheduler-task-fields.model';
import { getEntities as getSchedulerTaskFields } from 'app/entities/scheduler-task-fields/scheduler-task-fields.reducer';
import { getEntity, updateEntity, createEntity, reset } from './scheduler-task-values.reducer';
import { ISchedulerTaskValues } from 'app/shared/model/scheduler-task-values.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SchedulerTaskValuesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const schedulerTasks = useAppSelector(state => state.schedulerTask.entities);
  const schedulerTaskFields = useAppSelector(state => state.schedulerTaskFields.entities);
  const schedulerTaskValuesEntity = useAppSelector(state => state.schedulerTaskValues.entity);
  const loading = useAppSelector(state => state.schedulerTaskValues.loading);
  const updating = useAppSelector(state => state.schedulerTaskValues.updating);
  const updateSuccess = useAppSelector(state => state.schedulerTaskValues.updateSuccess);
  const handleClose = () => {
    props.history.push('/scheduler-task-values');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getSchedulerTasks({}));
    dispatch(getSchedulerTaskFields({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...schedulerTaskValuesEntity,
      ...values,
      schedulerTask: schedulerTasks.find(it => it.id.toString() === values.schedulerTask.toString()),
      schedulerTaskFields: schedulerTaskFields.find(it => it.id.toString() === values.schedulerTaskFields.toString()),
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
          ...schedulerTaskValuesEntity,
          schedulerTask: schedulerTaskValuesEntity?.schedulerTask?.id,
          schedulerTaskFields: schedulerTaskValuesEntity?.schedulerTaskFields?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.schedulerTaskValues.home.createOrEditLabel" data-cy="SchedulerTaskValuesCreateUpdateHeading">
            <Translate contentKey="autoproApp.schedulerTaskValues.home.createOrEditLabel">Create or edit a SchedulerTaskValues</Translate>
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
                  id="scheduler-task-values-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.schedulerTaskValues.value')}
                id="scheduler-task-values-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                id="scheduler-task-values-schedulerTask"
                name="schedulerTask"
                data-cy="schedulerTask"
                label={translate('autoproApp.schedulerTaskValues.schedulerTask')}
                type="select"
              >
                <option value="" key="0" />
                {schedulerTasks
                  ? schedulerTasks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="scheduler-task-values-schedulerTaskFields"
                name="schedulerTaskFields"
                data-cy="schedulerTaskFields"
                label={translate('autoproApp.schedulerTaskValues.schedulerTaskFields')}
                type="select"
              >
                <option value="" key="0" />
                {schedulerTaskFields
                  ? schedulerTaskFields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/scheduler-task-values" replace color="info">
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

export default SchedulerTaskValuesUpdate;
