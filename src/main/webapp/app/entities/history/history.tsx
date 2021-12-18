import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './history.reducer';
import { IHistory } from 'app/shared/model/history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const History = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const historyList = useAppSelector(state => state.history.entities);
  const loading = useAppSelector(state => state.history.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="history-heading" data-cy="HistoryHeading">
        <Translate contentKey="autoproApp.history.home.title">Histories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.history.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.history.home.createLabel">Create new History</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {historyList && historyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.history.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.history.url">Url</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.history.taskId">Task Id</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.history.deviceId">Device Id</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.history.accountId">Account Id</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.history.lastUpdate">Last Update</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {historyList.map((history, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${history.id}`} color="link" size="sm">
                      {history.id}
                    </Button>
                  </td>
                  <td>{history.url}</td>
                  <td>{history.taskId}</td>
                  <td>{history.deviceId}</td>
                  <td>{history.accountId}</td>
                  <td>{history.lastUpdate ? <TextFormat type="date" value={history.lastUpdate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${history.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${history.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${history.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="autoproApp.history.home.notFound">No Histories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default History;
