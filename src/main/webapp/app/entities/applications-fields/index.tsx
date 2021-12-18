import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ApplicationsFields from './applications-fields';
import ApplicationsFieldsDetail from './applications-fields-detail';
import ApplicationsFieldsUpdate from './applications-fields-update';
import ApplicationsFieldsDeleteDialog from './applications-fields-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ApplicationsFieldsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ApplicationsFieldsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ApplicationsFieldsDetail} />
      <ErrorBoundaryRoute path={match.url} component={ApplicationsFields} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ApplicationsFieldsDeleteDialog} />
  </>
);

export default Routes;
