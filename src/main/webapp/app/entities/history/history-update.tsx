import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './history.reducer';
import { IHistory } from 'app/shared/model/history.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const HistoryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const historyEntity = useAppSelector(state => state.history.entity);
  const loading = useAppSelector(state => state.history.loading);
  const updating = useAppSelector(state => state.history.updating);
  const updateSuccess = useAppSelector(state => state.history.updateSuccess);
  const handleClose = () => {
    props.history.push('/history');
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
      ...historyEntity,
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
          ...historyEntity,
          lastUpdate: convertDateTimeFromServer(historyEntity.lastUpdate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.history.home.createOrEditLabel" data-cy="HistoryCreateUpdateHeading">
            <Translate contentKey="autoproApp.history.home.createOrEditLabel">Create or edit a History</Translate>
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
                  id="history-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('autoproApp.history.url')} id="history-url" name="url" data-cy="url" type="text" />
              <ValidatedField
                label={translate('autoproApp.history.taskId')}
                id="history-taskId"
                name="taskId"
                data-cy="taskId"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.history.deviceId')}
                id="history-deviceId"
                name="deviceId"
                data-cy="deviceId"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.history.accountId')}
                id="history-accountId"
                name="accountId"
                data-cy="accountId"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.history.lastUpdate')}
                id="history-lastUpdate"
                name="lastUpdate"
                data-cy="lastUpdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/history" replace color="info">
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

export default HistoryUpdate;
