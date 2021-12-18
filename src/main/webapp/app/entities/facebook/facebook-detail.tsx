import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './facebook.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FacebookDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const facebookEntity = useAppSelector(state => state.facebook.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="facebookDetailsHeading">
          <Translate contentKey="autoproApp.facebook.detail.title">Facebook</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{facebookEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="autoproApp.facebook.name">Name</Translate>
            </span>
          </dt>
          <dd>{facebookEntity.name}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="autoproApp.facebook.url">Url</Translate>
            </span>
          </dt>
          <dd>{facebookEntity.url}</dd>
          <dt>
            <span id="idOnFb">
              <Translate contentKey="autoproApp.facebook.idOnFb">Id On Fb</Translate>
            </span>
          </dt>
          <dd>{facebookEntity.idOnFb}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="autoproApp.facebook.type">Type</Translate>
            </span>
          </dt>
          <dd>{facebookEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/facebook" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/facebook/${facebookEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FacebookDetail;
