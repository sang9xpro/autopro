import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './tasks.reducer';
import { ITasks } from 'app/shared/model/tasks.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Tasks = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const tasksList = useAppSelector(state => state.tasks.entities);
  const loading = useAppSelector(state => state.tasks.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="tasks-heading" data-cy="TasksHeading">
        <Translate contentKey="autoproApp.tasks.home.title">Tasks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.tasks.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.tasks.home.createLabel">Create new Tasks</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tasksList && tasksList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.tasks.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.tasks.taskName">Task Name</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.tasks.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.tasks.source">Source</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.tasks.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.tasks.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.tasks.applications">Applications</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tasksList.map((tasks, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${tasks.id}`} color="link" size="sm">
                      {tasks.id}
                    </Button>
                  </td>
                  <td>{tasks.taskName}</td>
                  <td>{tasks.description}</td>
                  <td>{tasks.source}</td>
                  <td>{tasks.price}</td>
                  <td>
                    <Translate contentKey={`autoproApp.TaskType.${tasks.type}`} />
                  </td>
                  <td>{tasks.applications ? <Link to={`applications/${tasks.applications.id}`}>{tasks.applications.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tasks.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tasks.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tasks.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="autoproApp.tasks.home.notFound">No Tasks found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Tasks;
