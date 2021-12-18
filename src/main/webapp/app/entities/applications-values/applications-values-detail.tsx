import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './applications-values.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicationsValuesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const applicationsValuesEntity = useAppSelector(state => state.applicationsValues.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicationsValuesDetailsHeading">
          <Translate contentKey="autoproApp.applicationsValues.detail.title">ApplicationsValues</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{applicationsValuesEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="autoproApp.applicationsValues.value">Value</Translate>
            </span>
          </dt>
          <dd>{applicationsValuesEntity.value}</dd>
          <dt>
            <Translate contentKey="autoproApp.applicationsValues.applications">Applications</Translate>
          </dt>
          <dd>{applicationsValuesEntity.applications ? applicationsValuesEntity.applications.id : ''}</dd>
          <dt>
            <Translate contentKey="autoproApp.applicationsValues.applicationsFields">Applications Fields</Translate>
          </dt>
          <dd>{applicationsValuesEntity.applicationsFields ? applicationsValuesEntity.applicationsFields.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/applications-values" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/applications-values/${applicationsValuesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicationsValuesDetail;
