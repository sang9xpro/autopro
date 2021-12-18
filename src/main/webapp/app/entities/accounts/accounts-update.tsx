import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplications } from 'app/shared/model/applications.model';
import { getEntities as getApplications } from 'app/entities/applications/applications.reducer';
import { getEntity, updateEntity, createEntity, reset } from './accounts.reducer';
import { IAccounts } from 'app/shared/model/accounts.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AccountsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applications = useAppSelector(state => state.applications.entities);
  const accountsEntity = useAppSelector(state => state.accounts.entity);
  const loading = useAppSelector(state => state.accounts.loading);
  const updating = useAppSelector(state => state.accounts.updating);
  const updateSuccess = useAppSelector(state => state.accounts.updateSuccess);
  const handleClose = () => {
    props.history.push('/accounts');
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
    values.lastUpdate = convertDateTimeToServer(values.lastUpdate);

    const entity = {
      ...accountsEntity,
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
      ? {
          lastUpdate: displayDefaultDateTime(),
        }
      : {
          ...accountsEntity,
          lastUpdate: convertDateTimeFromServer(accountsEntity.lastUpdate),
          applications: accountsEntity?.applications?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.accounts.home.createOrEditLabel" data-cy="AccountsCreateUpdateHeading">
            <Translate contentKey="autoproApp.accounts.home.createOrEditLabel">Create or edit a Accounts</Translate>
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
                  id="accounts-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.accounts.userName')}
                id="accounts-userName"
                name="userName"
                data-cy="userName"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.accounts.password')}
                id="accounts-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.accounts.urlLogin')}
                id="accounts-urlLogin"
                name="urlLogin"
                data-cy="urlLogin"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.accounts.profileFirefox')}
                id="accounts-profileFirefox"
                name="profileFirefox"
                data-cy="profileFirefox"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.accounts.profileChrome')}
                id="accounts-profileChrome"
                name="profileChrome"
                data-cy="profileChrome"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.accounts.lastUpdate')}
                id="accounts-lastUpdate"
                name="lastUpdate"
                data-cy="lastUpdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('autoproApp.accounts.owner')} id="accounts-owner" name="owner" data-cy="owner" type="text" />
              <ValidatedField
                label={translate('autoproApp.accounts.actived')}
                id="accounts-actived"
                name="actived"
                data-cy="actived"
                type="text"
                validate={{
                  min: { value: 1, message: translate('entity.validation.min', { min: 1 }) },
                  max: { value: 1, message: translate('entity.validation.max', { max: 1 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="accounts-applications"
                name="applications"
                data-cy="applications"
                label={translate('autoproApp.accounts.applications')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/accounts" replace color="info">
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

export default AccountsUpdate;
