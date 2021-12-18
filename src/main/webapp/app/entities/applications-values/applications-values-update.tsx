import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplications } from 'app/shared/model/applications.model';
import { getEntities as getApplications } from 'app/entities/applications/applications.reducer';
import { IApplicationsFields } from 'app/shared/model/applications-fields.model';
import { getEntities as getApplicationsFields } from 'app/entities/applications-fields/applications-fields.reducer';
import { getEntity, updateEntity, createEntity, reset } from './applications-values.reducer';
import { IApplicationsValues } from 'app/shared/model/applications-values.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicationsValuesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applications = useAppSelector(state => state.applications.entities);
  const applicationsFields = useAppSelector(state => state.applicationsFields.entities);
  const applicationsValuesEntity = useAppSelector(state => state.applicationsValues.entity);
  const loading = useAppSelector(state => state.applicationsValues.loading);
  const updating = useAppSelector(state => state.applicationsValues.updating);
  const updateSuccess = useAppSelector(state => state.applicationsValues.updateSuccess);
  const handleClose = () => {
    props.history.push('/applications-values');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getApplications({}));
    dispatch(getApplicationsFields({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...applicationsValuesEntity,
      ...values,
      applications: applications.find(it => it.id.toString() === values.applications.toString()),
      applicationsFields: applicationsFields.find(it => it.id.toString() === values.applicationsFields.toString()),
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
          ...applicationsValuesEntity,
          applications: applicationsValuesEntity?.applications?.id,
          applicationsFields: applicationsValuesEntity?.applicationsFields?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoproApp.applicationsValues.home.createOrEditLabel" data-cy="ApplicationsValuesCreateUpdateHeading">
            <Translate contentKey="autoproApp.applicationsValues.home.createOrEditLabel">Create or edit a ApplicationsValues</Translate>
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
                  id="applications-values-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoproApp.applicationsValues.value')}
                id="applications-values-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                id="applications-values-applications"
                name="applications"
                data-cy="applications"
                label={translate('autoproApp.applicationsValues.applications')}
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
              <ValidatedField
                id="applications-values-applicationsFields"
                name="applicationsFields"
                data-cy="applicationsFields"
                label={translate('autoproApp.applicationsValues.applicationsFields')}
                type="select"
              >
                <option value="" key="0" />
                {applicationsFields
                  ? applicationsFields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/applications-values" replace color="info">
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

export default ApplicationsValuesUpdate;
