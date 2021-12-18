import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './account-values.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AccountValuesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const accountValuesEntity = useAppSelector(state => state.accountValues.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accountValuesDetailsHeading">
          <Translate contentKey="autoproApp.accountValues.detail.title">AccountValues</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{accountValuesEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="autoproApp.accountValues.value">Value</Translate>
            </span>
          </dt>
          <dd>{accountValuesEntity.value}</dd>
          <dt>
            <Translate contentKey="autoproApp.accountValues.accounts">Accounts</Translate>
          </dt>
          <dd>{accountValuesEntity.accounts ? accountValuesEntity.accounts.id : ''}</dd>
          <dt>
            <Translate contentKey="autoproApp.accountValues.accountFields">Account Fields</Translate>
          </dt>
          <dd>{accountValuesEntity.accountFields ? accountValuesEntity.accountFields.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/account-values" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/account-values/${accountValuesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccountValuesDetail;
