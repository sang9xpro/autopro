import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Loggers from './loggers';
import LoggersFields from './loggers-fields';
import LoggersValues from './loggers-values';
import History from './history';
import HistoryFields from './history-fields';
import HistoryValues from './history-values';
import Accounts from './accounts';
import AccountFields from './account-fields';
import AccountValues from './account-values';
import Tasks from './tasks';
import TaskFields from './task-fields';
import TaskValues from './task-values';
import Applications from './applications';
import ApplicationsFields from './applications-fields';
import ApplicationsValues from './applications-values';
import Devices from './devices';
import DevicesFields from './devices-fields';
import DeviceValues from './device-values';
import SchedulerTask from './scheduler-task';
import SchedulerTaskFields from './scheduler-task-fields';
import SchedulerTaskValues from './scheduler-task-values';
import Facebook from './facebook';
import FacebookFields from './facebook-fields';
import FacebookValues from './facebook-values';
import MostOfContent from './most-of-content';
import MostOfContFields from './most-of-cont-fields';
import MostOfContValues from './most-of-cont-values';
import Comments from './comments';
import CommentsFields from './comments-fields';
import CommentsValues from './comments-values';
import Country from './country';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}loggers`} component={Loggers} />
      <ErrorBoundaryRoute path={`${match.url}loggers-fields`} component={LoggersFields} />
      <ErrorBoundaryRoute path={`${match.url}loggers-values`} component={LoggersValues} />
      <ErrorBoundaryRoute path={`${match.url}history`} component={History} />
      <ErrorBoundaryRoute path={`${match.url}history-fields`} component={HistoryFields} />
      <ErrorBoundaryRoute path={`${match.url}history-values`} component={HistoryValues} />
      <ErrorBoundaryRoute path={`${match.url}accounts`} component={Accounts} />
      <ErrorBoundaryRoute path={`${match.url}account-fields`} component={AccountFields} />
      <ErrorBoundaryRoute path={`${match.url}account-values`} component={AccountValues} />
      <ErrorBoundaryRoute path={`${match.url}tasks`} component={Tasks} />
      <ErrorBoundaryRoute path={`${match.url}task-fields`} component={TaskFields} />
      <ErrorBoundaryRoute path={`${match.url}task-values`} component={TaskValues} />
      <ErrorBoundaryRoute path={`${match.url}applications`} component={Applications} />
      <ErrorBoundaryRoute path={`${match.url}applications-fields`} component={ApplicationsFields} />
      <ErrorBoundaryRoute path={`${match.url}applications-values`} component={ApplicationsValues} />
      <ErrorBoundaryRoute path={`${match.url}devices`} component={Devices} />
      <ErrorBoundaryRoute path={`${match.url}devices-fields`} component={DevicesFields} />
      <ErrorBoundaryRoute path={`${match.url}device-values`} component={DeviceValues} />
      <ErrorBoundaryRoute path={`${match.url}scheduler-task`} component={SchedulerTask} />
      <ErrorBoundaryRoute path={`${match.url}scheduler-task-fields`} component={SchedulerTaskFields} />
      <ErrorBoundaryRoute path={`${match.url}scheduler-task-values`} component={SchedulerTaskValues} />
      <ErrorBoundaryRoute path={`${match.url}facebook`} component={Facebook} />
      <ErrorBoundaryRoute path={`${match.url}facebook-fields`} component={FacebookFields} />
      <ErrorBoundaryRoute path={`${match.url}facebook-values`} component={FacebookValues} />
      <ErrorBoundaryRoute path={`${match.url}most-of-content`} component={MostOfContent} />
      <ErrorBoundaryRoute path={`${match.url}most-of-cont-fields`} component={MostOfContFields} />
      <ErrorBoundaryRoute path={`${match.url}most-of-cont-values`} component={MostOfContValues} />
      <ErrorBoundaryRoute path={`${match.url}comments`} component={Comments} />
      <ErrorBoundaryRoute path={`${match.url}comments-fields`} component={CommentsFields} />
      <ErrorBoundaryRoute path={`${match.url}comments-values`} component={CommentsValues} />
      <ErrorBoundaryRoute path={`${match.url}country`} component={Country} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
