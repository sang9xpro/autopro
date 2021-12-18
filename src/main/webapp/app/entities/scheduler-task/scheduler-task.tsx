import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './scheduler-task.reducer';
import { ISchedulerTask } from 'app/shared/model/scheduler-task.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SchedulerTask = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const schedulerTaskList = useAppSelector(state => state.schedulerTask.entities);
  const loading = useAppSelector(state => state.schedulerTask.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="scheduler-task-heading" data-cy="SchedulerTaskHeading">
        <Translate contentKey="autoproApp.schedulerTask.home.title">Scheduler Tasks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.schedulerTask.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.schedulerTask.home.createLabel">Create new Scheduler Task</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {schedulerTaskList && schedulerTaskList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.schedulerTask.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTask.startTime">Start Time</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTask.endTime">End Time</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTask.countFrom">Count From</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTask.countTo">Count To</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTask.point">Point</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTask.lastUpdate">Last Update</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTask.owner">Owner</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTask.status">Status</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {schedulerTaskList.map((schedulerTask, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${schedulerTask.id}`} color="link" size="sm">
                      {schedulerTask.id}
                    </Button>
                  </td>
                  <td>
                    {schedulerTask.startTime ? <TextFormat type="date" value={schedulerTask.startTime} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {schedulerTask.endTime ? <TextFormat type="date" value={schedulerTask.endTime} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{schedulerTask.countFrom}</td>
                  <td>{schedulerTask.countTo}</td>
                  <td>{schedulerTask.point}</td>
                  <td>
                    {schedulerTask.lastUpdate ? <TextFormat type="date" value={schedulerTask.lastUpdate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{schedulerTask.owner}</td>
                  <td>
                    <Translate contentKey={`autoproApp.SchedulerStatus.${schedulerTask.status}`} />
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${schedulerTask.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${schedulerTask.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${schedulerTask.id}/delete`}
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
              <Translate contentKey="autoproApp.schedulerTask.home.notFound">No Scheduler Tasks found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SchedulerTask;
