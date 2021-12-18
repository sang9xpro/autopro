import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './devices.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DevicesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const devicesEntity = useAppSelector(state => state.devices.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="devicesDetailsHeading">
          <Translate contentKey="autoproApp.devices.detail.title">Devices</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{devicesEntity.id}</dd>
          <dt>
            <span id="ipAddress">
              <Translate contentKey="autoproApp.devices.ipAddress">Ip Address</Translate>
            </span>
          </dt>
          <dd>{devicesEntity.ipAddress}</dd>
          <dt>
            <span id="macAddress">
              <Translate contentKey="autoproApp.devices.macAddress">Mac Address</Translate>
            </span>
          </dt>
          <dd>{devicesEntity.macAddress}</dd>
          <dt>
            <span id="os">
              <Translate contentKey="autoproApp.devices.os">Os</Translate>
            </span>
          </dt>
          <dd>{devicesEntity.os}</dd>
          <dt>
            <span id="deviceType">
              <Translate contentKey="autoproApp.devices.deviceType">Device Type</Translate>
            </span>
          </dt>
          <dd>{devicesEntity.deviceType}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="autoproApp.devices.status">Status</Translate>
            </span>
          </dt>
          <dd>{devicesEntity.status}</dd>
          <dt>
            <span id="lastUpdate">
              <Translate contentKey="autoproApp.devices.lastUpdate">Last Update</Translate>
            </span>
          </dt>
          <dd>{devicesEntity.lastUpdate ? <TextFormat value={devicesEntity.lastUpdate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="owner">
              <Translate contentKey="autoproApp.devices.owner">Owner</Translate>
            </span>
          </dt>
          <dd>{devicesEntity.owner}</dd>
        </dl>
        <Button tag={Link} to="/devices" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/devices/${devicesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DevicesDetail;
