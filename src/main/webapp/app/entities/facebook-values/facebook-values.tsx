import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './facebook-values.reducer';
import { IFacebookValues } from 'app/shared/model/facebook-values.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FacebookValues = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const facebookValuesList = useAppSelector(state => state.facebookValues.entities);
  const loading = useAppSelector(state => state.facebookValues.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="facebook-values-heading" data-cy="FacebookValuesHeading">
        <Translate contentKey="autoproApp.facebookValues.home.title">Facebook Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.facebookValues.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.facebookValues.home.createLabel">Create new Facebook Values</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {facebookValuesList && facebookValuesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.facebookValues.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.facebookValues.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.facebookValues.facebook">Facebook</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.facebookValues.facebookFields">Facebook Fields</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {facebookValuesList.map((facebookValues, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${facebookValues.id}`} color="link" size="sm">
                      {facebookValues.id}
                    </Button>
                  </td>
                  <td>{facebookValues.value}</td>
                  <td>
                    {facebookValues.facebook ? <Link to={`facebook/${facebookValues.facebook.id}`}>{facebookValues.facebook.id}</Link> : ''}
                  </td>
                  <td>
                    {facebookValues.facebookFields ? (
                      <Link to={`facebook-fields/${facebookValues.facebookFields.id}`}>{facebookValues.facebookFields.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${facebookValues.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${facebookValues.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${facebookValues.id}/delete`}
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
              <Translate contentKey="autoproApp.facebookValues.home.notFound">No Facebook Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FacebookValues;
