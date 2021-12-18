import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './most-of-content.reducer';
import { IMostOfContent } from 'app/shared/model/most-of-content.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MostOfContentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const mostOfContentEntity = useAppSelector(state => state.mostOfContent.entity);
  const loading = useAppSelector(state => state.mostOfContent.loading);
  const updating = useAppSelector(state => state.mostOfContent.updating);
  const updateSuccess = useAppSelector(state => state.mostOfContent.updateSuccess);
  const handleClose = () => {
    props.history.push('/most-of-content');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.postTime = convertDateTimeToServer(values.postTime);

    const entity = {
      ...mostOfContentEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          postTime: displayDefaultDateTime(),
        }
      : {
          ...mostOfContentEntity,
          postTime: convertDateTimeFromServer(mostOfContentEntity.postTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.mostOfContent.home.createOrEditLabel" data-cy="MostOfContentCreateUpdateHeading">
            <Translate contentKey="autoproApp.mostOfContent.home.createOrEditLabel">Create or edit a MostOfContent</Translate>
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
                  id="most-of-content-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.mostOfContent.urlOriginal')}
                id="most-of-content-urlOriginal"
                name="urlOriginal"
                data-cy="urlOriginal"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.mostOfContent.contentText')}
                id="most-of-content-contentText"
                name="contentText"
                data-cy="contentText"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.mostOfContent.postTime')}
                id="most-of-content-postTime"
                name="postTime"
                data-cy="postTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('autoproApp.mostOfContent.numberLike')}
                id="most-of-content-numberLike"
                name="numberLike"
                data-cy="numberLike"
                type="text"
              />
              <ValidatedField
                label={translate('autoproApp.mostOfContent.numberComment')}
                id="most-of-content-numberComment"
                name="numberComment"
                data-cy="numberComment"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/most-of-content" replace color="info">
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

export default MostOfContentUpdate;
