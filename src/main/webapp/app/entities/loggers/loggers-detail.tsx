import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './loggers.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LoggersDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const loggersEntity = useAppSelector(state => state.loggers.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="loggersDetailsHeading">
          <Translate contentKey="autoproApp.loggers.detail.title">Loggers</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{loggersEntity.id}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="autoproApp.loggers.status">Status</Translate>
            </span>
          </dt>
          <dd>{loggersEntity.status}</dd>
          <dt>
            <span id="logDetail">
              <Translate contentKey="autoproApp.loggers.logDetail">Log Detail</Translate>
            </span>
          </dt>
          <dd>
            {loggersEntity.logDetail ? (
              <div>
                {loggersEntity.logDetailContentType ? (
                  <a onClick={openFile(loggersEntity.logDetailContentType, loggersEntity.logDetail)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {loggersEntity.logDetailContentType}, {byteSize(loggersEntity.logDetail)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdate">
              <Translate contentKey="autoproApp.loggers.lastUpdate">Last Update</Translate>
            </span>
          </dt>
          <dd>{loggersEntity.lastUpdate ? <TextFormat value={loggersEntity.lastUpdate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/loggers" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/loggers/${loggersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LoggersDetail;
