import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './loggers-values.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LoggersValuesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const loggersValuesEntity = useAppSelector(state => state.loggersValues.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="loggersValuesDetailsHeading">
          <Translate contentKey="autoproApp.loggersValues.detail.title">LoggersValues</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{loggersValuesEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="autoproApp.loggersValues.value">Value</Translate>
            </span>
          </dt>
          <dd>{loggersValuesEntity.value}</dd>
          <dt>
            <Translate contentKey="autoproApp.loggersValues.loggers">Loggers</Translate>
          </dt>
          <dd>{loggersValuesEntity.loggers ? loggersValuesEntity.loggers.id : ''}</dd>
          <dt>
            <Translate contentKey="autoproApp.loggersValues.loggersFields">Loggers Fields</Translate>
          </dt>
          <dd>{loggersValuesEntity.loggersFields ? loggersValuesEntity.loggersFields.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/loggers-values" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/loggers-values/${loggersValuesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LoggersValuesDetail;
