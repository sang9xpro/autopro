import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './loggers.reducer';
import { ILoggers } from 'app/shared/model/loggers.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LoggersUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const loggersEntity = useAppSelector(state => state.loggers.entity);
  const loading = useAppSelector(state => state.loggers.loading);
  const updating = useAppSelector(state => state.loggers.updating);
  const updateSuccess = useAppSelector(state => state.loggers.updateSuccess);
  const handleClose = () => {
    props.history.push('/loggers');
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
      ...loggersEntity,
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
          ...loggersEntity,
          lastUpdate: convertDateTimeFromServer(loggersEntity.lastUpdate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.loggers.home.createOrEditLabel" data-cy="LoggersCreateUpdateHeading">
            <Translate contentKey="autoproApp.loggers.home.createOrEditLabel">Create or edit a Loggers</Translate>
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
                  id="loggers-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.loggers.status')}
                id="loggers-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedBlobField
                label={translate('autoproApp.loggers.logDetail')}
                id="loggers-logDetail"
                name="logDetail"
                data-cy="logDetail"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('autoproApp.loggers.lastUpdate')}
                id="loggers-lastUpdate"
                name="lastUpdate"
                data-cy="lastUpdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/loggers" replace color="info">
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

export default LoggersUpdate;
