import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './history.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const HistoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const historyEntity = useAppSelector(state => state.history.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="historyDetailsHeading">
          <Translate contentKey="autoproApp.history.detail.title">History</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{historyEntity.id}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="autoproApp.history.url">Url</Translate>
            </span>
          </dt>
          <dd>{historyEntity.url}</dd>
          <dt>
            <span id="taskId">
              <Translate contentKey="autoproApp.history.taskId">Task Id</Translate>
            </span>
          </dt>
          <dd>{historyEntity.taskId}</dd>
          <dt>
            <span id="deviceId">
              <Translate contentKey="autoproApp.history.deviceId">Device Id</Translate>
            </span>
          </dt>
          <dd>{historyEntity.deviceId}</dd>
          <dt>
            <span id="accountId">
              <Translate contentKey="autoproApp.history.accountId">Account Id</Translate>
            </span>
          </dt>
          <dd>{historyEntity.accountId}</dd>
          <dt>
            <span id="lastUpdate">
              <Translate contentKey="autoproApp.history.lastUpdate">Last Update</Translate>
            </span>
          </dt>
          <dd>{historyEntity.lastUpdate ? <TextFormat value={historyEntity.lastUpdate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/history/${historyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HistoryDetail;
