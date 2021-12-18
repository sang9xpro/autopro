import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './scheduler-task-values.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SchedulerTaskValuesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const schedulerTaskValuesEntity = useAppSelector(state => state.schedulerTaskValues.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="schedulerTaskValuesDetailsHeading">
          <Translate contentKey="autoproApp.schedulerTaskValues.detail.title">SchedulerTaskValues</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{schedulerTaskValuesEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="autoproApp.schedulerTaskValues.value">Value</Translate>
            </span>
          </dt>
          <dd>{schedulerTaskValuesEntity.value}</dd>
          <dt>
            <Translate contentKey="autoproApp.schedulerTaskValues.schedulerTask">Scheduler Task</Translate>
          </dt>
          <dd>{schedulerTaskValuesEntity.schedulerTask ? schedulerTaskValuesEntity.schedulerTask.id : ''}</dd>
          <dt>
            <Translate contentKey="autoproApp.schedulerTaskValues.schedulerTaskFields">Scheduler Task Fields</Translate>
          </dt>
          <dd>{schedulerTaskValuesEntity.schedulerTaskFields ? schedulerTaskValuesEntity.schedulerTaskFields.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/scheduler-task-values" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/scheduler-task-values/${schedulerTaskValuesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SchedulerTaskValuesDetail;
