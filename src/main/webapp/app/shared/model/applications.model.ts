import { IApplicationsValues } from 'app/shared/model/applications-values.model';
import { IAccounts } from 'app/shared/model/accounts.model';
import { ITasks } from 'app/shared/model/tasks.model';
import { IComments } from 'app/shared/model/comments.model';

export interface IApplications {
  id?: number;
  appName?: string | null;
  appCode?: string | null;
  applicationsValues?: IApplicationsValues[] | null;
  accounts?: IAccounts[] | null;
  tasks?: ITasks[] | null;
  comments?: IComments[] | null;
}

export const defaultValue: Readonly<IApplications> = {};
