import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplications } from 'app/shared/model/applications.model';
import { getEntities as getApplications } from 'app/entities/applications/applications.reducer';
import { getEntity, updateEntity, createEntity, reset } from './tasks.reducer';
import { ITasks } from 'app/shared/model/tasks.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { TaskType } from 'app/shared/model/enumerations/task-type.model';

export const TasksUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applications = useAppSelector(state => state.applications.entities);
  const tasksEntity = useAppSelector(state => state.tasks.entity);
  const loading = useAppSelector(state => state.tasks.loading);
  const updating = useAppSelector(state => state.tasks.updating);
  const updateSuccess = useAppSelector(state => state.tasks.updateSuccess);
  const taskTypeValues = Object.keys(TaskType);
  const handleClose = () => {
    props.history.push('/tasks');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getApplications({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tasksEntity,
      ...values,
      applications: applications.find(it => it.id.toString() === values.applications.toString()),
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
          type: 'Stream',
          ...tasksEntity,
          applications: tasksEntity?.applications?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.tasks.home.createOrEditLabel" data-cy="TasksCreateUpdateHeading">
            <Translate contentKey="autoproApp.tasks.home.createOrEditLabel">Create or edit a Tasks</Translate>
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
                  id="tasks-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.tasks.taskName')}
                id="tasks-taskName"
                name="taskName"
                data-cy="taskName"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.tasks.description')}
                id="tasks-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('autoproApp.tasks.source')} id="tasks-source" name="source" data-cy="source" type="text" />
              <ValidatedField label={translate('autoproApp.tasks.price')} id="tasks-price" name="price" data-cy="price" type="text" />
              <ValidatedField label={translate('autoproApp.tasks.type')} id="tasks-type" name="type" data-cy="type" type="select">
                {taskTypeValues.map(taskType => (
                  <option value={taskType} key={taskType}>
                    {translate('autoproApp.TaskType.' + taskType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="tasks-applications"
                name="applications"
                data-cy="applications"
                label={translate('autoproApp.tasks.applications')}
                type="select"
              >
                <option value="" key="0" />
                {applications
                  ? applications.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tasks" replace color="info">
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

export default TasksUpdate;
