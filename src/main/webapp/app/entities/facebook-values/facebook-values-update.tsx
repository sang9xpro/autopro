import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IFacebook } from 'app/shared/model/facebook.model';
import { getEntities as getFacebooks } from 'app/entities/facebook/facebook.reducer';
import { IFacebookFields } from 'app/shared/model/facebook-fields.model';
import { getEntities as getFacebookFields } from 'app/entities/facebook-fields/facebook-fields.reducer';
import { getEntity, updateEntity, createEntity, reset } from './facebook-values.reducer';
import { IFacebookValues } from 'app/shared/model/facebook-values.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FacebookValuesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const facebooks = useAppSelector(state => state.facebook.entities);
  const facebookFields = useAppSelector(state => state.facebookFields.entities);
  const facebookValuesEntity = useAppSelector(state => state.facebookValues.entity);
  const loading = useAppSelector(state => state.facebookValues.loading);
  const updating = useAppSelector(state => state.facebookValues.updating);
  const updateSuccess = useAppSelector(state => state.facebookValues.updateSuccess);
  const handleClose = () => {
    props.history.push('/facebook-values');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getFacebooks({}));
    dispatch(getFacebookFields({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...facebookValuesEntity,
      ...values,
      facebook: facebooks.find(it => it.id.toString() === values.facebook.toString()),
      facebookFields: facebookFields.find(it => it.id.toString() === values.facebookFields.toString()),
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
          ...facebookValuesEntity,
          facebook: facebookValuesEntity?.facebook?.id,
          facebookFields: facebookValuesEntity?.facebookFields?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.facebookValues.home.createOrEditLabel" data-cy="FacebookValuesCreateUpdateHeading">
            <Translate contentKey="autoproApp.facebookValues.home.createOrEditLabel">Create or edit a FacebookValues</Translate>
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
                  id="facebook-values-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.facebookValues.value')}
                id="facebook-values-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                id="facebook-values-facebook"
                name="facebook"
                data-cy="facebook"
                label={translate('autoproApp.facebookValues.facebook')}
                type="select"
              >
                <option value="" key="0" />
                {facebooks
                  ? facebooks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="facebook-values-facebookFields"
                name="facebookFields"
                data-cy="facebookFields"
                label={translate('autoproApp.facebookValues.facebookFields')}
                type="select"
              >
                <option value="" key="0" />
                {facebookFields
                  ? facebookFields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/facebook-values" replace color="info">
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

export default FacebookValuesUpdate;
