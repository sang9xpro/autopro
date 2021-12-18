import { IApplications } from 'app/shared/model/applications.model';
import { IApplicationsFields } from 'app/shared/model/applications-fields.model';

export interface IApplicationsValues {
  id?: number;
  value?: string | null;
  applications?: IApplications | null;
  applicationsFields?: IApplicationsFields | null;
}

export const defaultValue: Readonly<IApplicationsValues> = {};
