import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './history-values.reducer';
import { IHistoryValues } from 'app/shared/model/history-values.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const HistoryValues = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const historyValuesList = useAppSelector(state => state.historyValues.entities);
  const loading = useAppSelector(state => state.historyValues.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="history-values-heading" data-cy="HistoryValuesHeading">
        <Translate contentKey="autoproApp.historyValues.home.title">History Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.historyValues.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.historyValues.home.createLabel">Create new History Values</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {historyValuesList && historyValuesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.historyValues.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.historyValues.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.historyValues.history">History</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.historyValues.historyFields">History Fields</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {historyValuesList.map((historyValues, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${historyValues.id}`} color="link" size="sm">
                      {historyValues.id}
                    </Button>
                  </td>
                  <td>{historyValues.value}</td>
                  <td>{historyValues.history ? <Link to={`history/${historyValues.history.id}`}>{historyValues.history.id}</Link> : ''}</td>
                  <td>
                    {historyValues.historyFields ? (
                      <Link to={`history-fields/${historyValues.historyFields.id}`}>{historyValues.historyFields.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${historyValues.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${historyValues.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${historyValues.id}/delete`}
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
              <Translate contentKey="autoproApp.historyValues.home.notFound">No History Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default HistoryValues;
