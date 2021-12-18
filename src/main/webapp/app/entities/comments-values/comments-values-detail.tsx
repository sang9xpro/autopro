import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './comments-values.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CommentsValuesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const commentsValuesEntity = useAppSelector(state => state.commentsValues.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commentsValuesDetailsHeading">
          <Translate contentKey="autoproApp.commentsValues.detail.title">CommentsValues</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{commentsValuesEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="autoproApp.commentsValues.value">Value</Translate>
            </span>
          </dt>
          <dd>{commentsValuesEntity.value}</dd>
          <dt>
            <Translate contentKey="autoproApp.commentsValues.comments">Comments</Translate>
          </dt>
          <dd>{commentsValuesEntity.comments ? commentsValuesEntity.comments.id : ''}</dd>
          <dt>
            <Translate contentKey="autoproApp.commentsValues.commentsFields">Comments Fields</Translate>
          </dt>
          <dd>{commentsValuesEntity.commentsFields ? commentsValuesEntity.commentsFields.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/comments-values" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/comments-values/${commentsValuesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommentsValuesDetail;
