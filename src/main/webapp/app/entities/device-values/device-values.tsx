import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './device-values.reducer';
import { IDeviceValues } from 'app/shared/model/device-values.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DeviceValues = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const deviceValuesList = useAppSelector(state => state.deviceValues.entities);
  const loading = useAppSelector(state => state.deviceValues.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="device-values-heading" data-cy="DeviceValuesHeading">
        <Translate contentKey="autoproApp.deviceValues.home.title">Device Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.deviceValues.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.deviceValues.home.createLabel">Create new Device Values</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {deviceValuesList && deviceValuesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.deviceValues.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.deviceValues.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.deviceValues.devices">Devices</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.deviceValues.devicesFields">Devices Fields</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {deviceValuesList.map((deviceValues, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${deviceValues.id}`} color="link" size="sm">
                      {deviceValues.id}
                    </Button>
                  </td>
                  <td>{deviceValues.value}</td>
                  <td>{deviceValues.devices ? <Link to={`devices/${deviceValues.devices.id}`}>{deviceValues.devices.id}</Link> : ''}</td>
                  <td>
                    {deviceValues.devicesFields ? (
                      <Link to={`devices-fields/${deviceValues.devicesFields.id}`}>{deviceValues.devicesFields.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${deviceValues.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${deviceValues.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${deviceValues.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="autoproApp.deviceValues.home.notFound">No Device Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DeviceValues;
