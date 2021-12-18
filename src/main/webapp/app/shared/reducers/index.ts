import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import loggers from 'app/entities/loggers/loggers.reducer';
// prettier-ignore
import loggersFields from 'app/entities/loggers-fields/loggers-fields.reducer';
// prettier-ignore
import loggersValues from 'app/entities/loggers-values/loggers-values.reducer';
// prettier-ignore
import history from 'app/entities/history/history.reducer';
// prettier-ignore
import historyFields from 'app/entities/history-fields/history-fields.reducer';
// prettier-ignore
import historyValues from 'app/entities/history-values/history-values.reducer';
// prettier-ignore
import accounts from 'app/entities/accounts/accounts.reducer';
// prettier-ignore
import accountFields from 'app/entities/account-fields/account-fields.reducer';
// prettier-ignore
import accountValues from 'app/entities/account-values/account-values.reducer';
// prettier-ignore
import tasks from 'app/entities/tasks/tasks.reducer';
// prettier-ignore
import taskFields from 'app/entities/task-fields/task-fields.reducer';
// prettier-ignore
import taskValues from 'app/entities/task-values/task-values.reducer';
// prettier-ignore
import applications from 'app/entities/applications/applications.reducer';
// prettier-ignore
import applicationsFields from 'app/entities/applications-fields/applications-fields.reducer';
// prettier-ignore
import applicationsValues from 'app/entities/applications-values/applications-values.reducer';
// prettier-ignore
import devices from 'app/entities/devices/devices.reducer';
// prettier-ignore
import devicesFields from 'app/entities/devices-fields/devices-fields.reducer';
// prettier-ignore
import deviceValues from 'app/entities/device-values/device-values.reducer';
// prettier-ignore
import schedulerTask from 'app/entities/scheduler-task/scheduler-task.reducer';
// prettier-ignore
import schedulerTaskFields from 'app/entities/scheduler-task-fields/scheduler-task-fields.reducer';
// prettier-ignore
import schedulerTaskValues from 'app/entities/scheduler-task-values/scheduler-task-values.reducer';
// prettier-ignore
import facebook from 'app/entities/facebook/facebook.reducer';
// prettier-ignore
import facebookFields from 'app/entities/facebook-fields/facebook-fields.reducer';
// prettier-ignore
import facebookValues from 'app/entities/facebook-values/facebook-values.reducer';
// prettier-ignore
import mostOfContent from 'app/entities/most-of-content/most-of-content.reducer';
// prettier-ignore
import mostOfContFields from 'app/entities/most-of-cont-fields/most-of-cont-fields.reducer';
// prettier-ignore
import mostOfContValues from 'app/entities/most-of-cont-values/most-of-cont-values.reducer';
// prettier-ignore
import comments from 'app/entities/comments/comments.reducer';
// prettier-ignore
import commentsFields from 'app/entities/comments-fields/comments-fields.reducer';
// prettier-ignore
import commentsValues from 'app/entities/comments-values/comments-values.reducer';
// prettier-ignore
import country from 'app/entities/country/country.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  loggers,
  loggersFields,
  loggersValues,
  history,
  historyFields,
  historyValues,
  accounts,
  accountFields,
  accountValues,
  tasks,
  taskFields,
  taskValues,
  applications,
  applicationsFields,
  applicationsValues,
  devices,
  devicesFields,
  deviceValues,
  schedulerTask,
  schedulerTaskFields,
  schedulerTaskValues,
  facebook,
  facebookFields,
  facebookValues,
  mostOfContent,
  mostOfContFields,
  mostOfContValues,
  comments,
  commentsFields,
  commentsValues,
  country,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
