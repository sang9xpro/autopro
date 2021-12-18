import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './applications-fields.reducer';
import { IApplicationsFields } from 'app/shared/model/applications-fields.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicationsFields = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const applicationsFieldsList = useAppSelector(state => state.applicationsFields.entities);
  const loading = useAppSelector(state => state.applicationsFields.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="applications-fields-heading" data-cy="ApplicationsFieldsHeading">
        <Translate contentKey="autoproApp.applicationsFields.home.title">Applications Fields</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.applicationsFields.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.applicationsFields.home.createLabel">Create new Applications Fields</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {applicationsFieldsList && applicationsFieldsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.applicationsFields.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.applicationsFields.fieldName">Field Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {applicationsFieldsList.map((applicationsFields, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${applicationsFields.id}`} color="link" size="sm">
                      {applicationsFields.id}
                    </Button>
                  </td>
                  <td>{applicationsFields.fieldName}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${applicationsFields.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${applicationsFields.id}/edit`}
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
                        to={`${match.url}/${applicationsFields.id}/delete`}
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
              <Translate contentKey="autoproApp.applicationsFields.home.notFound">No Applications Fields found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ApplicationsFields;
