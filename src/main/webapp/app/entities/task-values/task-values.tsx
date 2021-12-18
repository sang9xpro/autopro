import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './task-values.reducer';
import { ITaskValues } from 'app/shared/model/task-values.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaskValues = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const taskValuesList = useAppSelector(state => state.taskValues.entities);
  const loading = useAppSelector(state => state.taskValues.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="task-values-heading" data-cy="TaskValuesHeading">
        <Translate contentKey="autoproApp.taskValues.home.title">Task Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.taskValues.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.taskValues.home.createLabel">Create new Task Values</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {taskValuesList && taskValuesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.taskValues.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.taskValues.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.taskValues.tasks">Tasks</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.taskValues.taskFields">Task Fields</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {taskValuesList.map((taskValues, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${taskValues.id}`} color="link" size="sm">
                      {taskValues.id}
                    </Button>
                  </td>
                  <td>{taskValues.value}</td>
                  <td>{taskValues.tasks ? <Link to={`tasks/${taskValues.tasks.id}`}>{taskValues.tasks.id}</Link> : ''}</td>
                  <td>
                    {taskValues.taskFields ? <Link to={`task-fields/${taskValues.taskFields.id}`}>{taskValues.taskFields.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${taskValues.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${taskValues.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${taskValues.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="autoproApp.taskValues.home.notFound">No Task Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TaskValues;
