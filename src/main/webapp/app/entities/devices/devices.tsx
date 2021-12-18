import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './devices.reducer';
import { IDevices } from 'app/shared/model/devices.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Devices = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const devicesList = useAppSelector(state => state.devices.entities);
  const loading = useAppSelector(state => state.devices.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="devices-heading" data-cy="DevicesHeading">
        <Translate contentKey="autoproApp.devices.home.title">Devices</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.devices.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.devices.home.createLabel">Create new Devices</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {devicesList && devicesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.devices.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.devices.ipAddress">Ip Address</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.devices.macAddress">Mac Address</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.devices.os">Os</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.devices.deviceType">Device Type</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.devices.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.devices.lastUpdate">Last Update</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.devices.owner">Owner</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {devicesList.map((devices, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${devices.id}`} color="link" size="sm">
                      {devices.id}
                    </Button>
                  </td>
                  <td>{devices.ipAddress}</td>
                  <td>{devices.macAddress}</td>
                  <td>{devices.os}</td>
                  <td>
                    <Translate contentKey={`autoproApp.DeviceType.${devices.deviceType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`autoproApp.DeviceStatus.${devices.status}`} />
                  </td>
                  <td>{devices.lastUpdate ? <TextFormat type="date" value={devices.lastUpdate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{devices.owner}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${devices.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${devices.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${devices.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="autoproApp.devices.home.notFound">No Devices found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Devices;
