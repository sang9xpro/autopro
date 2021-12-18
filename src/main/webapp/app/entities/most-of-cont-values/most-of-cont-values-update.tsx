import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMostOfContent } from 'app/shared/model/most-of-content.model';
import { getEntities as getMostOfContents } from 'app/entities/most-of-content/most-of-content.reducer';
import { IMostOfContFields } from 'app/shared/model/most-of-cont-fields.model';
import { getEntities as getMostOfContFields } from 'app/entities/most-of-cont-fields/most-of-cont-fields.reducer';
import { getEntity, updateEntity, createEntity, reset } from './most-of-cont-values.reducer';
import { IMostOfContValues } from 'app/shared/model/most-of-cont-values.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MostOfContValuesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const mostOfContents = useAppSelector(state => state.mostOfContent.entities);
  const mostOfContFields = useAppSelector(state => state.mostOfContFields.entities);
  const mostOfContValuesEntity = useAppSelector(state => state.mostOfContValues.entity);
  const loading = useAppSelector(state => state.mostOfContValues.loading);
  const updating = useAppSelector(state => state.mostOfContValues.updating);
  const updateSuccess = useAppSelector(state => state.mostOfContValues.updateSuccess);
  const handleClose = () => {
    props.history.push('/most-of-cont-values');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMostOfContents({}));
    dispatch(getMostOfContFields({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...mostOfContValuesEntity,
      ...values,
      mostOfContent: mostOfContents.find(it => it.id.toString() === values.mostOfContent.toString()),
      mostOfContFields: mostOfContFields.find(it => it.id.toString() === values.mostOfContFields.toString()),
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
          ...mostOfContValuesEntity,
          mostOfContent: mostOfContValuesEntity?.mostOfContent?.id,
          mostOfContFields: mostOfContValuesEntity?.mostOfContFields?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.mostOfContValues.home.createOrEditLabel" data-cy="MostOfContValuesCreateUpdateHeading">
            <Translate contentKey="autoproApp.mostOfContValues.home.createOrEditLabel">Create or edit a MostOfContValues</Translate>
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
                  id="most-of-cont-values-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.mostOfContValues.value')}
                id="most-of-cont-values-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                id="most-of-cont-values-mostOfContent"
                name="mostOfContent"
                data-cy="mostOfContent"
                label={translate('autoproApp.mostOfContValues.mostOfContent')}
                type="select"
              >
                <option value="" key="0" />
                {mostOfContents
                  ? mostOfContents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="most-of-cont-values-mostOfContFields"
                name="mostOfContFields"
                data-cy="mostOfContFields"
                label={translate('autoproApp.mostOfContValues.mostOfContFields')}
                type="select"
              >
                <option value="" key="0" />
                {mostOfContFields
                  ? mostOfContFields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/most-of-cont-values" replace color="info">
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

export default MostOfContValuesUpdate;
