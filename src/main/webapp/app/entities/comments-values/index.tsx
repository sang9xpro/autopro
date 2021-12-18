import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CommentsValues from './comments-values';
import CommentsValuesDetail from './comments-values-detail';
import CommentsValuesUpdate from './comments-values-update';
import CommentsValuesDeleteDialog from './comments-values-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CommentsValuesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CommentsValuesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CommentsValuesDetail} />
      <ErrorBoundaryRoute path={match.url} component={CommentsValues} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CommentsValuesDeleteDialog} />
  </>
);

export default Routes;
