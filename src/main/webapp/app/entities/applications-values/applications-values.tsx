import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './applications-values.reducer';
import { IApplicationsValues } from 'app/shared/model/applications-values.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicationsValues = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const applicationsValuesList = useAppSelector(state => state.applicationsValues.entities);
  const loading = useAppSelector(state => state.applicationsValues.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="applications-values-heading" data-cy="ApplicationsValuesHeading">
        <Translate contentKey="autoproApp.applicationsValues.home.title">Applications Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.applicationsValues.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.applicationsValues.home.createLabel">Create new Applications Values</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {applicationsValuesList && applicationsValuesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.applicationsValues.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.applicationsValues.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.applicationsValues.applications">Applications</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.applicationsValues.applicationsFields">Applications Fields</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {applicationsValuesList.map((applicationsValues, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${applicationsValues.id}`} color="link" size="sm">
                      {applicationsValues.id}
                    </Button>
                  </td>
                  <td>{applicationsValues.value}</td>
                  <td>
                    {applicationsValues.applications ? (
                      <Link to={`applications/${applicationsValues.applications.id}`}>{applicationsValues.applications.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {applicationsValues.applicationsFields ? (
                      <Link to={`applications-fields/${applicationsValues.applicationsFields.id}`}>
                        {applicationsValues.applicationsFields.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${applicationsValues.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${applicationsValues.id}/edit`}
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
                        to={`${match.url}/${applicationsValues.id}/delete`}
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
              <Translate contentKey="autoproApp.applicationsValues.home.notFound">No Applications Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ApplicationsValues;
