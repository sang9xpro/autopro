import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IComments } from 'app/shared/model/comments.model';
import { getEntities as getComments } from 'app/entities/comments/comments.reducer';
import { ICommentsFields } from 'app/shared/model/comments-fields.model';
import { getEntities as getCommentsFields } from 'app/entities/comments-fields/comments-fields.reducer';
import { getEntity, updateEntity, createEntity, reset } from './comments-values.reducer';
import { ICommentsValues } from 'app/shared/model/comments-values.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CommentsValuesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const comments = useAppSelector(state => state.comments.entities);
  const commentsFields = useAppSelector(state => state.commentsFields.entities);
  const commentsValuesEntity = useAppSelector(state => state.commentsValues.entity);
  const loading = useAppSelector(state => state.commentsValues.loading);
  const updating = useAppSelector(state => state.commentsValues.updating);
  const updateSuccess = useAppSelector(state => state.commentsValues.updateSuccess);
  const handleClose = () => {
    props.history.push('/comments-values');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getComments({}));
    dispatch(getCommentsFields({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...commentsValuesEntity,
      ...values,
      comments: comments.find(it => it.id.toString() === values.comments.toString()),
      commentsFields: commentsFields.find(it => it.id.toString() === values.commentsFields.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...commentsValuesEntity,
          comments: commentsValuesEntity?.comments?.id,
          commentsFields: commentsValuesEntity?.commentsFields?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.commentsValues.home.createOrEditLabel" data-cy="CommentsValuesCreateUpdateHeading">
            <Translate contentKey="autoproApp.commentsValues.home.createOrEditLabel">Create or edit a CommentsValues</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="comments-values-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.commentsValues.value')}
                id="comments-values-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                id="comments-values-comments"
                name="comments"
                data-cy="comments"
                label={translate('autoproApp.commentsValues.comments')}
                type="select"
              >
                <option value="" key="0" />
                {comments
                  ? comments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="comments-values-commentsFields"
                name="commentsFields"
                data-cy="commentsFields"
                label={translate('autoproApp.commentsValues.commentsFields')}
                type="select"
              >
                <option value="" key="0" />
                {commentsFields
                  ? commentsFields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/comments-values" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CommentsValuesUpdate;
