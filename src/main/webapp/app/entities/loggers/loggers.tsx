import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './loggers.reducer';
import { ILoggers } from 'app/shared/model/loggers.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Loggers = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const loggersList = useAppSelector(state => state.loggers.entities);
  const loading = useAppSelector(state => state.loggers.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="loggers-heading" data-cy="LoggersHeading">
        <Translate contentKey="autoproApp.loggers.home.title">Loggers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.loggers.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.loggers.home.createLabel">Create new Loggers</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {loggersList && loggersList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.loggers.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.loggers.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.loggers.logDetail">Log Detail</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.loggers.lastUpdate">Last Update</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {loggersList.map((loggers, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${loggers.id}`} color="link" size="sm">
                      {loggers.id}
                    </Button>
                  </td>
                  <td>{loggers.status}</td>
                  <td>
                    {loggers.logDetail ? (
                      <div>
                        {loggers.logDetailContentType ? (
                          <a onClick={openFile(loggers.logDetailContentType, loggers.logDetail)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {loggers.logDetailContentType}, {byteSize(loggers.logDetail)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{loggers.lastUpdate ? <TextFormat type="date" value={loggers.lastUpdate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${loggers.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${loggers.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${loggers.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="autoproApp.loggers.home.notFound">No Loggers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Loggers;
