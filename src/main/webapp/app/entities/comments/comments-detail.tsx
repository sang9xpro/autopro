import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './comments.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CommentsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const commentsEntity = useAppSelector(state => state.comments.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commentsDetailsHeading">
          <Translate contentKey="autoproApp.comments.detail.title">Comments</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{commentsEntity.id}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="autoproApp.comments.content">Content</Translate>
            </span>
          </dt>
          <dd>{commentsEntity.content}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="autoproApp.comments.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {commentsEntity.image ? (
              <div>
                {commentsEntity.imageContentType ? (
                  <a onClick={openFile(commentsEntity.imageContentType, commentsEntity.image)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {commentsEntity.imageContentType}, {byteSize(commentsEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="owner">
              <Translate contentKey="autoproApp.comments.owner">Owner</Translate>
            </span>
          </dt>
          <dd>{commentsEntity.owner}</dd>
          <dt>
            <Translate contentKey="autoproApp.comments.applications">Applications</Translate>
          </dt>
          <dd>{commentsEntity.applications ? commentsEntity.applications.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/comments" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/comments/${commentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommentsDetail;
