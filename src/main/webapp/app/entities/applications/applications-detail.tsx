import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './applications.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicationsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const applicationsEntity = useAppSelector(state => state.applications.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicationsDetailsHeading">
          <Translate contentKey="autoproApp.applications.detail.title">Applications</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{applicationsEntity.id}</dd>
          <dt>
            <span id="appName">
              <Translate contentKey="autoproApp.applications.appName">App Name</Translate>
            </span>
          </dt>
          <dd>{applicationsEntity.appName}</dd>
          <dt>
            <span id="appCode">
              <Translate contentKey="autoproApp.applications.appCode">App Code</Translate>
            </span>
          </dt>
          <dd>{applicationsEntity.appCode}</dd>
        </dl>
        <Button tag={Link} to="/applications" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/applications/${applicationsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicationsDetail;
