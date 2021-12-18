import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SchedulerTaskFields from './scheduler-task-fields';
import SchedulerTaskFieldsDetail from './scheduler-task-fields-detail';
import SchedulerTaskFieldsUpdate from './scheduler-task-fields-update';
import SchedulerTaskFieldsDeleteDialog from './scheduler-task-fields-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SchedulerTaskFieldsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SchedulerTaskFieldsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SchedulerTaskFieldsDetail} />
      <ErrorBoundaryRoute path={match.url} component={SchedulerTaskFields} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SchedulerTaskFieldsDeleteDialog} />
  </>
);

export default Routes;
