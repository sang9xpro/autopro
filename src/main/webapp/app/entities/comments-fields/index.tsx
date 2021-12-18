import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CommentsFields from './comments-fields';
import CommentsFieldsDetail from './comments-fields-detail';
import CommentsFieldsUpdate from './comments-fields-update';
import CommentsFieldsDeleteDialog from './comments-fields-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CommentsFieldsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CommentsFieldsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CommentsFieldsDetail} />
      <ErrorBoundaryRoute path={match.url} component={CommentsFields} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CommentsFieldsDeleteDialog} />
  </>
);

export default Routes;
