import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './scheduler-task-values.reducer';
import { ISchedulerTaskValues } from 'app/shared/model/scheduler-task-values.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SchedulerTaskValues = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const schedulerTaskValuesList = useAppSelector(state => state.schedulerTaskValues.entities);
  const loading = useAppSelector(state => state.schedulerTaskValues.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="scheduler-task-values-heading" data-cy="SchedulerTaskValuesHeading">
        <Translate contentKey="autoproApp.schedulerTaskValues.home.title">Scheduler Task Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.schedulerTaskValues.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.schedulerTaskValues.home.createLabel">Create new Scheduler Task Values</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {schedulerTaskValuesList && schedulerTaskValuesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.schedulerTaskValues.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTaskValues.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTaskValues.schedulerTask">Scheduler Task</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTaskValues.schedulerTaskFields">Scheduler Task Fields</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {schedulerTaskValuesList.map((schedulerTaskValues, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${schedulerTaskValues.id}`} color="link" size="sm">
                      {schedulerTaskValues.id}
                    </Button>
                  </td>
                  <td>{schedulerTaskValues.value}</td>
                  <td>
                    {schedulerTaskValues.schedulerTask ? (
                      <Link to={`scheduler-task/${schedulerTaskValues.schedulerTask.id}`}>{schedulerTaskValues.schedulerTask.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {schedulerTaskValues.schedulerTaskFields ? (
                      <Link to={`scheduler-task-fields/${schedulerTaskValues.schedulerTaskFields.id}`}>
                        {schedulerTaskValues.schedulerTaskFields.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${schedulerTaskValues.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${schedulerTaskValues.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${schedulerTaskValues.id}/delete`}
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
              <Translate contentKey="autoproApp.schedulerTaskValues.home.notFound">No Scheduler Task Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SchedulerTaskValues;
