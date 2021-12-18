import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IHistory } from 'app/shared/model/history.model';
import { getEntities as getHistories } from 'app/entities/history/history.reducer';
import { IHistoryFields } from 'app/shared/model/history-fields.model';
import { getEntities as getHistoryFields } from 'app/entities/history-fields/history-fields.reducer';
import { getEntity, updateEntity, createEntity, reset } from './history-values.reducer';
import { IHistoryValues } from 'app/shared/model/history-values.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const HistoryValuesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const histories = useAppSelector(state => state.history.entities);
  const historyFields = useAppSelector(state => state.historyFields.entities);
  const historyValuesEntity = useAppSelector(state => state.historyValues.entity);
  const loading = useAppSelector(state => state.historyValues.loading);
  const updating = useAppSelector(state => state.historyValues.updating);
  const updateSuccess = useAppSelector(state => state.historyValues.updateSuccess);
  const handleClose = () => {
    props.history.push('/history-values');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getHistories({}));
    dispatch(getHistoryFields({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...historyValuesEntity,
      ...values,
      history: histories.find(it => it.id.toString() === values.history.toString()),
      historyFields: historyFields.find(it => it.id.toString() === values.historyFields.toString()),
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
          ...historyValuesEntity,
          history: historyValuesEntity?.history?.id,
          historyFields: historyValuesEntity?.historyFields?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.historyValues.home.createOrEditLabel" data-cy="HistoryValuesCreateUpdateHeading">
            <Translate contentKey="autoproApp.historyValues.home.createOrEditLabel">Create or edit a HistoryValues</Translate>
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
                  id="history-values-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.historyValues.value')}
                id="history-values-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                id="history-values-history"
                name="history"
                data-cy="history"
                label={translate('autoproApp.historyValues.history')}
                type="select"
              >
                <option value="" key="0" />
                {histories
                  ? histories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="history-values-historyFields"
                name="historyFields"
                data-cy="historyFields"
                label={translate('autoproApp.historyValues.historyFields')}
                type="select"
              >
                <option value="" key="0" />
                {historyFields
                  ? historyFields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/history-values" replace color="info">
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

export default HistoryValuesUpdate;
