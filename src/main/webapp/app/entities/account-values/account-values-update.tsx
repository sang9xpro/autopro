import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAccounts } from 'app/shared/model/accounts.model';
import { getEntities as getAccounts } from 'app/entities/accounts/accounts.reducer';
import { IAccountFields } from 'app/shared/model/account-fields.model';
import { getEntities as getAccountFields } from 'app/entities/account-fields/account-fields.reducer';
import { getEntity, updateEntity, createEntity, reset } from './account-values.reducer';
import { IAccountValues } from 'app/shared/model/account-values.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AccountValuesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const accounts = useAppSelector(state => state.accounts.entities);
  const accountFields = useAppSelector(state => state.accountFields.entities);
  const accountValuesEntity = useAppSelector(state => state.accountValues.entity);
  const loading = useAppSelector(state => state.accountValues.loading);
  const updating = useAppSelector(state => state.accountValues.updating);
  const updateSuccess = useAppSelector(state => state.accountValues.updateSuccess);
  const handleClose = () => {
    props.history.push('/account-values');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAccounts({}));
    dispatch(getAccountFields({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...accountValuesEntity,
      ...values,
      accounts: accounts.find(it => it.id.toString() === values.accounts.toString()),
      accountFields: accountFields.find(it => it.id.toString() === values.accountFields.toString()),
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
          ...accountValuesEntity,
          accounts: accountValuesEntity?.accounts?.id,
          accountFields: accountValuesEntity?.accountFields?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.accountValues.home.createOrEditLabel" data-cy="AccountValuesCreateUpdateHeading">
            <Translate contentKey="autoproApp.accountValues.home.createOrEditLabel">Create or edit a AccountValues</Translate>
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
                  id="account-values-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.accountValues.value')}
                id="account-values-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                id="account-values-accounts"
                name="accounts"
                data-cy="accounts"
                label={translate('autoproApp.accountValues.accounts')}
                type="select"
              >
                <option value="" key="0" />
                {accounts
                  ? accounts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="account-values-accountFields"
                name="accountFields"
                data-cy="accountFields"
                label={translate('autoproApp.accountValues.accountFields')}
                type="select"
              >
                <option value="" key="0" />
                {accountFields
                  ? accountFields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/account-values" replace color="info">
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

export default AccountValuesUpdate;
