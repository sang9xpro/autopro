import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './most-of-cont-values.reducer';
import { IMostOfContValues } from 'app/shared/model/most-of-cont-values.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MostOfContValues = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const mostOfContValuesList = useAppSelector(state => state.mostOfContValues.entities);
  const loading = useAppSelector(state => state.mostOfContValues.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="most-of-cont-values-heading" data-cy="MostOfContValuesHeading">
        <Translate contentKey="autoproApp.mostOfContValues.home.title">Most Of Cont Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.mostOfContValues.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.mostOfContValues.home.createLabel">Create new Most Of Cont Values</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {mostOfContValuesList && mostOfContValuesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.mostOfContValues.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.mostOfContValues.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.mostOfContValues.mostOfContent">Most Of Content</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.mostOfContValues.mostOfContFields">Most Of Cont Fields</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mostOfContValuesList.map((mostOfContValues, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${mostOfContValues.id}`} color="link" size="sm">
                      {mostOfContValues.id}
                    </Button>
                  </td>
                  <td>{mostOfContValues.value}</td>
                  <td>
                    {mostOfContValues.mostOfContent ? (
                      <Link to={`most-of-content/${mostOfContValues.mostOfContent.id}`}>{mostOfContValues.mostOfContent.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {mostOfContValues.mostOfContFields ? (
                      <Link to={`most-of-cont-fields/${mostOfContValues.mostOfContFields.id}`}>{mostOfContValues.mostOfContFields.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mostOfContValues.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${mostOfContValues.id}/edit`}
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
                        to={`${match.url}/${mostOfContValues.id}/delete`}
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
              <Translate contentKey="autoproApp.mostOfContValues.home.notFound">No Most Of Cont Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MostOfContValues;
