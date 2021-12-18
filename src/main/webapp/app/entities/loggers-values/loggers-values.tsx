import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './loggers-values.reducer';
import { ILoggersValues } from 'app/shared/model/loggers-values.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LoggersValues = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const loggersValuesList = useAppSelector(state => state.loggersValues.entities);
  const loading = useAppSelector(state => state.loggersValues.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="loggers-values-heading" data-cy="LoggersValuesHeading">
        <Translate contentKey="autoproApp.loggersValues.home.title">Loggers Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.loggersValues.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.loggersValues.home.createLabel">Create new Loggers Values</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {loggersValuesList && loggersValuesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.loggersValues.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.loggersValues.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.loggersValues.loggers">Loggers</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.loggersValues.loggersFields">Loggers Fields</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {loggersValuesList.map((loggersValues, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${loggersValues.id}`} color="link" size="sm">
                      {loggersValues.id}
                    </Button>
                  </td>
                  <td>{loggersValues.value}</td>
                  <td>{loggersValues.loggers ? <Link to={`loggers/${loggersValues.loggers.id}`}>{loggersValues.loggers.id}</Link> : ''}</td>
                  <td>
                    {loggersValues.loggersFields ? (
                      <Link to={`loggers-fields/${loggersValues.loggersFields.id}`}>{loggersValues.loggersFields.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${loggersValues.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${loggersValues.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${loggersValues.id}/delete`}
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
              <Translate contentKey="autoproApp.loggersValues.home.notFound">No Loggers Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default LoggersValues;
