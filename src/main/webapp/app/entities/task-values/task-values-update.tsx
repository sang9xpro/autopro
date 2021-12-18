import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ITasks } from 'app/shared/model/tasks.model';
import { getEntities as getTasks } from 'app/entities/tasks/tasks.reducer';
import { ITaskFields } from 'app/shared/model/task-fields.model';
import { getEntities as getTaskFields } from 'app/entities/task-fields/task-fields.reducer';
import { getEntity, updateEntity, createEntity, reset } from './task-values.reducer';
import { ITaskValues } from 'app/shared/model/task-values.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaskValuesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const tasks = useAppSelector(state => state.tasks.entities);
  const taskFields = useAppSelector(state => state.taskFields.entities);
  const taskValuesEntity = useAppSelector(state => state.taskValues.entity);
  const loading = useAppSelector(state => state.taskValues.loading);
  const updating = useAppSelector(state => state.taskValues.updating);
  const updateSuccess = useAppSelector(state => state.taskValues.updateSuccess);
  const handleClose = () => {
    props.history.push('/task-values');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTasks({}));
    dispatch(getTaskFields({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...taskValuesEntity,
      ...values,
      tasks: tasks.find(it => it.id.toString() === values.tasks.toString()),
      taskFields: taskFields.find(it => it.id.toString() === values.taskFields.toString()),
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
          ...taskValuesEntity,
          tasks: taskValuesEntity?.tasks?.id,
          taskFields: taskValuesEntity?.taskFields?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.taskValues.home.createOrEditLabel" data-cy="TaskValuesCreateUpdateHeading">
            <Translate contentKey="autoproApp.taskValues.home.createOrEditLabel">Create or edit a TaskValues</Translate>
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
                  id="task-values-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.taskValues.value')}
                id="task-values-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                id="task-values-tasks"
                name="tasks"
                data-cy="tasks"
                label={translate('autoproApp.taskValues.tasks')}
                type="select"
              >
                <option value="" key="0" />
                {tasks
                  ? tasks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="task-values-taskFields"
                name="taskFields"
                data-cy="taskFields"
                label={translate('autoproApp.taskValues.taskFields')}
                type="select"
              >
                <option value="" key="0" />
                {taskFields
                  ? taskFields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/task-values" replace color="info">
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

export default TaskValuesUpdate;
