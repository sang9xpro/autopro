import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SchedulerTask from './scheduler-task';
import SchedulerTaskDetail from './scheduler-task-detail';
import SchedulerTaskUpdate from './scheduler-task-update';
import SchedulerTaskDeleteDialog from './scheduler-task-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SchedulerTaskUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SchedulerTaskUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SchedulerTaskDetail} />
      <ErrorBoundaryRoute path={match.url} component={SchedulerTask} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SchedulerTaskDeleteDialog} />
  </>
);

export default Routes;
