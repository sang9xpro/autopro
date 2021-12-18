import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './scheduler-task-fields.reducer';
import { ISchedulerTaskFields } from 'app/shared/model/scheduler-task-fields.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SchedulerTaskFields = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const schedulerTaskFieldsList = useAppSelector(state => state.schedulerTaskFields.entities);
  const loading = useAppSelector(state => state.schedulerTaskFields.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="scheduler-task-fields-heading" data-cy="SchedulerTaskFieldsHeading">
        <Translate contentKey="autoproApp.schedulerTaskFields.home.title">Scheduler Task Fields</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.schedulerTaskFields.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.schedulerTaskFields.home.createLabel">Create new Scheduler Task Fields</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {schedulerTaskFieldsList && schedulerTaskFieldsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.schedulerTaskFields.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.schedulerTaskFields.fieldName">Field Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {schedulerTaskFieldsList.map((schedulerTaskFields, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${schedulerTaskFields.id}`} color="link" size="sm">
                      {schedulerTaskFields.id}
                    </Button>
                  </td>
                  <td>{schedulerTaskFields.fieldName}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${schedulerTaskFields.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${schedulerTaskFields.id}/edit`}
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
                        to={`${match.url}/${schedulerTaskFields.id}/delete`}
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
              <Translate contentKey="autoproApp.schedulerTaskFields.home.notFound">No Scheduler Task Fields found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SchedulerTaskFields;
