import { ITaskValues } from 'app/shared/model/task-values.model';
import { IApplications } from 'app/shared/model/applications.model';
import { TaskType } from 'app/shared/model/enumerations/task-type.model';

export interface ITasks {
  id?: number;
  taskName?: string | null;
  description?: string | null;
  source?: string | null;
  price?: number | null;
  type?: TaskType | null;
  taskValues?: ITaskValues[] | null;
  applications?: IApplications | null;
}

export const defaultValue: Readonly<ITasks> = {};
