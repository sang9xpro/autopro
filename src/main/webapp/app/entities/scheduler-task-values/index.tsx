import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SchedulerTaskValues from './scheduler-task-values';
import SchedulerTaskValuesDetail from './scheduler-task-values-detail';
import SchedulerTaskValuesUpdate from './scheduler-task-values-update';
import SchedulerTaskValuesDeleteDialog from './scheduler-task-values-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SchedulerTaskValuesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SchedulerTaskValuesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SchedulerTaskValuesDetail} />
      <ErrorBoundaryRoute path={match.url} component={SchedulerTaskValues} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SchedulerTaskValuesDeleteDialog} />
  </>
);

export default Routes;
