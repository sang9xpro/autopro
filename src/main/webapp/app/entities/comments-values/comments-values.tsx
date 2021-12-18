import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './comments-values.reducer';
import { ICommentsValues } from 'app/shared/model/comments-values.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CommentsValues = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const commentsValuesList = useAppSelector(state => state.commentsValues.entities);
  const loading = useAppSelector(state => state.commentsValues.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="comments-values-heading" data-cy="CommentsValuesHeading">
        <Translate contentKey="autoproApp.commentsValues.home.title">Comments Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.commentsValues.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.commentsValues.home.createLabel">Create new Comments Values</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {commentsValuesList && commentsValuesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.commentsValues.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.commentsValues.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.commentsValues.comments">Comments</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.commentsValues.commentsFields">Comments Fields</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {commentsValuesList.map((commentsValues, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${commentsValues.id}`} color="link" size="sm">
                      {commentsValues.id}
                    </Button>
                  </td>
                  <td>{commentsValues.value}</td>
                  <td>
                    {commentsValues.comments ? <Link to={`comments/${commentsValues.comments.id}`}>{commentsValues.comments.id}</Link> : ''}
                  </td>
                  <td>
                    {commentsValues.commentsFields ? (
                      <Link to={`comments-fields/${commentsValues.commentsFields.id}`}>{commentsValues.commentsFields.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${commentsValues.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${commentsValues.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${commentsValues.id}/delete`}
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
              <Translate contentKey="autoproApp.commentsValues.home.notFound">No Comments Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CommentsValues;
