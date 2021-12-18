import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './account-values.reducer';
import { IAccountValues } from 'app/shared/model/account-values.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AccountValues = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const accountValuesList = useAppSelector(state => state.accountValues.entities);
  const loading = useAppSelector(state => state.accountValues.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="account-values-heading" data-cy="AccountValuesHeading">
        <Translate contentKey="autoproApp.accountValues.home.title">Account Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.accountValues.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.accountValues.home.createLabel">Create new Account Values</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {accountValuesList && accountValuesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.accountValues.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.accountValues.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.accountValues.accounts">Accounts</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.accountValues.accountFields">Account Fields</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {accountValuesList.map((accountValues, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${accountValues.id}`} color="link" size="sm">
                      {accountValues.id}
                    </Button>
                  </td>
                  <td>{accountValues.value}</td>
                  <td>
                    {accountValues.accounts ? <Link to={`accounts/${accountValues.accounts.id}`}>{accountValues.accounts.id}</Link> : ''}
                  </td>
                  <td>
                    {accountValues.accountFields ? (
                      <Link to={`account-fields/${accountValues.accountFields.id}`}>{accountValues.accountFields.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${accountValues.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${accountValues.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${accountValues.id}/delete`}
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
              <Translate contentKey="autoproApp.accountValues.home.notFound">No Account Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AccountValues;
