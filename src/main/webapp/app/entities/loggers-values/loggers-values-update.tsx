import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ILoggers } from 'app/shared/model/loggers.model';
import { getEntities as getLoggers } from 'app/entities/loggers/loggers.reducer';
import { ILoggersFields } from 'app/shared/model/loggers-fields.model';
import { getEntities as getLoggersFields } from 'app/entities/loggers-fields/loggers-fields.reducer';
import { getEntity, updateEntity, createEntity, reset } from './loggers-values.reducer';
import { ILoggersValues } from 'app/shared/model/loggers-values.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LoggersValuesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const loggers = useAppSelector(state => state.loggers.entities);
  const loggersFields = useAppSelector(state => state.loggersFields.entities);
  const loggersValuesEntity = useAppSelector(state => state.loggersValues.entity);
  const loading = useAppSelector(state => state.loggersValues.loading);
  const updating = useAppSelector(state => state.loggersValues.updating);
  const updateSuccess = useAppSelector(state => state.loggersValues.updateSuccess);
  const handleClose = () => {
    props.history.push('/loggers-values');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getLoggers({}));
    dispatch(getLoggersFields({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...loggersValuesEntity,
      ...values,
      loggers: loggers.find(it => it.id.toString() === values.loggers.toString()),
      loggersFields: loggersFields.find(it => it.id.toString() === values.loggersFields.toString()),
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
          ...loggersValuesEntity,
          loggers: loggersValuesEntity?.loggers?.id,
          loggersFields: loggersValuesEntity?.loggersFields?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.loggersValues.home.createOrEditLabel" data-cy="LoggersValuesCreateUpdateHeading">
            <Translate contentKey="autoproApp.loggersValues.home.createOrEditLabel">Create or edit a LoggersValues</Translate>
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
                  id="loggers-values-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.loggersValues.value')}
                id="loggers-values-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                id="loggers-values-loggers"
                name="loggers"
                data-cy="loggers"
                label={translate('autoproApp.loggersValues.loggers')}
                type="select"
              >
                <option value="" key="0" />
                {loggers
                  ? loggers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="loggers-values-loggersFields"
                name="loggersFields"
                data-cy="loggersFields"
                label={translate('autoproApp.loggersValues.loggersFields')}
                type="select"
              >
                <option value="" key="0" />
                {loggersFields
                  ? loggersFields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/loggers-values" replace color="info">
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

export default LoggersValuesUpdate;
