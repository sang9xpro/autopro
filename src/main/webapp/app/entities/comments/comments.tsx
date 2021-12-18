import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './comments.reducer';
import { IComments } from 'app/shared/model/comments.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Comments = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const commentsList = useAppSelector(state => state.comments.entities);
  const loading = useAppSelector(state => state.comments.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="comments-heading" data-cy="CommentsHeading">
        <Translate contentKey="autoproApp.comments.home.title">Comments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.comments.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.comments.home.createLabel">Create new Comments</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {commentsList && commentsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.comments.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.comments.content">Content</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.comments.image">Image</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.comments.owner">Owner</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.comments.applications">Applications</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {commentsList.map((comments, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${comments.id}`} color="link" size="sm">
                      {comments.id}
                    </Button>
                  </td>
                  <td>{comments.content}</td>
                  <td>
                    {comments.image ? (
                      <div>
                        {comments.imageContentType ? (
                          <a onClick={openFile(comments.imageContentType, comments.image)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {comments.imageContentType}, {byteSize(comments.image)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{comments.owner}</td>
                  <td>
                    {comments.applications ? <Link to={`applications/${comments.applications.id}`}>{comments.applications.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${comments.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${comments.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${comments.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="autoproApp.comments.home.notFound">No Comments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Comments;
