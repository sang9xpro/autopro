import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Applications from './applications';
import ApplicationsDetail from './applications-detail';
import ApplicationsUpdate from './applications-update';
import ApplicationsDeleteDialog from './applications-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ApplicationsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ApplicationsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ApplicationsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Applications} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ApplicationsDeleteDialog} />
  </>
);

export default Routes;
