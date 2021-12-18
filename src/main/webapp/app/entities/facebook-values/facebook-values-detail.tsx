import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './facebook-values.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FacebookValuesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const facebookValuesEntity = useAppSelector(state => state.facebookValues.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="facebookValuesDetailsHeading">
          <Translate contentKey="autoproApp.facebookValues.detail.title">FacebookValues</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{facebookValuesEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="autoproApp.facebookValues.value">Value</Translate>
            </span>
          </dt>
          <dd>{facebookValuesEntity.value}</dd>
          <dt>
            <Translate contentKey="autoproApp.facebookValues.facebook">Facebook</Translate>
          </dt>
          <dd>{facebookValuesEntity.facebook ? facebookValuesEntity.facebook.id : ''}</dd>
          <dt>
            <Translate contentKey="autoproApp.facebookValues.facebookFields">Facebook Fields</Translate>
          </dt>
          <dd>{facebookValuesEntity.facebookFields ? facebookValuesEntity.facebookFields.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/facebook-values" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/facebook-values/${facebookValuesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FacebookValuesDetail;
