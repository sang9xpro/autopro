import { IApplicationsValues } from 'app/shared/model/applications-values.model';

export interface IApplicationsFields {
  id?: number;
  fieldName?: string | null;
  applicationsValues?: IApplicationsValues[] | null;
}

export const defaultValue: Readonly<IApplicationsFields> = {};
