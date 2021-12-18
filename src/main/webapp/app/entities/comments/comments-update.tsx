import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplications } from 'app/shared/model/applications.model';
import { getEntities as getApplications } from 'app/entities/applications/applications.reducer';
import { getEntity, updateEntity, createEntity, reset } from './comments.reducer';
import { IComments } from 'app/shared/model/comments.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CommentsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applications = useAppSelector(state => state.applications.entities);
  const commentsEntity = useAppSelector(state => state.comments.entity);
  const loading = useAppSelector(state => state.comments.loading);
  const updating = useAppSelector(state => state.comments.updating);
  const updateSuccess = useAppSelector(state => state.comments.updateSuccess);
  const handleClose = () => {
    props.history.push('/comments');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getApplications({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...commentsEntity,
      ...values,
      applications: applications.find(it => it.id.toString() === values.applications.toString()),
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
          ...commentsEntity,
          applications: commentsEntity?.applications?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.comments.home.createOrEditLabel" data-cy="CommentsCreateUpdateHeading">
            <Translate contentKey="autoproApp.comments.home.createOrEditLabel">Create or edit a Comments</Translate>
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
                  id="comments-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.comments.content')}
                id="comments-content"
                name="content"
                data-cy="content"
                type="text"
              />
              <ValidatedBlobField
                label={translate('autoproApp.comments.image')}
                id="comments-image"
                name="image"
                data-cy="image"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField label={translate('autoproApp.comments.owner')} id="comments-owner" name="owner" data-cy="owner" type="text" />
              <ValidatedField
                id="comments-applications"
                name="applications"
                data-cy="applications"
                label={translate('autoproApp.comments.applications')}
                type="select"
              >
                <option value="" key="0" />
                {applications
                  ? applications.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/comments" replace color="info">
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

export default CommentsUpdate;
