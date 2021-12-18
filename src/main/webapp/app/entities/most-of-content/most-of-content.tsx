import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './most-of-content.reducer';
import { IMostOfContent } from 'app/shared/model/most-of-content.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MostOfContent = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const mostOfContentList = useAppSelector(state => state.mostOfContent.entities);
  const loading = useAppSelector(state => state.mostOfContent.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="most-of-content-heading" data-cy="MostOfContentHeading">
        <Translate contentKey="autoproApp.mostOfContent.home.title">Most Of Contents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoproApp.mostOfContent.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoproApp.mostOfContent.home.createLabel">Create new Most Of Content</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {mostOfContentList && mostOfContentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoproApp.mostOfContent.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.mostOfContent.urlOriginal">Url Original</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.mostOfContent.contentText">Content Text</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.mostOfContent.postTime">Post Time</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.mostOfContent.numberLike">Number Like</Translate>
                </th>
                <th>
                  <Translate contentKey="autoproApp.mostOfContent.numberComment">Number Comment</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mostOfContentList.map((mostOfContent, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${mostOfContent.id}`} color="link" size="sm">
                      {mostOfContent.id}
                    </Button>
                  </td>
                  <td>{mostOfContent.urlOriginal}</td>
                  <td>{mostOfContent.contentText}</td>
                  <td>
                    {mostOfContent.postTime ? <TextFormat type="date" value={mostOfContent.postTime} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{mostOfContent.numberLike}</td>
                  <td>{mostOfContent.numberComment}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mostOfContent.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mostOfContent.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${mostOfContent.id}/delete`}
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
              <Translate contentKey="autoproApp.mostOfContent.home.notFound">No Most Of Contents found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MostOfContent;
