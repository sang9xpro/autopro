import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './scheduler-task.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SchedulerTaskDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const schedulerTaskEntity = useAppSelector(state => state.schedulerTask.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="schedulerTaskDetailsHeading">
          <Translate contentKey="autoproApp.schedulerTask.detail.title">SchedulerTask</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{schedulerTaskEntity.id}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="autoproApp.schedulerTask.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {schedulerTaskEntity.startTime ? (
              <TextFormat value={schedulerTaskEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="autoproApp.schedulerTask.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {schedulerTaskEntity.endTime ? <TextFormat value={schedulerTaskEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="countFrom">
              <Translate contentKey="autoproApp.schedulerTask.countFrom">Count From</Translate>
            </span>
          </dt>
          <dd>{schedulerTaskEntity.countFrom}</dd>
          <dt>
            <span id="countTo">
              <Translate contentKey="autoproApp.schedulerTask.countTo">Count To</Translate>
            </span>
          </dt>
          <dd>{schedulerTaskEntity.countTo}</dd>
          <dt>
            <span id="point">
              <Translate contentKey="autoproApp.schedulerTask.point">Point</Translate>
            </span>
          </dt>
          <dd>{schedulerTaskEntity.point}</dd>
          <dt>
            <span id="lastUpdate">
              <Translate contentKey="autoproApp.schedulerTask.lastUpdate">Last Update</Translate>
            </span>
          </dt>
          <dd>
            {schedulerTaskEntity.lastUpdate ? (
              <TextFormat value={schedulerTaskEntity.lastUpdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="owner">
              <Translate contentKey="autoproApp.schedulerTask.owner">Owner</Translate>
            </span>
          </dt>
          <dd>{schedulerTaskEntity.owner}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="autoproApp.schedulerTask.status">Status</Translate>
            </span>
          </dt>
          <dd>{schedulerTaskEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/scheduler-task" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/scheduler-task/${schedulerTaskEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SchedulerTaskDetail;
