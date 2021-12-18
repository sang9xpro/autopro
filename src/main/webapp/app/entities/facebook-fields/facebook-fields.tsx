import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './facebook-fields.reducer';
import { IFacebookFields } from 'app/shared/model/facebook-fields.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FacebookFields = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const facebookFieldsList = useAppSelector(state => state.facebookFields.entities);
  const loading = useAppSelector(state => state.facebookFields.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="facebook-fields-heading" data-cy="FacebookFieldsHeading">
        <Translate contentKey="autoproApp.facebookFields.home.title">Facebook Fields</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.facebookFields.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.facebookFields.home.createLabel">Create new Facebook Fields</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {facebookFieldsList && facebookFieldsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.facebookFields.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.facebookFields.fieldName">Field Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {facebookFieldsList.map((facebookFields, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${facebookFields.id}`} color="link" size="sm">
                      {facebookFields.id}
                    </Button>
                  </td>
                  <td>{facebookFields.fieldName}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${facebookFields.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${facebookFields.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${facebookFields.id}/delete`}
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
              <Translate contentKey="autoproApp.facebookFields.home.notFound">No Facebook Fields found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FacebookFields;
