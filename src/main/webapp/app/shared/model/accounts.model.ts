import dayjs from 'dayjs';
import { IAccountValues } from 'app/shared/model/account-values.model';
import { IApplications } from 'app/shared/model/applications.model';

export interface IAccounts {
  id?: number;
  userName?: string | null;
  password?: string | null;
  urlLogin?: string | null;
  profileFirefox?: string | null;
  profileChrome?: string | null;
  lastUpdate?: string | null;
  owner?: string | null;
  actived?: number | null;
  accountValues?: IAccountValues[] | null;
  applications?: IApplications | null;
}

export const defaultValue: Readonly<IAccounts> = {};
