import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './tasks.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TasksDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tasksEntity = useAppSelector(state => state.tasks.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tasksDetailsHeading">
          <Translate contentKey="autoproApp.tasks.detail.title">Tasks</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tasksEntity.id}</dd>
          <dt>
            <span id="taskName">
              <Translate contentKey="autoproApp.tasks.taskName">Task Name</Translate>
            </span>
          </dt>
          <dd>{tasksEntity.taskName}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="autoproApp.tasks.description">Description</Translate>
            </span>
          </dt>
          <dd>{tasksEntity.description}</dd>
          <dt>
            <span id="source">
              <Translate contentKey="autoproApp.tasks.source">Source</Translate>
            </span>
          </dt>
          <dd>{tasksEntity.source}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="autoproApp.tasks.price">Price</Translate>
            </span>
          </dt>
          <dd>{tasksEntity.price}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="autoproApp.tasks.type">Type</Translate>
            </span>
          </dt>
          <dd>{tasksEntity.type}</dd>
          <dt>
            <Translate contentKey="autoproApp.tasks.applications">Applications</Translate>
          </dt>
          <dd>{tasksEntity.applications ? tasksEntity.applications.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tasks" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tasks/${tasksEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TasksDetail;
