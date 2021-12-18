import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ApplicationsValues from './applications-values';
import ApplicationsValuesDetail from './applications-values-detail';
import ApplicationsValuesUpdate from './applications-values-update';
import ApplicationsValuesDeleteDialog from './applications-values-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ApplicationsValuesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ApplicationsValuesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ApplicationsValuesDetail} />
      <ErrorBoundaryRoute path={match.url} component={ApplicationsValues} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ApplicationsValuesDeleteDialog} />
  </>
);

export default Routes;
