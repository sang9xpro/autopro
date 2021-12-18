import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './most-of-cont-fields.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MostOfContFieldsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const mostOfContFieldsEntity = useAppSelector(state => state.mostOfContFields.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mostOfContFieldsDetailsHeading">
          <Translate contentKey="autoproApp.mostOfContFields.detail.title">MostOfContFields</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mostOfContFieldsEntity.id}</dd>
          <dt>
            <span id="fieldName">
              <Translate contentKey="autoproApp.mostOfContFields.fieldName">Field Name</Translate>
            </span>
          </dt>
          <dd>{mostOfContFieldsEntity.fieldName}</dd>
        </dl>
        <Button tag={Link} to="/most-of-cont-fields" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/most-of-cont-fields/${mostOfContFieldsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MostOfContFieldsDetail;
