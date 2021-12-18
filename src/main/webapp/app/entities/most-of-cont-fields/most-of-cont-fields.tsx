import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './most-of-cont-fields.reducer';
import { IMostOfContFields } from 'app/shared/model/most-of-cont-fields.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MostOfContFields = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const mostOfContFieldsList = useAppSelector(state => state.mostOfContFields.entities);
  const loading = useAppSelector(state => state.mostOfContFields.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="most-of-cont-fields-heading" data-cy="MostOfContFieldsHeading">
        <Translate contentKey="autoproApp.mostOfContFields.home.title">Most Of Cont Fields</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.mostOfContFields.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.mostOfContFields.home.createLabel">Create new Most Of Cont Fields</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {mostOfContFieldsList && mostOfContFieldsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.mostOfContFields.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.mostOfContFields.fieldName">Field Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mostOfContFieldsList.map((mostOfContFields, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${mostOfContFields.id}`} color="link" size="sm">
                      {mostOfContFields.id}
                    </Button>
                  </td>
                  <td>{mostOfContFields.fieldName}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mostOfContFields.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${mostOfContFields.id}/edit`}
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
                        to={`${match.url}/${mostOfContFields.id}/delete`}
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
              <Translate contentKey="autoproApp.mostOfContFields.home.notFound">No Most Of Cont Fields found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MostOfContFields;
