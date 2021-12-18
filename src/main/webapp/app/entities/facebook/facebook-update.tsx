import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './facebook.reducer';
import { IFacebook } from 'app/shared/model/facebook.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { FbType } from 'app/shared/model/enumerations/fb-type.model';

export const FacebookUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const facebookEntity = useAppSelector(state => state.facebook.entity);
  const loading = useAppSelector(state => state.facebook.loading);
  const updating = useAppSelector(state => state.facebook.updating);
  const updateSuccess = useAppSelector(state => state.facebook.updateSuccess);
  const fbTypeValues = Object.keys(FbType);
  const handleClose = () => {
    props.history.push('/facebook');
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
    const entity = {
      ...facebookEntity,
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
      ? {}
      : {
          type: 'Post',
          ...facebookEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.facebook.home.createOrEditLabel" data-cy="FacebookCreateUpdateHeading">
            <Translate contentKey="autoproApp.facebook.home.createOrEditLabel">Create or edit a Facebook</Translate>
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
                  id="facebook-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('autoproApp.facebook.name')} id="facebook-name" name="name" data-cy="name" type="text" />
              <ValidatedField label={translate('autoproApp.facebook.url')} id="facebook-url" name="url" data-cy="url" type="text" />
              <ValidatedField
                label={translate('autoproApp.facebook.idOnFb')}
                id="facebook-idOnFb"
                name="idOnFb"
                data-cy="idOnFb"
                type="text"
              />
              <ValidatedField label={translate('autoproApp.facebook.type')} id="facebook-type" name="type" data-cy="type" type="select">
                {fbTypeValues.map(fbType => (
                  <option value={fbType} key={fbType}>
                    {translate('autoproApp.FbType.' + fbType)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/facebook" replace color="info">
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

export default FacebookUpdate;
