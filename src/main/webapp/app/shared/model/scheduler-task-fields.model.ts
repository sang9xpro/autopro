import { ISchedulerTaskValues } from 'app/shared/model/scheduler-task-values.model';

export interface ISchedulerTaskFields {
  id?: number;
  fieldName?: string | null;
  schedulerTaskValues?: ISchedulerTaskValues[] | null;
}

export const defaultValue: Readonly<ISchedulerTaskFields> = {};
